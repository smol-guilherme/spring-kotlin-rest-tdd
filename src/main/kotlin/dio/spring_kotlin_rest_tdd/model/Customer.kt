package dio.spring_kotlin_rest_tdd.model

import dio.spring_kotlin_rest_tdd.dto.request.CustomerDto
import dio.spring_kotlin_rest_tdd.dto.request.CustomerUpdateDto
import dio.spring_kotlin_rest_tdd.exception.BusinessException
import jakarta.persistence.*
import jakarta.validation.constraints.Email

@Entity
data class Customer(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column val id: Long? = null,
  @Column(nullable = false) var firstName: String,
  @Column(nullable = false) var lastName: String,
  @Column(nullable = false) var income: Long = 100000,
  @Column(unique = true, nullable = false) val cpf: String,
  @Column(unique = true, nullable = false) var email: String,
  @Column(nullable = false) var cep: String,
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
        firstName = handler(originalData.firstName, data?.firstName)!!,
        lastName = handler(originalData.lastName, data?.lastName)!!,
        income = handler(originalData.income, data?.income)!!,
        cpf = handler(originalData.cpf, data?.cpf)!!,
        email = handler(originalData.email, data?.email)!!,
        cep = handler(originalData.cep, data?.cep)!!,
        address = null
      )
    }

    private fun <T> handler(original: T, new: T): T {
      return when (original) {
        is String -> new.toString().ifEmpty { original } as T
        is Long -> new ?: original as T
        else -> { throw BusinessException("An unexpected error occurred.") }
      }
    }
  }

}
