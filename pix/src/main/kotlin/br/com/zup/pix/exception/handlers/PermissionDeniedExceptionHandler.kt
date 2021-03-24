package br.com.zup.pix.exception.handlers

import br.com.zup.pix.exception.ExceptionHandler
import br.com.zup.pix.exception.ExceptionHandler.StatusWithDetails
import br.com.zup.pix.exception.types.PermissionDeniedException
import io.grpc.Status
import javax.inject.Singleton

@Singleton
class PermissionDeniedExceptionHandler : ExceptionHandler<PermissionDeniedException> {

    override fun handle(e: PermissionDeniedException): StatusWithDetails =
        StatusWithDetails(
            Status.PERMISSION_DENIED
                .withDescription(e.message)
                .withCause(e)
        )

    override fun supports(e: Exception): Boolean = e is PermissionDeniedException
}