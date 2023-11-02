package dio.spring_kotlin_rest_tdd.factory.credit

import dio.spring_kotlin_rest_tdd.model.Credit
import dio.spring_kotlin_rest_tdd.model.Customer
import dio.spring_kotlin_rest_tdd.model.type.DataTypes
import java.time.LocalDate
import java.util.UUID

class CreditFixture {

  companion object {
    fun create(
      id: UUID = UUID.randomUUID(),
      dayOfFirstInstallment: LocalDate = LocalDate.now().plusDays(1),
      status: DataTypes.Status = DataTypes.Status.IN_PROGRESS,
      numberOfInstallments: Int = 0,
      creditValue: Long = 1000000,
      customer: Customer
    ): Credit {
      return Credit(
        id,
        customer = customer,
        dayOfFirstInstallment = dayOfFirstInstallment,
        numberOfInstallments = numberOfInstallments,
        creditValue = creditValue,
        status = status,
      )
    }
  }
}