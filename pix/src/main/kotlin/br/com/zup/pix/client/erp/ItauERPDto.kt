package br.com.zup.pix.client.erp

import br.com.zup.pix.model.AccountOwner
import br.com.zup.pix.model.BankAccount
import br.com.zup.pix.model.Institution
import br.com.zup.pix.model.enums.AccountType
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class ERPAccountResponse(

    @JsonProperty("tipo")
    @NotBlank
    val type: String,

    @JsonProperty("instituicao")
    @NotNull
    val institution: ERPInstitutionResponse,

    @JsonProperty("agencia")
    @NotBlank
    @Size(max = 4)
    val agency: String,

    @JsonProperty("numero")
    @NotBlank
    val number: String,

    @JsonProperty("titular")
    @NotNull
    val owner: ERPOwnerResponse
) {

    fun toModel(): BankAccount = BankAccount(
        type = AccountType.valueOf(type),
        agency = agency,
        number = number,
        owner = owner.toModel(),
        institution = Institution(
            name = institution.name,
            ispb = institution.ispb
        )
    )

}

data class ERPInstitutionResponse(

    @JsonProperty("nome")
    @NotBlank
    val name: String,

    @JsonProperty("ispb")
    @NotBlank
    val ispb: String
)

data class ERPOwnerResponse(

    @JsonProperty("id")
    @NotNull
    val id: UUID,

    @JsonProperty("nome")
    @NotBlank
    val name: String,

    @JsonProperty("cpf")
    @NotBlank
    val cpf: String
) {
    fun toModel(): AccountOwner = AccountOwner(
        clientId = id,
        name = name,
        cpf = cpf
    )
}