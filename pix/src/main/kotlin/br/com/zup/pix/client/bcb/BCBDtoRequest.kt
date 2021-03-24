package br.com.zup.pix.client.bcb

import br.com.zup.pix.model.AccountOwner
import br.com.zup.pix.model.BankAccount
import br.com.zup.pix.model.PixKey

private const val itauISPB: String = "60701190"

data class BCBCreatePixKeyRequest(
    val keyType: BCBKeyType,
    val key: String,
    val bankAccount: BCBBankAccountRequest,
    val owner: BCBOwnerRequest
) {
    companion object {
        fun byPixKey(pixKey: PixKey): BCBCreatePixKeyRequest =
            BCBCreatePixKeyRequest(
                keyType = BCBKeyType.findByKeyType(pixKey.keyType),
                key = pixKey.keyValue,
                bankAccount = BCBBankAccountRequest.byBankAccount(pixKey.bankAccount),
                owner = BCBOwnerRequest.byOwner(pixKey.bankAccount.owner)
            )
    }
}

data class BCBBankAccountRequest(
    val participant: String,
    val branch: String,
    val accountNumber: String,
    val accountType: BCPAccountType
) {
    companion object {
        fun byBankAccount(account: BankAccount): BCBBankAccountRequest =
            BCBBankAccountRequest(
                participant = itauISPB,
                branch = account.agency,
                accountNumber = account.number,
                accountType = BCPAccountType.findByAccountType(account.type)
            )
    }
}

data class BCBOwnerRequest(
    val type: OwnerType = OwnerType.NATURAL_PERSON,
    val name: String,
    val taxIdNumber: String
) {
    companion object {
        fun byOwner(owner: AccountOwner): BCBOwnerRequest =
            BCBOwnerRequest(
                name = owner.name,
                taxIdNumber = owner.cpf
            )
    }
}

data class BCBDeletePixKeyRequest(
    val key: String,
    val participant: String = itauISPB
)

