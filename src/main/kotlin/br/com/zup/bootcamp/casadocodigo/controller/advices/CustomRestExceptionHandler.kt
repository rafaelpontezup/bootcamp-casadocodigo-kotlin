package br.com.zup.bootcamp.casadocodigo.controller.advices

import br.com.zup.bootcamp.casadocodigo.controller.advices.errors.Errors
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.context.MessageSource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*

/**
 * https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
 * https://neetkee.com/posts/kotlin-json-validation/
 * https://www.baeldung.com/exception-handling-for-rest-with-spring
 * https://stackoverflow.com/questions/54797207/spring-not-null-validation-throwing-httpmessagenotreadableexception-instead-of-m
 * https://github.com/valiktor/valiktor/blob/622a2b967d152edcea0056716d222d660013a636/valiktor-spring/valiktor-spring/src/main/kotlin/org/valiktor/springframework/web/controller/MissingKotlinParameterExceptionHandler.kt
 * https://stackoverflow.com/questions/28350445/where-does-the-default-json-errors-response-in-spring-boot-starter-web-comes-fro
 * https://blog.jayway.com/2013/02/03/improve-your-spring-rest-api-part-iii/
 * https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html#mvc-ann-rest-spring-mvc-exceptions
 */
@RestControllerAdvice
class CustomRestExceptionHandler(val messageSource: MessageSource) : ResponseEntityExceptionHandler() {

    /**
     * Customiza response de erro quando ocorrer erro de validação
     * (<code>@Valid</code>) no controller
     */
    override fun handleMethodArgumentNotValid(
            ex: MethodArgumentNotValidException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest
    ): ResponseEntity<Any> {

        val locale: Locale = request.locale
        val result: BindingResult = ex.bindingResult

        val errors: Errors = Errors.of(result, locale, messageSource)

        return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errors);
    }

    override fun handleHttpMessageNotReadable(
            ex: HttpMessageNotReadableException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest
    ): ResponseEntity<Any> {

        /**
         *  workaround
         *  https://stackoverflow.com/questions/54797207/spring-not-null-validation-throwing-httpmessagenotreadableexception-instead-of-m
         */
        val cause = ex.cause
        if (cause is MissingKotlinParameterException) {
            val missingException: MissingKotlinParameterException = cause
            val fieldName = missingException.path.joinToString(separator = ".") { it.fieldName }

            val errors = Errors().with(fieldName, "must not be null")
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errors);
        }

        return super.handleHttpMessageNotReadable(ex, headers, status, request)
    }

    /**
     * Não funciona quando estendemos a classe ResponseEntityExceptionHandler. De alguma forma quem
     * trata esse erro é a ExceptionHandlerExceptionResolver
     */
//    @ExceptionHandler(value = [MissingKotlinParameterException::class])
//    fun handleMissingKotlinParameter(exception: MissingKotlinParameterException): ResponseEntity<Any> {
//
//        val fieldName = exception.path.joinToString(separator = ".") { it.fieldName }
//
//        val errors = Errors().with(fieldName, "must not be null")
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(errors);
//    }

}