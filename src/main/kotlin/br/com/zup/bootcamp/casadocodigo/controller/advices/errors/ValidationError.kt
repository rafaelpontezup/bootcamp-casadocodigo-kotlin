package br.com.zup.bootcamp.casadocodigo.controller.advices.errors

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty


/**
 * Representa um erro de validação de um determinado campo da entidade ou formulario
 */
class ValidationError @JsonCreator constructor(
        @param:JsonProperty("field") val field: String?,
        @param:JsonProperty("message") val message: String?
) {

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (field?.hashCode() ?: 0)
        result = prime * result + (message?.hashCode() ?: 0)
        return result
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj) return true
        if (obj == null) return false
        if (javaClass != obj.javaClass) return false
        val other = obj as ValidationError
        if (field == null) {
            if (other.field != null) return false
        } else if (field != other.field) return false
        if (message == null) {
            if (other.message != null) return false
        } else if (message != other.message) return false
        return true
    }

    override fun toString(): String {
        return "ValidationError [field=$field, message=$message]"
    }
}