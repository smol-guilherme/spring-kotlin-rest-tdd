package dio.spring_kotlin_rest_tdd.service

import dio.spring_kotlin_rest_tdd.model.Credit
import java.time.LocalDate
import java.util.Optional

interface CreditService {

  fun register(params: Credit)

  fun listAll(): Iterable<Credit>

  fun listOne(id: Long): Optional<Credit>

  fun requestOne(customerId: Long, creditValue: Int, dayOfFirstInstallment: LocalDate, numberOfInstallments: Int): String
}