package br.com.zup.bootcamp.casadocodigo.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Autor(
        @Id
        @GeneratedValue
        var id: Long? = null,
        @Column(nullable = false)
        var nome: String,
        @Column(nullable = false)
        var email: String,
        @Column(length = 400, nullable = false)
        var descricao: String,
        @Column(nullable = false)
        var criadoEm: LocalDateTime = LocalDateTime.now()
) {


}