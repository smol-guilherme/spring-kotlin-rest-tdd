package dio.spring_kotlin_rest_tdd.dto.response

import jakarta.validation.constraints.Email
import dio.spring_kotlin_rest_tdd.model.type.DataTypes.Status

data class CustomerCreditDto(
  val customerId: Long,
  val creditValue: Long,
  val status: Status,
  val numberOfInstallments: Int,
  val customerEmail: Email,
  val customerIncome: Long
) {
}