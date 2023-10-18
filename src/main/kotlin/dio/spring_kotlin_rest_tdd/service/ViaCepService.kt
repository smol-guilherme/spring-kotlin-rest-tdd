package dio.spring_kotlin_rest_tdd.service

import dio.spring_kotlin_rest_tdd.model.Address
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
interface ViaCepService {

  @GetMapping("/{cep}/json/")
  fun enquiryCep(@PathVariable("cep") cep: String): Address
}