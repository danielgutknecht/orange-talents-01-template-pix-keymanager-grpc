package br.com.zup.pix.client.bcb

import br.com.zup.pix.model.enums.AccountType
import br.com.zup.pix.model.enums.KeyType

enum class BCBKeyType(val modelKeyType: KeyType?) {
    CPF(KeyType.CPF),
    CNPJ(null),
    PHONE(KeyType.TELL_NUMBER),
    EMAIL(KeyType.EMAIL),
    RANDOM(KeyType.RANDOM);

    companion object {
        fun findByKeyType(type: KeyType): BCBKeyType {
            for (value in values()) {
                if (value.modelKeyType != null && value.modelKeyType == type) {
                    return value
                }
            }
            return RANDOM
        }
    }
}

enum class BCPAccountType(val modelAccountType: AccountType) {
    CACC(AccountType.CONTA_CORRENTE),
    SVGS(AccountType.CONTA_POUPANCA);

    companion object {
        fun findByAccountType(type: AccountType): BCPAccountType {
            for (value in values()) {
                if (value.modelAccountType == type) {
                    return value
                }
            }

            return CACC
        }
    }
}

enum class OwnerType {
    NATURAL_PERSON,
    LEGAL_PERSON
}