package br.com.zup.pix.model

import javax.persistence.Column
import javax.persistence.Embeddable
import javax.validation.constraints.NotNull

@Embeddable
class Institution(

    @field:NotNull
    @Column(name = "institution_name")
    val name: String,

    @field:NotNull
    @Column(name = "institution_ispb")
    val ispb: String
)