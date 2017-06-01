package malbec.core.data

import malbec.core.domain.Repository
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
open class RepositoriesDao(val template: NamedParameterJdbcTemplate) {

    /**
     * Returns all known FileInstallation instances
     */
    fun selectAll(): List<Repository> {
        val sql = "SELECT id, name, url FROM repositories"
        return template.query(sql, MapSqlParameterSource(), ::mapRepositoryRow)
    }   //END selectAll

    /**
     * Inserts a single Repository record into the database
     */
    fun insert(item: Repository): Repository {
        val sql = "INSERT INTO repositories(name, url) VALUES(:name, :url)"

        val id = template.insertAndGenerateIntKey(sql, mapRepositoryParameters(item))
        return item.copy(id = id)
    }   //END insert

    /**
     * Updates a single record
     */
    fun update(item: Repository) {
        val sql = "UPDATE repositories SET name=:name, url=:url WHERE " +
          "id = :id"
        template.update(sql, mapRepositoryParameters(item))
    }   //END update

    /**
     * Deletes a single record
     */
    fun delete(item: Repository) {
        val sql = "DELETE FROM repositories WHERE id=:id"
        template.update(sql, mapRepositoryParameters(item))
    }   //END delete

}   //END RepositoriesDao

private fun mapRepositoryRow(rs: ResultSet, rowNum: Int) = Repository(
  id = rs.getInt("id"),
  name = rs.getString("name"),
  url = rs.getString("url")
)   //END mapRepositoryRow

private fun mapRepositoryParameters(item: Repository): SqlParameterSource =
  MapSqlParameterSource()
    .addValue("id", item.id)
    .addValue("name", item.name)
    .addValue("url", item.url)