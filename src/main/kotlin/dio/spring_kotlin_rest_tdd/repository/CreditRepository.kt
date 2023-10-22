package dio.spring_kotlin_rest_tdd.repository

import dio.spring_kotlin_rest_tdd.model.Credit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CreditRepository: JpaRepository<Credit, Long> {

}