package dio.spring_kotlin_rest_tdd.service.impl

import dio.spring_kotlin_rest_tdd.dto.request.CustomerDto
import dio.spring_kotlin_rest_tdd.dto.request.CustomerUpdateDto
import dio.spring_kotlin_rest_tdd.exception.BusinessException
import dio.spring_kotlin_rest_tdd.model.Address
import dio.spring_kotlin_rest_tdd.model.Credit
import dio.spring_kotlin_rest_tdd.model.Customer
import dio.spring_kotlin_rest_tdd.repository.AddressRepository
import dio.spring_kotlin_rest_tdd.repository.CustomerRepository
import dio.spring_kotlin_rest_tdd.service.CustomerService
import dio.spring_kotlin_rest_tdd.service.ViaCepService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CustomerServiceImplementation(
  @Autowired private val repository: CustomerRepository,
  @Autowired private val viaCep: ViaCepService,
  @Autowired private val addresses: AddressRepository
): CustomerService {

  override fun findById(id: Long): Customer {
    return repository.findById(id).orElseThrow { throw BusinessException("No user $id found") }
  }

  override fun save(customer: CustomerDto): Customer {
    val customerData = Customer(customer, address = saveCustomerWithAddress(customer))
    return repository.save(customerData)
  }

  override fun delete(id: Long) {
    val customer = this.findById(id)
    repository.delete(customer)
  }

  internal fun <T> saveCustomerWithAddress(data: T): Address {
    return when (data) {
      is CustomerDto -> execute(data.cep!!)
      is CustomerUpdateDto -> execute(data.cep!!)
      else -> throw IllegalArgumentException("Invalid CEP")
    }
  }

  private fun execute(cep: String): Address {
    val address = addresses.findById(cep).orElseGet {
      val newAddress: Address = viaCep.enquiryCep(cep)
      addresses.save(newAddress)
      return@orElseGet newAddress
    }
    return address
  }

  fun update(id: Long, updateData: CustomerUpdateDto): Customer {
    val original = this.findById(id)
    val updatedData = Customer.updateEntity(updateData, original)
    updatedData.address = saveCustomerWithAddress(updatedData.cep)
    return repository.save(updatedData)
  }
}