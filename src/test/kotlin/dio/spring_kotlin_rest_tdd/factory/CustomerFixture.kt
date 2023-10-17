package dio.spring_kotlin_rest_tdd.factory

import dio.spring_kotlin_rest_tdd.model.Customer
import kotlin.random.Random

class CustomerFixture {

  companion object {
    fun create(
      id: Long = Random.nextLong(0, 99999)
    ): Customer {
      return Customer(id = id)
    }
  }
}