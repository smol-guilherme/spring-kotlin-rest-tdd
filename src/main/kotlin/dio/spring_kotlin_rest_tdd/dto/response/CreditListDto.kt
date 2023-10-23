package dio.spring_kotlin_rest_tdd.dto.response

import dio.spring_kotlin_rest_tdd.model.type.DataTypes

class CreditListDto(
  val customerId: Long,
  val creditValue: Long,
  val numberOfInstallments: Int,
) {
}