package dio.spring_kotlin_rest_tdd.model

import dio.spring_kotlin_rest_tdd.model.type.DataTypes.Status
import jakarta.persistence.*
import java.time.LocalDate
import java.util.*

@Entity
data class Credit(
  @Id @GeneratedValue(strategy = GenerationType.UUID)
  @Column val id: UUID? = null,
  @Column var status: Status = Status.IN_PROGRESS,
  @Column val dayOfFirstInstallment: LocalDate = LocalDate.now().plusDays(1),
  @Column val numberOfInstallments: Int = 0,
  @Column val creditValue: Long,
  @ManyToOne
  val customer: Customer,
  ) {}
