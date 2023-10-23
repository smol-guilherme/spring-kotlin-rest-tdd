package dio.spring_kotlin_rest_tdd.factory

import dio.spring_kotlin_rest_tdd.dto.CreditDto
import dio.spring_kotlin_rest_tdd.model.Credit
import dio.spring_kotlin_rest_tdd.model.Customer
import dio.spring_kotlin_rest_tdd.model.type.DataTypes
import java.time.LocalDate
import kotlin.random.Random

class CreditFixture {

  companion object {
    fun create(
      id: Long = Random.nextLong(0, 1000),
      dayOfFirstInstallment: LocalDate = LocalDate.now().plusDays(1),
      status: DataTypes.Status = DataTypes.Status.IN_PROGRESS,
      numberOfInstallments: Int = 0,
      creditValue: Long = 1000000,
      customerId: Long?,
    ): Credit {
      return Credit(
        id,
        dayOfFirstInstallment = dayOfFirstInstallment,
        numberOfInstallments = numberOfInstallments,
        creditValue = creditValue,
        customerId = customerId,
        status = status
      )
    }
  }
}