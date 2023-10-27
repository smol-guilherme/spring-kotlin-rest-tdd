package dio.spring_kotlin_rest_tdd.dto

import dio.spring_kotlin_rest_tdd.model.Customer
import java.time.LocalDate

data class CreditDto(
  val customerId: Customer,
  val creditValue: Long,
  val dayOfFirstInstallment: LocalDate,
  val numberOfInstallments: Int,
) {
}