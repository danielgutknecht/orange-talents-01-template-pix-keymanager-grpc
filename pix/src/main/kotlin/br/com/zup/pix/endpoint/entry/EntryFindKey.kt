package br.com.zup.pix.endpoint.entry

import br.com.zup.pix.FindPixKeyRequest
import br.com.zup.pix.validator.ValidUniqueId
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class EntryFindByKey(

    @field:NotBlank
    val key: String?
)

fun FindPixKeyRequest.toEntryFindByKey(): EntryFindByKey = EntryFindByKey(
    key = key
)

@Introspected
data class EntryFindByPixId(

    @field:ValidUniqueId
    @field:NotBlank
    val clientId: String?,

    @field:ValidUniqueId
    @field:NotBlank
    val pixId: String?
)

fun FindPixKeyRequest.toEntryFindByPixId(): EntryFindByPixId = EntryFindByPixId(
    pixId = pixId.pixId,
    clientId = pixId.clientId
)