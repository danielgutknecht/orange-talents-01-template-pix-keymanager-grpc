package br.com.zup.pix.exception.handlers

import br.com.zup.pix.exception.ExceptionHandler
import br.com.zup.pix.exception.ExceptionHandler.StatusWithDetails
import br.com.zup.pix.exception.types.AlreadyExistsException
import io.grpc.Status
import java.lang.Exception
import javax.inject.Singleton

@Singleton
class AlreadyExistsExceptionHandler : ExceptionHandler<AlreadyExistsException> {

    override fun handle(e: AlreadyExistsException): StatusWithDetails =
        StatusWithDetails(
            Status.ALREADY_EXISTS
                .withDescription(e.message)
                .withCause(e)
        )

    override fun supports(e: Exception): Boolean = e is AlreadyExistsException
}