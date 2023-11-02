package dio.spring_kotlin_rest_tdd.controller

import com.fasterxml.jackson.databind.ObjectMapper
import dio.spring_kotlin_rest_tdd.dto.CreditDto
import dio.spring_kotlin_rest_tdd.factory.credit.CreditDtoFixture
import dio.spring_kotlin_rest_tdd.factory.customer.AddressFixture
import dio.spring_kotlin_rest_tdd.factory.customer.CustomerFixture
import dio.spring_kotlin_rest_tdd.repository.AddressRepository
import dio.spring_kotlin_rest_tdd.repository.CreditRepository
import dio.spring_kotlin_rest_tdd.repository.CustomerRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class CreditControllerTest {
  @Autowired
  private lateinit var addresses: AddressRepository
  @Autowired
  private lateinit var customers: CustomerRepository
  @Autowired
  private lateinit var credits: CreditRepository
  @Autowired
  private lateinit var mockMvc: MockMvc
  @Autowired
  private lateinit var mapper: ObjectMapper

  companion object {
    const val URL: String = "/api/credits"
  }

  @BeforeEach
  fun setup() {
    customers.deleteAll()
    credits.deleteAll()
  }
  @AfterEach
  fun tearDown() {
    customers.deleteAll()
    credits.deleteAll()
  }

  @Test
  fun `when request new Credit to Database, then Returns Success`() {
    val address = AddressFixture.create()
    addresses.save(address)
    val factoredCustomer = CustomerFixture.create(address = address)
    customers.save(factoredCustomer)
    val factoredCreditRequest = CreditDtoFixture.create(factoredCustomer)

    val stringData = mapper.writeValueAsString(factoredCreditRequest)

    mockMvc.perform(
      MockMvcRequestBuilders
      .post(URL)
      .contentType(MediaType.APPLICATION_JSON)
      .content(stringData)).andExpect(MockMvcResultMatchers.status().isCreated)
  }

  @Test
  fun `when request new Credit with Invalid Data, then Returns Bad Request`() {
    val address = AddressFixture.create()
    addresses.save(address)
    val factoredCustomer = CustomerFixture.create(address = address)
    customers.save(factoredCustomer)
    val factoredCreditRequest = CreditDtoFixture.create(customerId = factoredCustomer, numberOfInstallments = 49)

    val stringData = mapper.writeValueAsString(factoredCreditRequest)

    mockMvc.perform(
      MockMvcRequestBuilders
        .post(URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(stringData)).andExpect(MockMvcResultMatchers.status().isBadRequest)
  }



  @Test
  fun `when X, then Y`() {
    TODO()
  }

}