package dio.spring_kotlin_rest_tdd.factory.customer

import dio.spring_kotlin_rest_tdd.dto.request.CustomerDto
import dio.spring_kotlin_rest_tdd.model.Address
import dio.spring_kotlin_rest_tdd.model.Credit
import dio.spring_kotlin_rest_tdd.model.Customer
import kotlin.random.Random

class CustomerDtoFixture {

  companion object {
    fun create(
      firstName: String? = "Albert",
      lastName: String? = "da Silva",
      cpf: String = "12345678935",
      email: String = "${firstName?:"${Random(1000).toString()}".lowercase()}@email.com",
      cep: String = "70150900",
      income: Long = 100000,
    ): CustomerDto {
      return CustomerDto(
        firstName = firstName!!,
        lastName = lastName!!,
        cpf = cpf,
        email = email,
        cep = cep,
        income = income,
      )
    }
  }
}