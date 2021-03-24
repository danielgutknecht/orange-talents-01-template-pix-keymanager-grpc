package br.com.zup.pix.model

import java.util.*
import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Embeddable
class AccountOwner(

    @field:NotNull
    val clientId: UUID,

    @field:NotBlank
    val name: String,

    @field:NotBlank
    val cpf: String
)