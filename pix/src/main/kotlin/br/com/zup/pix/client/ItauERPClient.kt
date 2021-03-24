package br.com.zup.pix.client

import br.com.zup.pix.client.erp.ERPAccountResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client

@Client("http://localhost:9091")
interface ItauERPClient {

    @Get("/api/v1/clientes/{clientId}/contas")
    fun getAccount(@PathVariable clientId: String, @QueryValue("tipo") type: String): HttpResponse<ERPAccountResponse>

}