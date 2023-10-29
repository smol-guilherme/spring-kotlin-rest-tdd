package dio.spring_kotlin_rest_tdd.controller

import com.fasterxml.jackson.databind.ObjectMapper
import dio.spring_kotlin_rest_tdd.dto.request.CustomerUpdateDto
import dio.spring_kotlin_rest_tdd.factory.customer.AddressFixture
import dio.spring_kotlin_rest_tdd.factory.customer.CustomerDtoFixture
import dio.spring_kotlin_rest_tdd.factory.customer.CustomerUpdateFixture
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
import kotlin.random.Random


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
    val factoredCustomerDto = CustomerDtoFixture.create()
    val stringData = mapper.writeValueAsString(factoredCustomerDto)

    mockMvc.perform(MockMvcRequestBuilders
      .post(URL)
      .contentType(MediaType.APPLICATION_JSON)
      .content(stringData)).andExpect(MockMvcResultMatchers.status().isCreated)
  }

  @Test
  fun `when save a Duplicate Customer to Database, then Returns Conflict Status Code`() {
    val factoredAddress = AddressFixture.create()
    val factoredCustomerDto = CustomerDtoFixture.create()
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
    val factoredCustomerDto = CustomerDtoFixture.create(firstName = "")
    val stringData = mapper.writeValueAsString(factoredCustomerDto)

    mockMvc.perform(MockMvcRequestBuilders
      .post(URL)
      .contentType(MediaType.APPLICATION_JSON)
      .content(stringData))
      .andExpect(MockMvcResultMatchers.status().isBadRequest)
  }

  @Test
  fun `when Searching for a Customer by Id, then Returns Success`() {
    val factoredAddress = AddressFixture.create()
    val factoredCustomerDto = CustomerDtoFixture.create()
    addresses.save(factoredAddress)
    val savedCustomer = customers.save(Customer(factoredCustomerDto, factoredAddress))

    mockMvc.perform(MockMvcRequestBuilders
      .get(URL+"/${savedCustomer.id}")
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isOk)
      .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("${savedCustomer.cpf}"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("${savedCustomer.email}"))
  }

  @Test
  fun `when Searching for a Customer by an Invalid Id, then Returns Bad Request`() {
    mockMvc.perform(MockMvcRequestBuilders
      .get(URL+"/${Random.nextLong(0, 10000)}")
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isBadRequest)
  }

  @Test
  fun `when Deleting an existing Customer, then Returns No Content`() {
    val factoredAddress = AddressFixture.create()
    val factoredCustomerDto = CustomerDtoFixture.create()
    addresses.save(factoredAddress)
    val customer = customers.save(Customer(factoredCustomerDto, factoredAddress))

    mockMvc.perform(MockMvcRequestBuilders
      .delete(URL+"/${customer.id}")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isNoContent)
  }

  @Test
  fun `when Deleting a nonexistent Customer, then Returns Bad Request`() {
    mockMvc.perform(MockMvcRequestBuilders
      .delete(URL+"/${Random.nextLong(0, 10000)}")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isBadRequest)
  }

  @Test
  fun `when Updating an existing Customer, then Returns Success`() {
    val factoredAddress = AddressFixture.create("70150900")
    val factoredCustomerDto = CustomerDtoFixture.create(cep = "70150900")
    addresses.save(factoredAddress)
    val savedCustomer = customers.save(Customer(factoredCustomerDto, factoredAddress))
    val factoredUpdateData = CustomerUpdateFixture.create(
      firstName = "Jonas",
      income = 12000L,
      address = savedCustomer.address
    )
    val stringData = mapper.writeValueAsString(factoredUpdateData)

    mockMvc.perform(MockMvcRequestBuilders
      .patch(URL+"?customerId=${savedCustomer.id!!}")
      .contentType(MediaType.APPLICATION_JSON)
      .content(stringData))
      .andExpect(MockMvcResultMatchers.status().isOk)
      .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("${factoredUpdateData.firstName}"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("${savedCustomer.lastName}"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.income").value("${factoredUpdateData.income}"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("${savedCustomer.cpf}"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("${savedCustomer.email}"))
  }

  @Test
  fun `when Updating an existing Customer with No Changes, then Returns Success`() {
    val factoredAddress = AddressFixture.create("70150900")
    val factoredCustomerDto = CustomerDtoFixture.create(cep = "70150900")
    addresses.save(factoredAddress)
    val savedCustomer = customers.save(Customer(factoredCustomerDto, factoredAddress))
    val factoredUpdateData = CustomerUpdateFixture.create(
      firstName = "",
      lastName = "",
      address = savedCustomer.address
    )
    val stringData = mapper.writeValueAsString(factoredUpdateData)

    mockMvc.perform(MockMvcRequestBuilders
      .patch(URL+"?customerId=${savedCustomer.id!!}")
      .contentType(MediaType.APPLICATION_JSON)
      .content(stringData))
      .andExpect(MockMvcResultMatchers.status().isOk)
  }

  @Test
  fun `when Updating a nonexistent Customer, then Returns Bad Request`() {
    val factoredAddress = AddressFixture.create()
    val address = addresses.save(factoredAddress)
    val factoredUpdateData = CustomerUpdateFixture.create(
      firstName = "Jonas",
      lastName = "Tales",
      address = address
    )
    val stringData = mapper.writeValueAsString(factoredUpdateData)

    mockMvc.perform(MockMvcRequestBuilders
      .patch(URL+"?customerId=${Random.nextLong(0,1000)}")
      .contentType(MediaType.APPLICATION_JSON)
      .content(stringData))
      .andExpect(MockMvcResultMatchers.status().isBadRequest)
  }
}