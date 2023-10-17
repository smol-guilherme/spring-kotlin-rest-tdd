package dio.spring_kotlin_rest_tdd.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.validation.constraints.Email
import javax.naming.Name
import org.hibernate.validator.constraints.br.CPF

@Entity
data class Customer(
  @Id
  val id: Long,
  var firstName: Name,
  var lastName: String,
  val cpf: CPF,
  var email: Email,
  var cep: String,
  var address: Address
)
