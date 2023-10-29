package dio.spring_kotlin_rest_tdd.controller

import dio.spring_kotlin_rest_tdd.dto.request.CustomerDto
import dio.spring_kotlin_rest_tdd.dto.request.CustomerUpdateDto
import dio.spring_kotlin_rest_tdd.model.Customer
import dio.spring_kotlin_rest_tdd.service.impl.CustomerServiceImplementation
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/customers")
class CustomerController(
  @Autowired private val customers: CustomerServiceImplementation
) {

  @PostMapping
  fun saveCustomer(@RequestBody @Valid data: CustomerDto): ResponseEntity<Customer> {
    val created = customers.save(data)
    return ResponseEntity.status(HttpStatus.CREATED).body(created)
  }

  @GetMapping("/{id}")
  fun findById(@PathVariable id: Long): ResponseEntity<Customer> {
    val response = customers.findById(id)
    return ResponseEntity.status(HttpStatus.OK).body(response)
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun deleteCustomer(@PathVariable id: Long) = customers.delete(id)

  @PatchMapping
  fun upadateCustomer(
    @RequestParam(value = "customerId") id: Long,
    @RequestBody customerData: CustomerUpdateDto
  ): ResponseEntity<Customer> {
    val response = customers.update(id, customerData)
    return ResponseEntity.status(HttpStatus.OK).body(response)
  }
}