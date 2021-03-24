package br.com.zup.pix.exception

import io.grpc.Metadata
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.protobuf.StatusProto
import java.lang.Exception

interface ExceptionHandler<E: Exception> {

    fun handle(e: E): StatusWithDetails

    fun supports(e: Exception): Boolean

    data class StatusWithDetails(val status: Status, val metadata: Metadata = Metadata()) {
        constructor(exception: StatusRuntimeException): this(exception.status, exception.trailers ?: Metadata())
        constructor(status: com.google.rpc.Status): this(StatusProto.toStatusRuntimeException(status))

        fun asRuntimeException(): StatusRuntimeException = status.asRuntimeException(metadata)
    }

}