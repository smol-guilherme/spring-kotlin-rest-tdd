package dio.spring_kotlin_rest_tdd.factory.credit

import dio.spring_kotlin_rest_tdd.dto.response.CreditListDto
import dio.spring_kotlin_rest_tdd.model.type.DataTypes

class CreditListFixture() {

  companion object {
    fun create(
      customerId: Long,
      creditValue: Long,
      numberOfInstallments: Int,
      status: DataTypes.Status = DataTypes.Status.IN_PROGRESS
    ): CreditListDto {
      return CreditListDto(
        customerId = customerId,
        numberOfInstallments = numberOfInstallments,
        creditValue = creditValue,
        status = status
      )
    }
  }
}