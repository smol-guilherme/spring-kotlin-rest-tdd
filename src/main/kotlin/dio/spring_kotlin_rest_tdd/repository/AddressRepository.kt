package dio.spring_kotlin_rest_tdd.repository

import dio.spring_kotlin_rest_tdd.model.Address
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AddressRepository : CrudRepository<Address, String> {

}
