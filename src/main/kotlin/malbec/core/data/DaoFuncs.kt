package malbec.core.data

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.jdbc.support.GeneratedKeyHolder

/**
 * Function that will map values from the given object into named parameters
 */
typealias ParameterMapper<T> = (T) -> SqlParameterSource

/**
 * Inserts a new row and returns the generated key
 */
fun NamedParameterJdbcTemplate.insertAndGenerateKey(sql: String, params: SqlParameterSource): Number {

    val keyHolder = GeneratedKeyHolder()
    this.update(sql, params, keyHolder)

    return keyHolder.key
}   //END insertAndGenerateKey

/**
 * Inserts a new row and returns the generated key as an Int
 */
fun NamedParameterJdbcTemplate.insertAndGenerateIntKey(sql: String, params: SqlParameterSource): Int =
    this.insertAndGenerateKey(sql, params).toInt()