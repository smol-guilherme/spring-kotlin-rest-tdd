package dio.spring_kotlin_rest_tdd.dto

import java.time.LocalDate

data class CreditDto(
  val customerId: Long,
  val creditValue: Long,
  val dayOfFirstInstallment: LocalDate,
  val numberOfInstallments: Int,
) {
}