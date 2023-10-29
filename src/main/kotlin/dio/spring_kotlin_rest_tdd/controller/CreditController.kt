package dio.spring_kotlin_rest_tdd.controller

import dio.spring_kotlin_rest_tdd.dto.CreditDto
import dio.spring_kotlin_rest_tdd.dto.response.CreditListDto
import dio.spring_kotlin_rest_tdd.dto.response.CustomerCreditDto
import dio.spring_kotlin_rest_tdd.service.impl.CreditServiceImplementation
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/credits")
class CreditController(
  @Autowired private val credits: CreditServiceImplementation
) {

  @PostMapping
  fun saveCredit(@RequestBody @Valid data: CreditDto): ResponseEntity<String> {
    val response = credits.requestOne(data)
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(response)
  }

  @GetMapping
  fun findAllByCustomerId(@RequestParam(value = "customerId") customerId: Long):
      ResponseEntity<Iterable<CreditListDto>> {
    val response = credits.listAllFromCustomer(customerId)
    return ResponseEntity.status(HttpStatus.OK).body(response)
  }

  @GetMapping("/{creditCode}")
  fun findByCreditCode(
    @RequestParam(value = "customerId") customerId: Long,
    @PathVariable creditCode: UUID
  ): ResponseEntity<CustomerCreditDto> {
    // mudar esse throw
    val response = credits.listOne(creditCode, customerId).orElseThrow { IllegalArgumentException() }
    return ResponseEntity.status(HttpStatus.OK).body(response)
  }

}