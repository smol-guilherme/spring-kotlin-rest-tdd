package dio.spring_kotlin_rest_tdd.repository

import dio.spring_kotlin_rest_tdd.model.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository: JpaRepository<Customer, Long> {

}
