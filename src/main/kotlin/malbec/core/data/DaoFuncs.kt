package malbec.core.data

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.jdbc.support.GeneratedKeyHolder

val DEFAULT_ID_COLUMN_NAME = "id"

/**
 * Function that will map values from the given object into named parameters
 */
typealias ParameterMapper<T> = (T) -> SqlParameterSource

typealias IntIdProvider<T> = (T) -> Int

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

/**
 * Produces a MapSqlParameterSource with a single argument - the provided row id
 */
fun mapIntIdParameter(id: Int, columnName: String = DEFAULT_ID_COLUMN_NAME): SqlParameterSource = MapSqlParameterSource()
  .addValue(columnName, id)