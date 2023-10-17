package dio.spring_kotlin_rest_tdd.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Customer(
  @Id
  val id: Long
)
