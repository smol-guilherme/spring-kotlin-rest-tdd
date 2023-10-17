package dio.spring_kotlin_rest_tdd.service

import dio.spring_kotlin_rest_tdd.factory.CustomerFixture
import dio.spring_kotlin_rest_tdd.model.Customer
import dio.spring_kotlin_rest_tdd.repository.CustomerRepository
import dio.spring_kotlin_rest_tdd.service.impl.CustomerServiceImplementation
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.util.*
import kotlin.random.Random

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CustomerServiceTest {
  @MockK lateinit var customers: CustomerRepository
  @InjectMockKs lateinit var service: CustomerServiceImplementation

  @Test
  fun `when Insert New Customer, then Success`() {
    val factoredCustomer = CustomerFixture.create()
    every { customers.save(any()) } returns factoredCustomer

    val result = service.save(factoredCustomer)
    Assertions.assertThat(result).isNotNull()
    Assertions.assertThat(result).isEqualTo(factoredCustomer)
    verify(exactly = 1) { service.save(factoredCustomer) }
  }

  @Test
  fun `when Search for Customer, then Returns Customer`() {
    val randomId = Random.nextLong(0, 99999)
    val factoredCustomer = CustomerFixture.create(randomId)
    every { customers.findById(randomId) } returns Optional.of(factoredCustomer)
    val result = service.findById(randomId)

    Assertions.assertThat(result).isNotNull
    Assertions.assertThat(result).isExactlyInstanceOf(Customer::class.java)
    Assertions.assertThat(result).isSameAs(factoredCustomer)
    verify(exactly = 1) { customers.findById(randomId) }
  }

  @Test
  fun `when X then Y`() {
    TODO()
  }

}