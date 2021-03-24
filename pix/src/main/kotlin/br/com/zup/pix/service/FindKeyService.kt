package br.com.zup.pix.service

import br.com.zup.pix.client.BCBClient
import br.com.zup.pix.endpoint.entry.EntryFindByKey
import br.com.zup.pix.endpoint.entry.EntryFindByPixId
import br.com.zup.pix.endpoint.entry.EntryListKeys
import br.com.zup.pix.endpoint.reply.PixKeyResponse
import br.com.zup.pix.exception.types.NotFoundException
import br.com.zup.pix.exception.types.PermissionDeniedException
import br.com.zup.pix.model.PixKey
import br.com.zup.pix.repository.KeyRepository
import io.micronaut.http.HttpStatus
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Singleton
class FindKeyService(
    @Inject private val keyRepository: KeyRepository,
    @Inject private val bcbClient: BCBClient
) {

    @Transactional
    fun findByKey(@Valid entry: EntryFindByKey): PixKeyResponse {
        val key: String = entry.key!!

        val optional = keyRepository.findByKeyValue(key)
        if (optional.isEmpty) {
            val bcbResponse = bcbClient.findKey(key)
            if (bcbResponse.status != HttpStatus.OK) {
                return bcbResponse.body()!!.toPixKey()
            }

            throw NotFoundException("Key not found.")
        }

        return PixKeyResponse.of(optional.get())
    }

    @Transactional
    fun findByPixId(entryFindRequest: EntryFindByPixId): PixKeyResponse {
        val key = keyRepository.findById(UUID.fromString(entryFindRequest.pixId))
            .orElseThrow { throw NotFoundException("Key not found.") }

        if (key.bankAccount.owner.clientId.toString() != entryFindRequest.clientId)
            throw PermissionDeniedException("You do not have permission to interact with this key.")

        return PixKeyResponse.of(key)
    }

    fun listAllByClient(entry: EntryListKeys): List<PixKey> =
        keyRepository.findAllByBankAccountOwnerClientId(UUID.fromString(entry.clientId!!))

}