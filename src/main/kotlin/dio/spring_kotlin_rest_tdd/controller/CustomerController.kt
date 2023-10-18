package dio.spring_kotlin_rest_tdd.controller

import dio.spring_kotlin_rest_tdd.service.impl.CustomerServiceImplementation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/customers")
class CustomerController(
  @Autowired private val customer: CustomerServiceImplementation
) {
}