package dio.spring_kotlin_rest_tdd.service

import dio.spring_kotlin_rest_tdd.dto.response.CreditListDto
import dio.spring_kotlin_rest_tdd.exception.BusinessException
import dio.spring_kotlin_rest_tdd.factory.*
import dio.spring_kotlin_rest_tdd.factory.credit.CreditFixture
import dio.spring_kotlin_rest_tdd.factory.credit.CreditListFixture
import dio.spring_kotlin_rest_tdd.factory.credit.CreditRequestFixture
import dio.spring_kotlin_rest_tdd.factory.credit.CustomerCreditFixture
import dio.spring_kotlin_rest_tdd.factory.customer.AddressFixture
import dio.spring_kotlin_rest_tdd.factory.customer.CustomerFixture
import dio.spring_kotlin_rest_tdd.model.Credit
import dio.spring_kotlin_rest_tdd.model.type.DataTypes
import dio.spring_kotlin_rest_tdd.repository.CreditRepository
import dio.spring_kotlin_rest_tdd.repository.CustomerRepository
import dio.spring_kotlin_rest_tdd.service.impl.CreditServiceImplementation
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate
import java.util.*
import kotlin.random.Random

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CreditServiceTest {
  @MockK
  lateinit var customer: CustomerRepository
  @MockK
  lateinit var credit: CreditRepository
  @InjectMockKs
  lateinit var service: CreditServiceImplementation

  @Test
  fun `when Register a new Credit Option, then Returns Success`() {
    val factoredCustomer = CustomerFixture.create(address = AddressFixture.address("97000000"))
    val factoredCreditRequest = CreditRequestFixture.create(
      dayOfFirstInstallment = LocalDate.now().plusDays(Random.nextLong(1,90)),
      numberOfInstallments = Random.nextInt(1,48),
      customerId = factoredCustomer.id
    )
    val factoredCredit = CreditFixture.create(
      customerId = factoredCreditRequest.customerId,
      dayOfFirstInstallment = factoredCreditRequest.dayOfFirstInstallment,
      numberOfInstallments = factoredCreditRequest.numberOfInstallments,
      )

    every { credit.save(any()) } returns factoredCredit
    every { customer.findById(any<Long>()) } returns Optional.of(factoredCustomer)
    val result = service.requestOne(factoredCreditRequest)

    Assertions.assertThat(result).isNotNull()
    Assertions.assertThat(result).isEqualTo("Your Credit was requested successfully.\n" +
        "Refer to ID ${factoredCredit.id} to verify the approval status.")
  }

  @Test
  fun `when Register a new Credit Option with too many Installments, then Throws an Exception`() {
    val factoredCustomer = CustomerFixture.create(address = AddressFixture.address("97000000"))
    val factoredCreditRequest = CreditRequestFixture.create(
      dayOfFirstInstallment = LocalDate.now().plusDays(Random.nextLong(1,90)),
      numberOfInstallments = Random.nextInt(49, 100),
      customerId = factoredCustomer.id
    )

    Assertions.assertThatExceptionOfType(BusinessException::class.java)
      .isThrownBy { service.validate(factoredCreditRequest) }
      .withMessage("Number of installments must be at most 48\n" +
          "${factoredCreditRequest.numberOfInstallments} was provided")
  }

  @Test
  fun `when Search for a Customers Credit Options, then Returns Success`() {
    val factoredCustomer = CustomerFixture.create(address = AddressFixture.address("97000000"))
    val factoredCustomerCredit = CustomerCreditFixture.create(
      numberOfInstallments = Random.nextInt(1, 48),
      customerId = factoredCustomer.id,
      creditValue = Random.nextLong(100000, 200000),
      status = DataTypes.Status.APPROVED,
      customerIncome = factoredCustomer.income,
      customerEmail = factoredCustomer.email
    )
    val factoredCredit = CreditFixture.create(
      id = factoredCustomerCredit.creditId,
      numberOfInstallments = factoredCustomerCredit.numberOfInstallments,
      creditValue = factoredCustomerCredit.creditValue,
      status = DataTypes.Status.APPROVED,
      customerId = factoredCustomer.id
    )

    every { credit.findOneByCustomerIdAndCreditId(any(), any()) } returns Optional.of(factoredCustomerCredit)
    every { credit.findById(any()) } returns Optional.of(factoredCredit)
    every { customer.findById(any<Long>()) } returns Optional.of(factoredCustomer)
    val result = service.listOne(factoredCustomerCredit.creditId, factoredCustomer.id)

    Assertions.assertThat(result).isNotNull()
    Assertions.assertThat(result).isEqualTo(Optional.of(factoredCustomerCredit))
    verify(exactly = 1) { credit.findOneByCustomerIdAndCreditId(factoredCustomerCredit.creditId, factoredCustomer.id) }
  }

  @Test
  fun `when Search for an Invalid Customers Credit Options, then Throws an Exception`() {
    val factoredCustomer = CustomerFixture.create(address = AddressFixture.address("97000000"))
    val randomUUID = UUID.randomUUID()

    every { credit.findById(randomUUID) } returns Optional.empty()
    every { customer.findById(any<Long>()) } returns Optional.of(factoredCustomer)

    Assertions.assertThatExceptionOfType(BusinessException::class.java)
      .isThrownBy { service.validate(randomUUID) }
      .withMessage("No Credit found for signature: $randomUUID")
  }

  @Test
  fun `when Request a List of the Customers Credit, then Returns Success`() {
    val factoredCustomer = CustomerFixture.create(address = AddressFixture.address("97000000"))
    val factoredList: Iterable<CreditListDto> = (1 .. Random.nextInt(3,5)).map {
      CreditListFixture.create(
        numberOfInstallments = Random.nextInt(1, 48),
        customerId = factoredCustomer.id,
        creditValue = Random.nextLong(100000, 200000),
      )
    }

    every { credit.findAllByCustomerId(any()) } returns factoredList
    every { customer.findById(any<Long>()) } returns Optional.of(factoredCustomer)

    val result = service.listAllFromCustomer(factoredCustomer.id)

    Assertions.assertThat(result).isNotNull()
    Assertions.assertThat(result).isEqualTo(factoredList)
    verify(exactly = 1) { credit.findAllByCustomerId(factoredCustomer.id) }
  }

  @Test
  fun `when Request a Credit List of an Invalid Customer, then Throws an Exception`() {
    val randomId = Random.nextLong(0, 100000)

    every { customer.findById(randomId) } returns Optional.empty()

    Assertions.assertThatExceptionOfType(BusinessException::class.java)
      .isThrownBy { service.validate(randomId) }
      .withMessage("No Customer found for ID: $randomId")
  }
}