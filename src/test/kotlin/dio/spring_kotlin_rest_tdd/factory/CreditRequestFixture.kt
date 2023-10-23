package dio.spring_kotlin_rest_tdd.factory

import dio.spring_kotlin_rest_tdd.dto.CreditDto
import dio.spring_kotlin_rest_tdd.model.Credit
import dio.spring_kotlin_rest_tdd.model.Customer
import java.time.LocalDate
import kotlin.random.Random

class CreditRequestFixture {

  companion object {
    fun create(
      dayOfFirstInstallment: LocalDate = LocalDate.now().plusDays(1),
      numberOfInstallments: Int = 0,
      creditValue: Long = 1000000,
      customerId: Long? = null,
    ): CreditDto {
      return CreditDto(
        dayOfFirstInstallment = dayOfFirstInstallment,
        numberOfInstallments = numberOfInstallments,
        creditValue = creditValue,
        customerId = customerId!!
      )
    }
  }
}