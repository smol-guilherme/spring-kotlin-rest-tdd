package dio.spring_kotlin_rest_tdd.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.br.CPF

data class CustomerDto(
  @field:NotEmpty var firstName: String,
  @field:NotEmpty var lastName: String,
  @field:NotNull var income: Long = 100000,
  @field:CPF val cpf: String,
  @field:Email var email: String,
  @field:NotEmpty var cep: String,
) {

}