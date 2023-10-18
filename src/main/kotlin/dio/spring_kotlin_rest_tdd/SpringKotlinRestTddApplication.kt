package dio.spring_kotlin_rest_tdd

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class SpringKotlinRestTddApplication

fun main(args: Array<String>) {
	runApplication<SpringKotlinRestTddApplication>(*args)
}
