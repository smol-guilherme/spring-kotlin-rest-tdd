package dio.spring_kotlin_rest_tdd.factory.credit

import dio.spring_kotlin_rest_tdd.dto.CreditDto
import dio.spring_kotlin_rest_tdd.model.Customer
import java.time.LocalDate
import kotlin.random.Random

class CreditDtoFixture() {
  companion object {
    fun create(
      customerId: Customer,
      creditValue: Long = Random.nextLong(10000, 100000),
      dayOfFirstInstallment: LocalDate = LocalDate.now().plusDays(90),
      numberOfInstallments: Int = Random.nextInt(1, 48),
    ): CreditDto {
      return CreditDto(
        customerId = customerId,
        numberOfInstallments = numberOfInstallments,
        dayOfFirstInstallment = dayOfFirstInstallment,
        creditValue = creditValue,
      )
    }
  }
}