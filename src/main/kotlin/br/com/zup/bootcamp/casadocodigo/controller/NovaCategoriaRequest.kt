package br.com.zup.bootcamp.casadocodigo.controller

import br.com.zup.bootcamp.casadocodigo.model.Categoria
import javax.validation.constraints.NotBlank

data class NovaCategoriaRequest(@field:NotBlank val nome: String) {

    fun toModel(): Categoria {
        return Categoria(nome = this.nome)
    }

}
