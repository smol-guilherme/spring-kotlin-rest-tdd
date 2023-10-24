package dio.spring_kotlin_rest_tdd.factory.credit

import dio.spring_kotlin_rest_tdd.dto.response.CreditListDto

class CreditListFixture() {

  companion object {
    fun create(
      customerId: Long,
      creditValue: Long,
      numberOfInstallments: Int,
    ): CreditListDto {
      return CreditListDto(
        customerId = customerId,
        numberOfInstallments = numberOfInstallments,
        creditValue = creditValue,
      )
    }
  }
}