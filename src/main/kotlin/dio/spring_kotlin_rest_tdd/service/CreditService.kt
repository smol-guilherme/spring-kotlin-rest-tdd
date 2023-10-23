package dio.spring_kotlin_rest_tdd.service

import dio.spring_kotlin_rest_tdd.dto.CreditDto
import dio.spring_kotlin_rest_tdd.model.Credit
import java.time.LocalDate
import java.util.Optional

interface CreditService {

  fun listAll(): Iterable<Credit>

  fun listOne(id: Long): Optional<Credit>

  fun requestOne(creditDto: CreditDto): String
}