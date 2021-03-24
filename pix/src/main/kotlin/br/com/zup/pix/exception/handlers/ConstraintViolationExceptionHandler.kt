package br.com.zup.pix.exception.handlers

import br.com.zup.pix.exception.ExceptionHandler
import br.com.zup.pix.exception.ExceptionHandler.StatusWithDetails
import io.grpc.Status
import java.lang.Exception
import javax.inject.Singleton
import javax.validation.ConstraintViolationException

@Singleton
class ConstraintViolationExceptionHandler : ExceptionHandler<ConstraintViolationException> {

    override fun handle(e: ConstraintViolationException): StatusWithDetails =
        StatusWithDetails(
            Status.INVALID_ARGUMENT
                .withDescription(e.message)
                .withCause(e)
        )

    override fun supports(e: Exception): Boolean = e is ConstraintViolationException
}