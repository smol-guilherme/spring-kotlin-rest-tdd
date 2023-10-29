package dio.spring_kotlin_rest_tdd.dto.request

import dio.spring_kotlin_rest_tdd.model.Address

data class CustomerUpdateDto(
   var firstName: String?,
   var lastName: String?,
   var income: Long? = null,
   val cpf: String?,
   var email: String?,
   var cep: String?,
   var address: Address?
) {
}