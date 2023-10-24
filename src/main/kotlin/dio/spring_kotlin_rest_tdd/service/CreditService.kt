package dio.spring_kotlin_rest_tdd.service

import dio.spring_kotlin_rest_tdd.dto.CreditDto
import dio.spring_kotlin_rest_tdd.dto.response.CreditListDto
import dio.spring_kotlin_rest_tdd.dto.response.CustomerCreditDto
import dio.spring_kotlin_rest_tdd.model.Credit
import java.util.*

interface CreditService {

  fun listAllFromCustomer(customerId: Long): Iterable<CreditListDto>

  fun listOne(creditId: UUID, customerId: Long): Optional<CustomerCreditDto>

  fun requestOne(creditDto: CreditDto): String
}