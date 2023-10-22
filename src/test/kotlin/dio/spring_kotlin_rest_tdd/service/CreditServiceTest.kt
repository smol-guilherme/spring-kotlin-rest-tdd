package dio.spring_kotlin_rest_tdd.service

import dio.spring_kotlin_rest_tdd.repository.AddressRepository
import dio.spring_kotlin_rest_tdd.repository.CustomerRepository
import dio.spring_kotlin_rest_tdd.service.impl.CustomerServiceImplementation
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CreditServiceTest {
  @MockK
  lateinit var address: AddressRepository
  @MockK
  lateinit var viaCep: ViaCepService
  @MockK
  lateinit var customers: CustomerRepository
  @InjectMockKs
  lateinit var service: CustomerServiceImplementation

  @Test
  fun `when X then Y`() {
    TODO()
  }
}