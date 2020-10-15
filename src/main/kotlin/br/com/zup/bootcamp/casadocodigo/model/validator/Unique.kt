package br.com.zup.bootcamp.casadocodigo.model.validator

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass


@MustBeDocumented
@Target(FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UniqueValueValidator::class])
annotation class Unique(
        val message: String = "{zup.unique.value.violation}",
        val groups: Array<KClass<Any>> = [],
        val payload: Array<KClass<Payload>> = [],
        val entityClass: KClass<*>,
        val fieldName: String
)