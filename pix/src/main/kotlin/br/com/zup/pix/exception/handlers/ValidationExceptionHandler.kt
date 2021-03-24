package br.com.zup.pix.exception.handlers

import br.com.zup.pix.exception.ExceptionHandler
import br.com.zup.pix.exception.ExceptionHandler.StatusWithDetails
import br.com.zup.pix.exception.types.ValidationException
import io.grpc.Status
import java.lang.Exception
import javax.inject.Singleton

@Singleton
class ValidationExceptionHandler : ExceptionHandler<ValidationException> {

    override fun handle(e: ValidationException): StatusWithDetails =
        StatusWithDetails(
            Status.INVALID_ARGUMENT
                .withDescription(e.message)
                .withCause(e)
        )

    override fun supports(e: Exception): Boolean = e is ValidationException
}