package dio.spring_kotlin_rest_tdd.dto.request

data class CustomerDto(
  var firstName: String,
  var lastName: String,
  var income: Long = 100000,
  val cpf: String,
  var email: String,
  var cep: String,
) {

}