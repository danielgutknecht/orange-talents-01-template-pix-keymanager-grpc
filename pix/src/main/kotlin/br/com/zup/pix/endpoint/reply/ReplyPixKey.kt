package br.com.zup.pix.endpoint.reply

import br.com.zup.pix.FindPixKeyReply
import br.com.zup.pix.model.AccountOwner
import br.com.zup.pix.model.BankAccount
import br.com.zup.pix.model.PixKey
import br.com.zup.pix.model.enums.AccountType
import br.com.zup.pix.model.enums.KeyType
import com.google.protobuf.Timestamp
import java.time.LocalDateTime
import java.time.ZoneId

data class PixKeyResponse(
    val keyType: KeyType,
    val keyValue: String,
    val bankAccount: BankAccountResponse,
    val id: String?,
    val createdAt: LocalDateTime
) {
    companion object {
        fun of(pixKey: PixKey): PixKeyResponse = PixKeyResponse(
            keyType = pixKey.keyType,
            keyValue = pixKey.keyValue,
            bankAccount = BankAccountResponse.of(pixKey.bankAccount),
            id = pixKey.id.toString(),
            createdAt = pixKey.createdAt
        )
    }
}

data class BankAccountResponse(
    val type: AccountType,
    val agency: String,
    val number: String,
    val owner: AccountOwnerResponse,
    val institution: InstitutionResponse
) {
    companion object {
        fun of(account: BankAccount): BankAccountResponse = BankAccountResponse(
            type = account.type,
            agency = account.agency,
            number = account.number,
            owner = AccountOwnerResponse.of(account.owner),
            institution = InstitutionResponse(
                name = account.institution.name,
                ispb = account.institution.ispb
            )
        )
    }
}

data class InstitutionResponse(
    val name: String,
    val ispb: String
)

data class AccountOwnerResponse(
    val clientId: String? = null,
    val name: String,
    val cpf: String
) {
    companion object {
        fun of(owner: AccountOwner): AccountOwnerResponse = AccountOwnerResponse(
            clientId = owner.clientId.toString(),
            name = owner.name,
            cpf = owner.cpf
        )
    }
}

fun PixKeyResponse.toFindPixKeyReply(): FindPixKeyReply {
    val owner = bankAccount.owner
    return FindPixKeyReply.newBuilder()
        .setClientId(owner.clientId ?: "")
        .setPixId(id ?: "")
        .setKey(
            FindPixKeyReply.PixKey.newBuilder()
                .setKeyType(br.com.zup.pix.KeyType.valueOf(keyType.name))
                .setKeyValue(keyValue)
                .setCreatedAt(createdAt.let {
                    val createdAt = it.atZone(ZoneId.of("UTC")).toInstant()
                    Timestamp.newBuilder()
                        .setSeconds(createdAt.epochSecond)
                        .setNanos(createdAt.nano)
                }).setAccount(
                    FindPixKeyReply.PixKey.BankAccount.newBuilder()
                        .setAccountType(br.com.zup.pix.AccountType.valueOf(bankAccount.type.name))
                        .setInstitution(bankAccount.institution.name)
                        .setAgency(bankAccount.agency)
                        .setNumber(bankAccount.number)
                        .setOwner(
                            FindPixKeyReply.PixKey.BankAccount.AccountOwner.newBuilder()
                                .setName(owner.name)
                                .setCpf(owner.cpf)
                                .build()
                        ).build()
                ).build()
        ).build()
}