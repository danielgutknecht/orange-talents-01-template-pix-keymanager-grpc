package br.com.zup.pix.endpoint.entry

import br.com.zup.pix.ListAllPixKeysRequest
import br.com.zup.pix.validator.ValidUniqueId
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class EntryListKeys(
    @field:NotBlank
    @field:ValidUniqueId
    val clientId: String?
)

fun ListAllPixKeysRequest.toEntryListKeys(): EntryListKeys = EntryListKeys(clientId)