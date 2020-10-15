package br.com.zup.bootcamp.casadocodigo.controller

import br.com.zup.bootcamp.casadocodigo.model.Categoria
import br.com.zup.bootcamp.casadocodigo.model.validator.Unique
import javax.validation.constraints.NotBlank

data class NovaCategoriaRequest(
        @field:NotBlank
        @field:Unique(entityClass = Categoria::class, fieldName = "nome")
        val nome: String
) {

    fun toModel(): Categoria {
        return Categoria(nome = this.nome)
    }

}
