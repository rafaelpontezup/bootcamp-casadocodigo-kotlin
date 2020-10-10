package br.com.zup.bootcamp.casadocodigo.repository

import br.com.zup.bootcamp.casadocodigo.model.Autor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AutorRepository : CrudRepository<Autor, Long> {

    fun existsByEmail(email: String): Boolean

}