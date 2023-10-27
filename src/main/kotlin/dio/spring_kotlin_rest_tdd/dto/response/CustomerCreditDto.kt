package dio.spring_kotlin_rest_tdd.dto.response

import dio.spring_kotlin_rest_tdd.model.type.DataTypes.Status
import jakarta.validation.constraints.Email
import java.util.*

data class CustomerCreditDto(
  val id: UUID,
  val customerId: Long,
  val creditValue: Long,
  val status: Status,
  val numberOfInstallments: Int,
  @Email
  val customerEmail: String,
  val customerIncome: Long
) {
}