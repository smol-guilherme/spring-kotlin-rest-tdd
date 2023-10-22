package dio.spring_kotlin_rest_tdd.factory

import dio.spring_kotlin_rest_tdd.model.Credit
import dio.spring_kotlin_rest_tdd.model.Customer
import dio.spring_kotlin_rest_tdd.model.type.DataTypes
import java.time.LocalDate
import kotlin.random.Random

class CreditFixture {

  companion object {
    fun create(
      id: Long = Random.nextLong(0, 1000),
      status: DataTypes.Status = DataTypes.Status.IN_PROGRESS,
      dayOfFirstInstallment: LocalDate = LocalDate.now().plusDays(1),
      numberOfInstallments: Int = 0,
      value: Long = 1000000,
      customer: Customer? = null,
    ): Credit {
      return Credit(id, status = status, dayOfFirstInstallment = dayOfFirstInstallment, numberOfInstallments = numberOfInstallments, value = value)
    }
  }
}