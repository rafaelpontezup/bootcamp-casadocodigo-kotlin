package br.com.zup.bootcamp.casadocodigo.controller

import br.com.zup.bootcamp.casadocodigo.model.Autor
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class AutorRequest(
        @NotBlank
        val nome: String,
        @NotBlank
        val email: String,
        @NotBlank
        @Size(max = 400)
        val descricao: String) {

        fun toModel(): Autor {
                return Autor(
                        nome = this.nome,
                        email = this.email,
                        descricao = this.descricao
                )
        }
}