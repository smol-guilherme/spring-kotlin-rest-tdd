package dio.spring_kotlin_rest_tdd.factory.customer

import dio.spring_kotlin_rest_tdd.model.Address

class AddressFixture {

  companion object {
    fun create(
      cep: String = "70150900",
      streetAddress: String? = "",
      complement: String? = "",
      number: String? = "",
      neighborhood: String? = "",
      city: String? = "",
      state: String? = "",
      ibge: String? = "",
      gia: String? = "",
      ddd: String? = "",
      siafi: String? = ""
    ): Address {
      return Address(
        cep = cep,
        streetAddress = streetAddress,
        complement = complement,
        number = number,
        neighborhood = neighborhood,
        city = city,
        state = state,
        ibge = ibge,
        gia = gia,
        ddd = ddd,
        siafi = siafi
      )
    }
  }
}