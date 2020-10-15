package br.com.zup.bootcamp.casadocodigo.controller

import br.com.zup.bootcamp.casadocodigo.model.Autor
import br.com.zup.bootcamp.casadocodigo.repository.AutorRepository
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

//cdd=4
@RestController
class AutorController(
        private val repository: AutorRepository
) {

    @Transactional
    @PostMapping("/api/autores")
    fun novo(@RequestBody @Valid novo: NovoAutorRequest, uriBuilder: UriComponentsBuilder): ResponseEntity<Any> {

//        val exists = repository.existsByEmail(novo.email);
//        if (exists) {
//            throw ResponseStatusException(BAD_REQUEST,
//                    "Já existe um(a) outro(a) autor(a) com o mesmo email: ${novo.email}")
//        }

        val autor: Autor = novo.toModel()
        repository.save(autor)

        val location: URI = uriBuilder.path("/api/autores/{id}").buildAndExpand(autor.id).toUri()
        return ResponseEntity
                .created(location).build<Any>()
    }

}