package dio.spring_kotlin_rest_tdd.model

import dio.spring_kotlin_rest_tdd.dto.request.CustomerDto
import dio.spring_kotlin_rest_tdd.dto.request.CustomerUpdateDto
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
  var address: Address?,
  @OneToMany(
    fetch = FetchType.LAZY,
    cascade = [CascadeType.REMOVE, CascadeType.PERSIST],
    mappedBy = "customer"
  )
  var credits: List<Credit> = mutableListOf(),
) {
  constructor(data: CustomerDto, address: Address?) : this(
    firstName = data.firstName,
    lastName = data.lastName,
    income = data.income,
    cpf = data.cpf,
    email = data.email,
    cep = data.cep,
    address = address!!,
  )

  companion object {
    fun updateEntity(data: CustomerUpdateDto, originalData: Customer): Customer {
      return Customer(
        id = originalData.id!!,
        firstName = data.firstName ?: originalData.firstName,
        lastName = data.lastName ?: originalData.lastName,
        income = data.income ?: originalData.income,
        cpf = data.cpf ?: originalData.cpf,
        email = data.email ?: originalData.email,
        cep = data.cep ?: originalData.cep,
        address = null
      )
    }
  }

}
