package dio.spring_kotlin_rest_tdd.service.impl

import dio.spring_kotlin_rest_tdd.dto.CreditDto
import dio.spring_kotlin_rest_tdd.exception.BusinessException
import dio.spring_kotlin_rest_tdd.model.Credit
import dio.spring_kotlin_rest_tdd.repository.CreditRepository
import dio.spring_kotlin_rest_tdd.repository.CustomerRepository
import dio.spring_kotlin_rest_tdd.service.CreditService
import java.lang.IllegalArgumentException
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

  override fun listAll(): Iterable<Credit> {
    TODO("Not yet implemented")
  }

  override fun listOne(id: Long): Optional<Credit> {
    TODO("Not yet implemented")
  }

  internal fun validate(data: CreditDto): Boolean {
    if(data.dayOfFirstInstallment > LocalDate.now().plusDays(91)) throw BusinessException("bottomtext")
    if(data.creditValue < 0) throw BusinessException("waytoodank")
    if(data.numberOfInstallments !in 1..48) throw BusinessException("feelsbadman")
    if(customers.findById(data.customerId).isEmpty) throw BusinessException("whosthere")
    return true
  }
}