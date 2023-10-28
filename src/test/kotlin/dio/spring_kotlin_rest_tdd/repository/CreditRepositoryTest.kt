package dio.spring_kotlin_rest_tdd.repository

import dio.spring_kotlin_rest_tdd.dto.response.CustomerCreditDto
import dio.spring_kotlin_rest_tdd.factory.credit.CreditFixture
import dio.spring_kotlin_rest_tdd.factory.customer.AddressFixture
import dio.spring_kotlin_rest_tdd.factory.customer.CustomerFixture
import dio.spring_kotlin_rest_tdd.model.Address
import dio.spring_kotlin_rest_tdd.model.Credit
import dio.spring_kotlin_rest_tdd.model.Customer
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import java.util.*

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditRepositoryTest {
  @Autowired lateinit var credits: CreditRepository
  @Autowired lateinit var testEntityManager: TestEntityManager

  private lateinit var customer: Customer
  private lateinit var address: Address
  private lateinit var credit1: Credit
  private lateinit var credit2: Credit

  @BeforeEach
  fun setup() {
    val factoredAddress = AddressFixture.create("97000000")
    val factoredCustomer = CustomerFixture.create(address = factoredAddress)
    val factoredCredit1 = CreditFixture.create(customer = factoredCustomer)
    val factoredCredit2 = CreditFixture.create(customer = factoredCustomer)

    address = testEntityManager.merge(factoredAddress)
    customer = testEntityManager.merge(factoredCustomer)
    credit1 = testEntityManager.merge(factoredCredit1)
    credit2 = testEntityManager.merge(factoredCredit2)
  }

  @Test
  fun `integrate Find by Credit ID, then Returns Success`() {
    val creditResponse1 = credits.findOneById(credit1.id!!)
    val creditResponse2 = credits.findOneById(credit2.id!!)

    Assertions.assertThat(creditResponse1).isNotNull
    Assertions.assertThat(creditResponse2).isNotNull
    Assertions.assertThat(creditResponse1).isEqualTo(Optional.of(credit1))
    Assertions.assertThat(creditResponse2).isEqualTo(Optional.of(credit2))
  }

  @Test
  fun `integrate Find all Credits assigned to the Customer, then Returns Success`() {
    val list = credits.findAllByCustomerId(customerId = customer.id!!)

    Assertions.assertThat(list).isNotEmpty
    Assertions.assertThat(list.count()).isEqualTo(2)
  }

  @Test
  fun `integrate Find a Single entry by Customer ID and Credit ID, then Returns Success`() {
    val single = credits.findOneByIdAndCustomerId(id = credit1.id!!, customerId = customer.id!!)

    Assertions.assertThat(single).isNotNull
  }
}