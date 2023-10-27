package dio.spring_kotlin_rest_tdd.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Address(
  @Id @Column(nullable = false, unique = true)
  val cep: String,
  @Column val streetAddress: String?,
  @Column(nullable = true)
  val complement: String?,
  @Column var number: String?,
  @Column val neighborhood: String?,
  @Column val city: String?,
  @Column val state: String?,
  @Column val ibge: String?,
  @Column val gia: String?,
  @Column val ddd: String?,
  @Column val siafi: String?
) {
}