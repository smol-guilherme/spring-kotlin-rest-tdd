package dio.spring_kotlin_rest_tdd.controller

import com.fasterxml.jackson.databind.ObjectMapper
import dio.spring_kotlin_rest_tdd.factory.customer.CustomerDtoFixture
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
  @Autowired private lateinit var mockMvc: MockMvc
  @Autowired private lateinit var mapper: ObjectMapper

  companion object {
    const val URL: String = "/api/customers"
  }

  @BeforeEach
  fun setup() = customers.deleteAll()
  @AfterEach
  fun tearDown() = customers.deleteAll()

  @Test
  fun `when save Customer to Database, then Returns Success`() {
    val factoredCustomerDto = CustomerDtoFixture.create(cep = "70150900")
    val stringData = mapper.writeValueAsString(factoredCustomerDto)

    mockMvc.perform(MockMvcRequestBuilders
      .post(URL)
      .contentType(MediaType.APPLICATION_JSON)
      .content(stringData)).andExpect(MockMvcResultMatchers.status().isCreated)
  }

}