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
    val customerData = Customer(customer, address = saveCustomerWithAddress(customer.cep))
    return repository.save(customerData)
  }

  override fun delete(id: Long) {
    val customer = this.findById(id)
    repository.delete(customer)
  }

  internal fun saveCustomerWithAddress(data: String): Address {
    return execute(data!!)
  }

  private fun execute(cep: String): Address {
    println("Banana: $cep")
    val address = addresses.findById(cep).orElseGet {
      val newAddress: Address = viaCep.enquiryCep(cep)
      addresses.save(newAddress)
      return@orElseGet newAddress
    }
    return address
  }

  fun update(id: Long, updateData: CustomerUpdateDto): Customer {
    val original = this.findById(id)
    val completeData = Customer.updateEntity(updateData, original)
    completeData.address = saveCustomerWithAddress(completeData.cep)
    if (completeData == original) return completeData
    return repository.save(completeData)
  }
}