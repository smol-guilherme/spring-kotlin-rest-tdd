package dio.spring_kotlin_rest_tdd.model

import jakarta.persistence.*
import jakarta.validation.constraints.Email

@Entity
data class Customer(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column val id: Long? = null,
  @Column var firstName: String,
  @Column var lastName: String,
  @Column var income: Long = 100000,
  @Column val cpf: String,
  @Column var email: String,
  @Column var cep: String,
  @ManyToOne
  var address: Address,
  @OneToMany(
    fetch = FetchType.LAZY,
    cascade = [CascadeType.REMOVE, CascadeType.PERSIST],
    mappedBy = "customer"
  )
  var credits: List<Credit> = mutableListOf(),
)
