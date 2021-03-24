package br.com.zup.pix.repository

import br.com.zup.pix.model.PixKey
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface KeyRepository : CrudRepository<PixKey, UUID> {

    fun findByKeyValue(keyValue: String): Optional<PixKey>

    fun existsByKeyValue(keyValue: String): Boolean

    fun findAllByBankAccountOwnerClientId(clientId: UUID): List<PixKey>

}