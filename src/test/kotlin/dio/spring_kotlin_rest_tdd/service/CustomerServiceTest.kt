package dio.spring_kotlin_rest_tdd.service

import dio.spring_kotlin_rest_tdd.exception.BusinessException
import dio.spring_kotlin_rest_tdd.factory.customer.AddressFixture
import dio.spring_kotlin_rest_tdd.factory.customer.CustomerFixture
import dio.spring_kotlin_rest_tdd.model.Customer
import dio.spring_kotlin_rest_tdd.repository.AddressRepository
import dio.spring_kotlin_rest_tdd.repository.CustomerRepository
import dio.spring_kotlin_rest_tdd.service.impl.CustomerServiceImplementation
import feign.FeignException
import feign.Request
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.random.Random

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CustomerServiceTest {
  @MockK lateinit var address: AddressRepository
  @MockK lateinit var viaCep: ViaCepService
  @MockK lateinit var customers: CustomerRepository
  @InjectMockKs lateinit var service: CustomerServiceImplementation

  @Test
  fun `when Insert New Customer, then Succeeds`() {
    val factoredCustomer = CustomerFixture.create(address = AddressFixture.address("97000000"))
    every { customers.save(any()) } returns factoredCustomer

    val result = service.save(factoredCustomer)
    Assertions.assertThat(result).isNotNull()
    Assertions.assertThat(result).isEqualTo(factoredCustomer)
    verify(exactly = 1) { service.save(factoredCustomer) }
  }

  @Test
  fun `when Search for Customer, then Returns Customer`() {
    val randomId = Random.nextLong(0, 99999)
    val factoredCustomer = CustomerFixture.create(randomId, address = AddressFixture.address("97000000"))
    every { customers.findById(randomId) } returns Optional.of(factoredCustomer)
    val result = service.findById(randomId)

    Assertions.assertThat(result).isNotNull
    Assertions.assertThat(result).isExactlyInstanceOf(Customer::class.java)
    Assertions.assertThat(result).isSameAs(factoredCustomer)
    verify(exactly = 1) { customers.findById(randomId) }
  }

  @Test
  fun `when Client is Insert With Invalid Address, then Throws an Exception`() {
    //    Request object below taken from OpenFeign tests on GitHub and refactored to Kotlin
    val request = Request.create(
      Request.HttpMethod.GET,
      "/home",
      emptyMap(),
      "data".toByteArray(StandardCharsets.UTF_16BE),
      StandardCharsets.UTF_16BE,
      null
    )
    //    mockkConstructor(FeignException.BadRequest::class) does not solve the issue I was having
    val exception = FeignException.BadRequest("Bad request", request, request.body(), request.headers())
    val randomCep = Random.nextInt(90000000, 99999999).toString()

    every { address.findById(randomCep) } returns Optional.empty()
    every { viaCep.enquiryCep(randomCep) } throws exception

    Assertions.assertThatExceptionOfType(FeignException::class.java)
    // add other test parameters 'is thrown by, etc...'
  }

  @Test
  fun `when Search for Customer, then Throws an Exception`() {
    val randomId = Random.nextLong(0, 99999)
    every { customers.findById(randomId) } returns Optional.empty()

    Assertions.assertThatExceptionOfType(BusinessException::class.java)
      .isThrownBy { service.findById(randomId) }
      .withMessage("No user $randomId found")
    verify(exactly = 1) { customers.findById(randomId) }
  }

  @Test
  fun `when Delete Customer, then Succeeds`() {
    val randomId = Random.nextLong(0, 99999)
    val factoredCustomer = CustomerFixture.create(randomId, address = AddressFixture.address("97000000"))
    every { customers.findById(randomId) } returns Optional.of(factoredCustomer)
    every { customers.delete(factoredCustomer) } just runs

    service.delete(randomId)

    verify(exactly = 1) { customers.findById(randomId) }
    verify(exactly = 1) { customers.delete(factoredCustomer) }
  }

  @Test
  fun `when X then Y`() {
    TODO()
  }
}