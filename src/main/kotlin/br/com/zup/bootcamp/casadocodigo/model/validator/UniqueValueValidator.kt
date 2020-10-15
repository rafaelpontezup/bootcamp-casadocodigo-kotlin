package br.com.zup.bootcamp.casadocodigo.model.validator

import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.EntityManager
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import kotlin.reflect.KClass

/**
 * Apesar de elegante, esse tipo de solução não evita registros duplicados. É praticamente
 * impossível implementar essa verificação de unicidade no lado Java (aplicação) devido a
 * problemas e concorrência (ex.: Phantom Read Anomaly).
 *
 * Sem dúvida, a solução mais segura é adicionar Unique Constraint na coluna/tabela e tratar o erro onde necessário;
 *
 * Possiveis soluções:
 *  - Unique Constraint + SQL-ON-CONFLICT
 *  - ADVISORY LOCKS (PostgreSQL);
 *  - Usar Serializable Isolation (depende do RDBMS, tipo de index etc)
 *  - Lockar a tabela inteira: LOCK TABLE <tabela>;
 *
 * Links:
 *  - https://stackoverflow.com/questions/3495368/unique-constraint-with-jpa-and-bean-validation
 *  - https://codingexplained.com/coding/java/hibernate/unique-field-validation-using-hibernate-spring
 *  - https://www.postgresql.org/docs/current/sql-insert.html#SQL-ON-CONFLICT
 *  - https://vladmihalcea.com/postgresql-triggers-isolation-levels/
 *  - https://www.postgresql.org/docs/13/explicit-locking.html#ADVISORY-LOCKS
 *  - https://stackoverflow.com/questions/12837708/predicate-locking-in-postgresql-9-2-1-with-serializable-isolation
 *  - https://www.enterprisedb.com/blog/serializable-postgresql-11-and-beyond
 *  - https://stackoverflow.com/questions/50745282/postgress-serializable-transaction-blocks-concurrent-write
 */
class UniqueValueValidator : ConstraintValidator<Unique, Any> {

    @Autowired
    lateinit var manager: EntityManager;

    private var fieldName: String? = null
    private var entityClass: KClass<*>? = null

    override fun initialize(constraintAnnotation: Unique?) {
        this.entityClass = constraintAnnotation?.entityClass
        this.fieldName = constraintAnnotation?.fieldName
    }

    override fun isValid(value: Any?, context: ConstraintValidatorContext?): Boolean {

        if (value == null) {
            return true
        }

        val jpql = "SELECT e from ${entityClass?.simpleName} e where e.$fieldName = :value"
        val entities = manager
                    .createQuery(jpql, entityClass?.java)
                    .setParameter("value", value)
                    .resultList

        return entities.isEmpty();
    }

}