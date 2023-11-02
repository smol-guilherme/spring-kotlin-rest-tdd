package dio.spring_kotlin_rest_tdd.handler

import feign.FeignException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ErrorHandler {

  @ExceptionHandler(IllegalArgumentException::class)
  fun <T: Throwable> handleIllegalArgument(ex: T): ResponseEntity<String> {
    val msg = "Error: ${ex.message} is required"
    return ResponseEntity(msg, HttpStatus.UNPROCESSABLE_ENTITY)
  }

  @ExceptionHandler(FeignException::class)
  fun <T: Throwable> handleInvalidPostalCode(ex: T): ResponseEntity<String> {
    val msg = "Error: Postal Code (CEP) is not valid"
    return ResponseEntity(msg, HttpStatus.BAD_REQUEST)
  }
}