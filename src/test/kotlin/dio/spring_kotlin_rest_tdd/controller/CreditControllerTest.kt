package dio.spring_kotlin_rest_tdd.controller

import com.fasterxml.jackson.databind.ObjectMapper
import dio.spring_kotlin_rest_tdd.repository.CreditRepository
import dio.spring_kotlin_rest_tdd.repository.CustomerRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class CreditControllerTest {
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
  fun `when X, then Y`() {
    TODO()
  }

}