package dio.spring_kotlin_rest_tdd.factory.customer

import dio.spring_kotlin_rest_tdd.dto.request.CustomerUpdateDto
import dio.spring_kotlin_rest_tdd.model.Address

class CustomerUpdateFixture {

  companion object {
    fun create(
      firstName: String? = "",
      lastName: String? = "",
      income: Long?,
      cpf: String? = "",
      email: String? = "",
      cep: String? = "",
      address: Address? = null,
    ): CustomerUpdateDto {
      return CustomerUpdateDto(
        firstName = firstName,
        lastName = lastName,
        income = income,
        cpf = cpf,
        email = email,
        cep = cep,
        address = address
      )
    }
  }
}