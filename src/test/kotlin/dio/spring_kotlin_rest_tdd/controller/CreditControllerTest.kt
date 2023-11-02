package dio.spring_kotlin_rest_tdd.controller

import com.fasterxml.jackson.databind.ObjectMapper
import dio.spring_kotlin_rest_tdd.dto.CreditDto
import dio.spring_kotlin_rest_tdd.dto.response.CreditListDto
import dio.spring_kotlin_rest_tdd.dto.response.CustomerCreditDto
import dio.spring_kotlin_rest_tdd.factory.credit.CreditDtoFixture
import dio.spring_kotlin_rest_tdd.factory.credit.CreditFixture
import dio.spring_kotlin_rest_tdd.factory.customer.AddressFixture
import dio.spring_kotlin_rest_tdd.factory.customer.CustomerFixture
import dio.spring_kotlin_rest_tdd.model.Credit
import dio.spring_kotlin_rest_tdd.repository.AddressRepository
import dio.spring_kotlin_rest_tdd.repository.CreditRepository
import dio.spring_kotlin_rest_tdd.repository.CustomerRepository
import org.hamcrest.Matchers.*
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
import java.util.Optional
import java.util.UUID
import kotlin.random.Random

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
  fun `when Search for All Credits of a Customer, then Returns Success`() {
    val address = AddressFixture.create()
    addresses.save(address)
    val factoredCustomer = CustomerFixture.create(address = address)
    val savedCustomer = customers.save(factoredCustomer)
    val listOfCredits = mutableListOf<Optional<Credit>>()
    val size = Random.nextInt(2,5)
    for (i in 1..size) {
      var entry = credits.save(CreditFixture.create(customer = factoredCustomer))
      listOfCredits.add(credits.findById(entry.id!!))
    }

    mockMvc.perform(MockMvcRequestBuilders
      .get(URL +"?customerId=${savedCustomer.id}")
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isOk)
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].creditValue").value(listOfCredits[0].orElse(null).creditValue))
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].numberOfInstallments").value(listOfCredits[0].orElse(null).numberOfInstallments))
      .andExpect(MockMvcResultMatchers.jsonPath("$[${size-1}].creditValue").value(listOfCredits[size-1].orElse(null).creditValue))
      .andExpect(MockMvcResultMatchers.jsonPath("$[${size-1}].numberOfInstallments").value(listOfCredits[size-1].orElse(null).numberOfInstallments))
  }

  @Test
  fun `when Search for All Credits of an Invalid Customer, then Returns Bad Request`() {
    val randomId = Random.nextLong(0, 10000)

    mockMvc.perform(
      MockMvcRequestBuilders
        .get(URL +"?customerId=${randomId}")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest)
  }

  @Test
  fun `when Search for a Single Credit of a Customer, then Returns Success`() {
    val address = AddressFixture.create()
    addresses.save(address)
    val factoredCustomer = CustomerFixture.create(address = address)
    val customer = customers.save(factoredCustomer)
    val credit = credits.save(CreditFixture.create(customer = customer))

    mockMvc.perform(
      MockMvcRequestBuilders
        .get(URL +"/${credit.id}?customerId=${customer.id}")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk)
        .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(customer.id))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(credit.id.toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.creditValue").value(credit.creditValue))
  }

  @Test
  fun `when Search for a Invalid Credit Id of a Customer, then Returns Bad Request`() {
    val address = AddressFixture.create()
    addresses.save(address)
    val factoredCustomer = CustomerFixture.create(address = address)
    val customer = customers.save(factoredCustomer)

    mockMvc.perform(
      MockMvcRequestBuilders
        .get(URL +"/${UUID.randomUUID()}?customerId=${customer.id}")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest)
  }
}