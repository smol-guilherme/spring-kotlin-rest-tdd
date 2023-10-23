package dio.spring_kotlin_rest_tdd.model

import dio.spring_kotlin_rest_tdd.model.type.DataTypes.Status
import jakarta.persistence.*
import java.time.LocalDate

@Entity
data class Credit(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,
  var status: Status = Status.IN_PROGRESS,
  val dayOfFirstInstallment: LocalDate = LocalDate.now().plusDays(1),
  val numberOfInstallments: Int = 0,
  val creditValue: Long,
  @ManyToOne
  val customerId: Long? = null,
  ) {}
