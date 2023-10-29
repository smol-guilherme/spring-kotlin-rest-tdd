package dio.spring_kotlin_rest_tdd.controller

import com.fasterxml.jackson.databind.ObjectMapper
import dio.spring_kotlin_rest_tdd.factory.customer.AddressFixture
import dio.spring_kotlin_rest_tdd.factory.customer.CustomerDtoFixture
import dio.spring_kotlin_rest_tdd.model.Address
import dio.spring_kotlin_rest_tdd.model.Customer
import dio.spring_kotlin_rest_tdd.repository.AddressRepository
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
class CustomerControllerTest {
  @Autowired private lateinit var customers: CustomerRepository
  @Autowired private lateinit var addresses: AddressRepository
  @Autowired private lateinit var mockMvc: MockMvc
  @Autowired private lateinit var mapper: ObjectMapper

  companion object {
    const val URL: String = "/api/customers"
  }

  @BeforeEach
  fun setup() {
    customers.deleteAll()
    addresses.deleteAll()
  }
  @AfterEach
  fun tearDown() {
    customers.deleteAll()
    addresses.deleteAll()
  }

  @Test
  fun `when save Customer to Database, then Returns Success`() {
    val factoredCustomerDto = CustomerDtoFixture.create(cep = "70150900")
    val stringData = mapper.writeValueAsString(factoredCustomerDto)

    mockMvc.perform(MockMvcRequestBuilders
      .post(URL)
      .contentType(MediaType.APPLICATION_JSON)
      .content(stringData)).andExpect(MockMvcResultMatchers.status().isCreated)
  }

  @Test
  fun `when save a Duplicate Customer to Database, then Returns Conflict Status Code`() {
    val factoredAddress = AddressFixture.create("70150900")
    val factoredCustomerDto = CustomerDtoFixture.create(cep = "70150900")
    addresses.save(factoredAddress)
    customers.save(Customer(factoredCustomerDto, factoredAddress))
    val customer = Customer(factoredCustomerDto, factoredAddress)

    val stringData = mapper.writeValueAsString(customer)

    mockMvc.perform(MockMvcRequestBuilders
      .post(URL)
      .contentType(MediaType.APPLICATION_JSON)
      .content(stringData))
      .andExpect(MockMvcResultMatchers.status().isConflict)
  }

  @Test
  fun `when save a Customer with Missing data, then Returns Bad Request`() {
    val factoredCustomerDto = CustomerDtoFixture.create(cep = "70150900", firstName = "")
    val stringData = mapper.writeValueAsString(factoredCustomerDto)

    mockMvc.perform(MockMvcRequestBuilders
      .post(URL)
      .contentType(MediaType.APPLICATION_JSON)
      .content(stringData))
      .andExpect(MockMvcResultMatchers.status().isBadRequest)
  }

//  @Test
//  fun ``


}