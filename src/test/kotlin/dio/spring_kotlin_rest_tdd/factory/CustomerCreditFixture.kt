package dio.spring_kotlin_rest_tdd.factory

import dio.spring_kotlin_rest_tdd.dto.response.CustomerCreditDto
import dio.spring_kotlin_rest_tdd.model.type.DataTypes
import jakarta.validation.constraints.Email
import java.util.*

class CustomerCreditFixture {

  companion object {
    fun create(
      id: UUID = UUID.randomUUID(),
      status: DataTypes.Status = DataTypes.Status.IN_PROGRESS,
      numberOfInstallments: Int = 0,
      creditValue: Long = 1000000,
      customerId: Long,
      customerEmail: String,
      customerIncome: Long = 100000
    ): CustomerCreditDto {
      return CustomerCreditDto(
        id,
        customerId = customerId,
        numberOfInstallments = numberOfInstallments,
        creditValue = creditValue,
        status = status,
        customerEmail = customerEmail,
        customerIncome = customerIncome
      )
    }
  }
}