package dio.spring_kotlin_rest_tdd.factory

import dio.spring_kotlin_rest_tdd.model.Address
import dio.spring_kotlin_rest_tdd.model.Customer
import jakarta.validation.constraints.Email
import org.hibernate.validator.constraints.br.CPF
import kotlin.random.Random

class CustomerFixture {

  companion object {
    fun create(
      id: Long = Random.nextLong(0, 99999),
      firstName: String = "Albert",
      lastName: String = "da Silva",
      cpf: CPF = "01725835985" as CPF,
      email: Email = "${firstName.lowercase()}@email.com" as Email,
      cep: String = "95000000",
      address: Address
    ): Customer {
      return Customer(id = id, firstName = firstName, lastName = lastName, cpf = cpf, email = email, cep = cep, address = address)
    }
  }
}