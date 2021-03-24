package br.com.zup.pix.endpoint

import br.com.zup.pix.*
import br.com.zup.pix.endpoint.entry.*
import br.com.zup.pix.endpoint.reply.PixKeyResponse
import br.com.zup.pix.endpoint.reply.toFindPixKeyReply
import br.com.zup.pix.exception.ErrorHandler
import br.com.zup.pix.exception.types.InternalException
import br.com.zup.pix.exception.types.ValidationException
import br.com.zup.pix.service.CreateKeyService
import br.com.zup.pix.service.FindKeyService
import br.com.zup.pix.service.RemoveKeyService
import com.google.protobuf.Timestamp
import io.grpc.stub.StreamObserver
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

@ErrorHandler
@Singleton
class PixKeyEndpoint(
    @Inject private val createService: CreateKeyService,
    @Inject private val removeService: RemoveKeyService,
    @Inject private val findService: FindKeyService,
) : KeyManagerServiceGrpc.KeyManagerServiceImplBase() {

    override fun create(request: CreatePixKeyRequest, responseObserver: StreamObserver<CreatePixKeyReply>) {

        val entry = request.toEntryCreateKey()
        val pixKey = createService.persistKey(entry)

        responseObserver.onNext(
            CreatePixKeyReply.newBuilder()
                .setClientId(pixKey.bankAccount.owner.clientId.toString())
                .setPixId(pixKey.id.toString())
                .build()
        )
        responseObserver.onCompleted()
    }

    override fun remove(request: RemovePixKeyRequest, responseObserver: StreamObserver<RemovePixKeyReply>) {

        val entry = request.toEntryRemoveKey()
        removeService.removeKey(entry)

        responseObserver.onNext(
            RemovePixKeyReply.newBuilder()
                .setClientId(entry.clientId.toString())
                .setPixId(entry.pixId.toString())
                .build()
        )
        responseObserver.onCompleted()
    }

    override fun find(request: FindPixKeyRequest, responseObserver: StreamObserver<FindPixKeyReply>) {

        fun buildResponse(key: PixKeyResponse) {
            responseObserver.onNext(key.toFindPixKeyReply())
            responseObserver.onCompleted()
        }

        when (request.filterCase) {
            FindPixKeyRequest.FilterCase.KEY -> buildResponse(findService.findByKey(request.toEntryFindByKey()))
            FindPixKeyRequest.FilterCase.PIXID -> buildResponse(findService.findByPixId(request.toEntryFindByPixId()))

            FindPixKeyRequest.FilterCase.FILTER_NOT_SET ->
                throw ValidationException("The data entered in the request is insufficient")

            else -> throw InternalException("An internal error has occurred")
        }
    }

    override fun listAllByClient(
        request: ListAllPixKeysRequest,
        responseObserver: StreamObserver<ListAllPixKeysReply>
    ) {
        val entry = request.toEntryListKeys()
        val keys = findService.listAllByClient(entry)

        responseObserver.onNext(
            ListAllPixKeysReply.newBuilder()
                .setClientId(entry.clientId!!)
                .addAllKeys(keys.map {
                    ListAllPixKeysReply.PixKey.newBuilder()
                        .setPixId(it.id.toString())
                        .setKeyType(KeyType.valueOf(it.keyType.name))
                        .setKeyValue(it.keyValue)
                        .setAccountType(AccountType.valueOf(it.bankAccount.type.name))
                        .setCreatedAt(it.createdAt.let { createdAt ->
                            val instant = createdAt.atZone(ZoneId.of("UTC")).toInstant()
                            Timestamp.newBuilder()
                                .setSeconds(instant.epochSecond)
                                .setNanos(instant.nano)
                                .build()
                        }).build()
                }).build()
        )
        responseObserver.onCompleted()
    }
}