package br.com.zup.pix.endpoint.entry

import br.com.zup.pix.CreatePixKeyRequest
import br.com.zup.pix.model.BankAccount
import br.com.zup.pix.AccountType as ReceiverAccountType
import br.com.zup.pix.KeyType as ReceiverKeyType
import br.com.zup.pix.model.PixKey
import br.com.zup.pix.model.enums.KeyType
import br.com.zup.pix.validator.ValidPixKey
import br.com.zup.pix.validator.ValidUniqueId
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
@ValidPixKey
data class EntryCreateKey(

    @field:ValidUniqueId
    @field:NotNull
    val clientId: String?,
    @field:NotNull
    val type: ReceiverKeyType?,
    @field:NotBlank
    val value: String?,
    @field:NotNull
    val accountType: ReceiverAccountType?
) {

    fun toModel(account: BankAccount): PixKey = PixKey(
        keyType = KeyType.valueOf(type!!.name),
        keyValue = if (type == ReceiverKeyType.RANDOM) {
            UUID.randomUUID().toString()
        } else {
            value!!
        },
        bankAccount = account
    )
}

fun CreatePixKeyRequest.toEntryCreateKey(): EntryCreateKey = EntryCreateKey(
    clientId = clientId,
    type = keyType,
    value = keyValue,
    accountType = accountType
)