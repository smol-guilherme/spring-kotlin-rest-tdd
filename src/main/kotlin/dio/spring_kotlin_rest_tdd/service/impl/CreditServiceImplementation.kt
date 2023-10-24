package dio.spring_kotlin_rest_tdd.service.impl

import dio.spring_kotlin_rest_tdd.dto.CreditDto
import dio.spring_kotlin_rest_tdd.dto.response.CreditListDto
import dio.spring_kotlin_rest_tdd.dto.response.CustomerCreditDto
import dio.spring_kotlin_rest_tdd.exception.BusinessException
import dio.spring_kotlin_rest_tdd.model.Credit
import dio.spring_kotlin_rest_tdd.repository.CreditRepository
import dio.spring_kotlin_rest_tdd.repository.CustomerRepository
import dio.spring_kotlin_rest_tdd.service.CreditService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class CreditServiceImplementation(
  private val repository: CreditRepository,
  private val customers: CustomerRepository
): CreditService {
  override fun requestOne(
    params: CreditDto
  ): String {
    validate(params)
    val dtoToObject = Credit(
      customerId = params.customerId,
      dayOfFirstInstallment = params.dayOfFirstInstallment,
      numberOfInstallments = params.numberOfInstallments,
      creditValue = params.creditValue
    )
    repository.save(dtoToObject)
    return "Done Deal"
  }

  override fun listOne(creditId: UUID, customerId: Long): Optional<CustomerCreditDto> {
    validate(customerId)
    return repository.findOneByCustomerIdAndCreditId(creditId, customerId)
  }

  override fun listAllFromCustomer(customerId: Long): Iterable<CreditListDto> {
    TODO()
  }

  internal fun <T> validate(data: T) {
    when (data) {
      is Long -> {
        if(customers.findById(data).isEmpty) throw BusinessException("No Customer found for ID: $data")
        return
      }
      is UUID -> {
        if(repository.findById(data).isEmpty) throw BusinessException("No Credit found for signature: $data")
      }
      is CreditDto -> {
        if(data.dayOfFirstInstallment > LocalDate.now().plusDays(91)) {
          throw BusinessException("First installment must be at most 90 days prior to the current date")
        }
        if(data.creditValue < 0) {
          throw BusinessException("Credit must be a positive number\n ${data.creditValue} was provided")
        }
        if(data.numberOfInstallments !in 1..48) {
          throw BusinessException("Number of installments must be at most 48\n${data.numberOfInstallments} was provided")
        }
        if(customers.findById(data.customerId).isEmpty) {
          throw BusinessException("No Customer found for ID: $data")
        }
      }
    }
  }
}