package dio.spring_kotlin_rest_tdd.service

import dio.spring_kotlin_rest_tdd.model.Customer
import java.util.*

interface CustomerService {

  fun findAll(): Iterable<Customer>

  fun findById(id: Long): Customer

  fun save(cst: Customer): Customer

  fun update(id: Long, cst: Customer)

  fun delete(id: Long)

}