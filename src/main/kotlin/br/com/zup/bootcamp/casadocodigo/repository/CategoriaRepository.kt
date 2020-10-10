package br.com.zup.bootcamp.casadocodigo.repository

import br.com.zup.bootcamp.casadocodigo.model.Categoria
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoriaRepository : CrudRepository<Categoria, Long> {

    fun existsByNome(nome: String): Boolean

}