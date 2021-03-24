package br.com.zup.pix.validator

import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import java.util.*
import javax.inject.Singleton
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.KClass

@MustBeDocumented
@Target(FIELD, CONSTRUCTOR, PROPERTY, VALUE_PARAMETER)
@Retention(RUNTIME)
@Constraint(validatedBy = [UniqueIdValidator::class])
annotation class ValidUniqueId(
    val message: String = "The UUID value has an invalid format",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)

@Singleton
class UniqueIdValidator : ConstraintValidator<ValidUniqueId, String> {

    override fun isValid(
        value: String?,
        annotationMetadata: AnnotationValue<ValidUniqueId>,
        context: ConstraintValidatorContext
    ): Boolean {
        if (value == null) {
            return true
        }
        return try {
            UUID.fromString(value)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}