package dio.spring_kotlin_rest_tdd.model

import jakarta.annotation.Nullable
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Address(
  @Id
  val cep: String,
  val streetAddress: String?,
  @Nullable
  val complement: String?,
  var number: String?,
  val neighborhood: String?,
  val city: String?,
  val state: String?,
  val ibge: String?,
  val gia: String?,
  val ddd: String?,
  val siafi: String?
) {
}