package br.com.zup.pix.exception.handlers

import br.com.zup.pix.exception.ExceptionHandler
import io.grpc.Status
import javax.inject.Singleton

@Singleton
class DefaultExceptionHandler: ExceptionHandler<Exception> {

    override fun handle(e: Exception): ExceptionHandler.StatusWithDetails =
        ExceptionHandler.StatusWithDetails(
            Status.UNKNOWN
                .withDescription(e.message)
                .withCause(e)
        )

    override fun supports(e: java.lang.Exception): Boolean = false
}