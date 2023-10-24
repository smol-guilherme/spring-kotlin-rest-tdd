package dio.spring_kotlin_rest_tdd.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.validation.constraints.Email

@Entity
data class Customer(
  @Id
  val id: Long,
  var firstName: String,
  var lastName: String,
  var income: Long = 100000,
  val cpf: String,
  var email: String,
  var cep: String,
  var address: Address
)
