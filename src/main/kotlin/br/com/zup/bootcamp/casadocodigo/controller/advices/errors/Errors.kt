package br.com.zup.bootcamp.casadocodigo.controller.advices.errors

import org.springframework.context.MessageSource
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import java.util.*
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException


/**
 * Representa o conjunto de erros de uma entidade ou formulario
 */
class Errors {

    private val errors: MutableList<ValidationError> = ArrayList()

    /**
     * Registra novo erro para determinado campo
     */
    fun with(field: String?, message: String?): Errors {
        errors.add(ValidationError(field, message))
        return this
    }

    fun getErrors(): List<ValidationError> {
        return errors
    }

    companion object {

        fun of(ex: ConstraintViolationException): Errors {
            val violations = ex.constraintViolations
            return of(violations)
        }

        fun of(violations: Collection<ConstraintViolation<*>>): Errors {
            val errors = Errors()
            for (violation in violations) {
                val field = violation.propertyPath.toString()
                val message = violation.message
                errors.with(field, message)
            }
            return errors
        }

        fun of(result: BindingResult, locale: Locale, i18n: MessageSource): Errors {
            val fieldErrors = result.fieldErrors
            return of(fieldErrors, locale, i18n)
        }

        fun of(fieldErrors: List<FieldError>, locale: Locale, i18n: MessageSource): Errors {
            val errors = Errors()
            for (fieldError in fieldErrors) {
                val field = fieldError.field
                val message = i18n.getMessage(fieldError, locale)
                errors.with(field, message)
            }
            return errors
        }

    }
}