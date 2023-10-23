package dio.spring_kotlin_rest_tdd.repository

import dio.spring_kotlin_rest_tdd.dto.response.CreditListDto
import dio.spring_kotlin_rest_tdd.dto.response.CustomerCreditDto
import dio.spring_kotlin_rest_tdd.model.Credit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CreditRepository: JpaRepository<Credit, Long> {

  fun findAllByCustomerId(customerId: Long): Iterable<CreditListDto>

  fun findOneByCustomerIdAndCreditId(customerId: Long, creditId: Long): Optional<CustomerCreditDto>
}