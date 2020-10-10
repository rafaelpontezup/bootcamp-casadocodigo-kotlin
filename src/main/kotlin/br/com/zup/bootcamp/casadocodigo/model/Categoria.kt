package br.com.zup.bootcamp.casadocodigo.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Categoria(
        @Id
        @GeneratedValue
        val id: Long? = null,
        @Column(nullable = false, unique = true)
        val nome: String
) {
}