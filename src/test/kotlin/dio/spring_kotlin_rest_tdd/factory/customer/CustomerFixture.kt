package dio.spring_kotlin_rest_tdd.factory.customer

import dio.spring_kotlin_rest_tdd.model.Address
import dio.spring_kotlin_rest_tdd.model.Customer
import kotlin.random.Random

class CustomerFixture {

  companion object {
    fun create(
      id: Long = Random.nextLong(0, 99999),
      firstName: String = "Albert",
      lastName: String = "da Silva",
      cpf: String = "12345678935",
      email: String = "${firstName.lowercase()}@email.com",
      cep: String = "95000000",
      income: Long = 100000,
      address: Address
    ): Customer {
      return Customer(id = id, firstName = firstName, lastName = lastName, cpf = cpf, email = email, cep = cep, address = address, income = income)
    }
  }
}