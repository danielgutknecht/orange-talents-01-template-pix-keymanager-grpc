package br.com.zup.pix.service

import br.com.zup.pix.client.BCBClient
import br.com.zup.pix.client.ItauERPClient
import br.com.zup.pix.client.bcb.BCBCreatePixKeyRequest
import br.com.zup.pix.endpoint.entry.EntryCreateKey
import br.com.zup.pix.exception.types.AlreadyExistsException
import br.com.zup.pix.exception.types.InternalException
import br.com.zup.pix.exception.types.NotFoundException
import br.com.zup.pix.model.PixKey
import br.com.zup.pix.model.enums.KeyType
import br.com.zup.pix.repository.KeyRepository
import io.micronaut.http.HttpStatus
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid
import javax.validation.ValidationException
import br.com.zup.pix.KeyType as ReceiverKeyType

@Validated
@Singleton
class CreateKeyService(
    @Inject private val keyRepository: KeyRepository,
    @Inject private val erpClient: ItauERPClient,
    @Inject private val bcbClient: BCBClient
) {

    private val logger = LoggerFactory.getLogger(CreateKeyService::class.java)

    @Transactional
    fun persistKey(@Valid entry: EntryCreateKey): PixKey {
        val keyValue = entry.value!!
        val clientId = entry.clientId!!
        val accountType = entry.accountType!!

        if (keyRepository.existsByKeyValue(keyValue))
            throw AlreadyExistsException("Key $keyValue is already registered.")

        val body = erpClient.getAccount(clientId, accountType.name).body()
            ?: throw NotFoundException("Account id $clientId and type ${accountType.name} was not found")

        if (entry.type!! == ReceiverKeyType.CPF && body.owner.cpf != keyValue)
            throw ValidationException("This CPF does not belong to the informed user.")

        val bankAccount = body.toModel()
        val pixKey = entry.toModel(bankAccount)

        keyRepository.save(pixKey)

        val bcbResponse = bcbClient.createKey(BCBCreatePixKeyRequest.byPixKey(pixKey))
        if (bcbResponse.status != HttpStatus.CREATED) {
            throw InternalException("We had a problem registering your key")
        }

        logger.info("Pix key registered successfully.")

        if (pixKey.keyType == KeyType.RANDOM) {
            pixKey.updateKey(bcbResponse.body()!!.key)
        }

        return pixKey
    }

}