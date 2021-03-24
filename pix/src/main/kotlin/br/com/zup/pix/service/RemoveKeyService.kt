package br.com.zup.pix.service

import br.com.zup.pix.client.BCBClient
import br.com.zup.pix.client.ItauERPClient
import br.com.zup.pix.client.bcb.BCBDeletePixKeyRequest
import br.com.zup.pix.endpoint.entry.EntryRemoveKey
import br.com.zup.pix.exception.types.InternalException
import br.com.zup.pix.exception.types.NotFoundException
import br.com.zup.pix.exception.types.PermissionDeniedException
import br.com.zup.pix.repository.KeyRepository
import io.micronaut.http.HttpStatus
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Singleton
class RemoveKeyService(
    @Inject private val keyRepository: KeyRepository,
    @Inject private val bcbClient: BCBClient
) {

    private val logger = LoggerFactory.getLogger(RemoveKeyService::class.java)

    @Transactional
    fun removeKey(@Valid entry: EntryRemoveKey) {

        val pixId = UUID.fromString(entry.pixId!!)
        val clientId = UUID.fromString(entry.clientId!!)

        val pixKey =
            keyRepository.findById(pixId)
                .orElseThrow { NotFoundException("The informed pix key was not found.") }

        if (pixKey.bankAccount.owner.clientId != clientId) {
            throw PermissionDeniedException("You don't have permission to remove this pix key.")
        }

        val bcbResponse = bcbClient.deleteKey(BCBDeletePixKeyRequest(pixKey.keyValue), pixKey.keyValue)
        if (bcbResponse.status() != HttpStatus.OK) {
            throw InternalException("We had a problem deleting your key")
        }

        keyRepository.deleteById(pixId).also {
            logger.info("Key successfully deleted")
        }
    }

}