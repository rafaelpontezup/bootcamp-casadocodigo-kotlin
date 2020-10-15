package br.com.zup.bootcamp.casadocodigo.controller

import br.com.zup.bootcamp.casadocodigo.model.Autor
import br.com.zup.bootcamp.casadocodigo.model.validator.Unique
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class NovoAutorRequest(
        @field:NotBlank
        val nome: String,
        @field:NotBlank
        @field:Email
        @field:Unique(
                entityClass = Autor::class,
                fieldName = "email",
                message = "{zup.unique.value.violation.autor}" // tip: interpolação não funcionou no Kotlin
        )
        val email: String,
        @field:NotBlank
        @field:Size(max = 400)
        val descricao: String) {

        fun toModel(): Autor {
                return Autor(
                        nome = this.nome,
                        email = this.email,
                        descricao = this.descricao
                )
        }
}