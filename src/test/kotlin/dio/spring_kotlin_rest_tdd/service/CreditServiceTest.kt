package dio.spring_kotlin_rest_tdd.service

import dio.spring_kotlin_rest_tdd.factory.AddressFixture
import dio.spring_kotlin_rest_tdd.factory.CreditFixture
import dio.spring_kotlin_rest_tdd.factory.CreditRequestFixture
import dio.spring_kotlin_rest_tdd.factory.CustomerFixture
import dio.spring_kotlin_rest_tdd.repository.CreditRepository
import dio.spring_kotlin_rest_tdd.service.impl.CreditServiceImplementation
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate
import kotlin.random.Random

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CreditServiceTest {
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
    val result = service.requestOne(factoredCreditRequest)

    Assertions.assertThat(result).isNotNull()
    Assertions.assertThat(result).isEqualTo(factoredCredit)
    // mudar aqui quando alterar a saída
  }
  @Test
  fun `when X then Y`() {
    TODO()
  }
}