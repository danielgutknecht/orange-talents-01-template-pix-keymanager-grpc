package br.com.zup.pix.exception.handlers

import br.com.zup.pix.exception.ExceptionHandler
import br.com.zup.pix.exception.ExceptionHandler.StatusWithDetails
import br.com.zup.pix.exception.types.InternalException
import io.grpc.Status
import java.lang.Exception

class InternalExceptionHandler: ExceptionHandler<InternalException> {

    override fun handle(e: InternalException): StatusWithDetails =
        StatusWithDetails(
            Status.INTERNAL
                .withDescription(e.message)
                .withCause(e)
        )

    override fun supports(e: Exception): Boolean = e is InternalException
}