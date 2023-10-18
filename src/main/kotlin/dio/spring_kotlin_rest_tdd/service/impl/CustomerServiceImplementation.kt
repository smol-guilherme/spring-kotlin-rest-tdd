package dio.spring_kotlin_rest_tdd.service.impl

import dio.spring_kotlin_rest_tdd.exception.BusinessException
import dio.spring_kotlin_rest_tdd.model.Customer
import dio.spring_kotlin_rest_tdd.repository.CustomerRepository
import dio.spring_kotlin_rest_tdd.service.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.util.*
import kotlin.NoSuchElementException

@Service
class CustomerServiceImplementation(
  private val repository: CustomerRepository
): CustomerService {

  override fun findById(id: Long): Customer {
    return repository.findById(id).orElseThrow { throw BusinessException("No user $id found") }
  }

  override fun save(customer: Customer): Customer {
    return repository.save(customer)
  }

  override fun update(id: Long, cst: Customer) {
    TODO("Not yet implemented")
  }

  override fun delete(id: Long) {
    val customer = this.findById(id)
    repository.delete(customer)
  }
}