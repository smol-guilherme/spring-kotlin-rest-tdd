package dio.spring_kotlin_rest_tdd.repository

import dio.spring_kotlin_rest_tdd.dto.response.CreditListDto
import dio.spring_kotlin_rest_tdd.dto.response.CustomerCreditDto
import dio.spring_kotlin_rest_tdd.model.Credit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CreditRepository: JpaRepository<Credit, UUID> {

  fun findAllByCustomerId(customerId: Long): Iterable<CreditListDto>
  fun findOneByIdAndCustomerId(id: UUID, customerId: Long): Optional<CustomerCreditDto>
  fun findOneById(id: UUID): Optional<Credit>
}