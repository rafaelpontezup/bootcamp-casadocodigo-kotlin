package br.com.zup.bootcamp.casadocodigo.controller

import br.com.zup.bootcamp.casadocodigo.repository.CategoriaRepository
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import javax.transaction.Transactional
import javax.validation.Valid

//cdd=3
@RestController
class CategoriaController(private val repository: CategoriaRepository) {

    @Transactional
    @PostMapping("/api/categorias")
    fun novo(@RequestBody @Valid nova: NovaCategoriaRequest, uriBuilder: UriComponentsBuilder): ResponseEntity<Any> {

        /**
         * Esse IF é necessário quando não usamos Bean Validation: @Unique
         */
//        val exists = repository.existsByNome(nova.nome)
//        if (exists) {
//            throw ResponseStatusException(BAD_REQUEST,
//                    "Já existe uma outra categoria com o mesmo nome: ${nova.nome}")
//        }

        val categoria = nova.toModel()
        repository.save(categoria)

        val location: URI = uriBuilder.path("/api/categorias/{id}").buildAndExpand(categoria.id).toUri()
        return ResponseEntity
                .created(location).build<Any>()
    }

}