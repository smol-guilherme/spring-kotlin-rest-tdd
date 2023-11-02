package dio.spring_kotlin_rest_tdd.service

import dio.spring_kotlin_rest_tdd.dto.request.CustomerDto
import dio.spring_kotlin_rest_tdd.model.Customer
import java.util.*

interface CustomerService {

  fun findById(id: Long): Customer

  fun save(cst: CustomerDto): Customer

  fun delete(id: Long)

}