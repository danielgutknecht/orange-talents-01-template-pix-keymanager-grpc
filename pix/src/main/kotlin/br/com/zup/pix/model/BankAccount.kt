package br.com.zup.pix.model

import br.com.zup.pix.model.enums.AccountType
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Embeddable
class BankAccount(

    @field:NotNull
    @field:Enumerated(EnumType.STRING)
    val type: AccountType,

    @field:NotBlank
    @field:Size(max = 4)
    val agency: String,

    @field:NotBlank
    val number: String,

    @field:NotNull
    @field:Embedded
    val institution: Institution,

    @field:NotNull
    @field:Embedded
    val owner: AccountOwner
)