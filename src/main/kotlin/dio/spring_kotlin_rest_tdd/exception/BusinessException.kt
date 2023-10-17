package dio.spring_kotlin_rest_tdd.exception

data class BusinessException(override val message: String?) : RuntimeException(message) {
}