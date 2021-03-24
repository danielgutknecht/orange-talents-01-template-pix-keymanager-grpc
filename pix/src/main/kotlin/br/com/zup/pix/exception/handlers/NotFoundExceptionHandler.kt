package br.com.zup.pix.exception.handlers

import br.com.zup.pix.exception.ExceptionHandler
import br.com.zup.pix.exception.ExceptionHandler.StatusWithDetails
import br.com.zup.pix.exception.types.NotFoundException
import io.grpc.Status
import javax.inject.Singleton

@Singleton
class NotFoundExceptionHandler : ExceptionHandler<NotFoundException> {

    override fun handle(e: NotFoundException): StatusWithDetails =
        StatusWithDetails(
            Status.NOT_FOUND
                .withDescription(e.message)
                .withCause(e)
        )

    override fun supports(e: Exception): Boolean = e is NotFoundException
}