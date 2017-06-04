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
     * Finds a single Repository by its id
     */
    fun find(id: Int): Repository =
      template.queryForObject("SELECT id, project_id, name, key_id, url FROM repositories WHERE id = :id",
                              mapIntIdParameter(id),
                              ::mapRepositoryRow)

    /**
     * Returns all known FileInstallation instances
     */
    fun selectAll(): List<Repository> {
        val sql = "SELECT id, project_id, name, key_id, url FROM repositories"
        return template.query(sql, MapSqlParameterSource(), ::mapRepositoryRow)
    }   //END selectAll

    /**
     * Inserts a single Repository record into the database
     */
    fun insert(item: Repository): Repository {
        val sql = "INSERT INTO repositories(project_id, name, key_id, url) VALUES(:pid, :name, :kid, :url)"

        val id = template.insertAndGenerateIntKey(sql, mapRepositoryParameters(item))
        return item.copy(id = id)
    }   //END insert

    /**
     * Updates a single record
     */
    fun update(item: Repository) {
        val sql = "UPDATE repositories SET name=:name, url=:url, key_id=:kid WHERE " +
          "id = :id"
        template.update(sql, mapRepositoryParameters(item))
    }   //END update

    /**
     * Deletes a single record
     */
    fun delete(id: Int) {
        val sql = "DELETE FROM repositories WHERE id=:id"
        template.update(sql, mapIntIdParameter(id))
    }   //END delete

}   //END RepositoriesDao

private fun mapRepositoryRow(rs: ResultSet, rowNum: Int) = Repository(
  id = rs.getInt("id"),
  projectId = rs.getInt("project_id"),
  name = rs.getString("name"),
  keyId = rs.getInt("key_id"),
  url = rs.getString("url")
)   //END mapRepositoryRow

private fun mapRepositoryParameters(item: Repository): SqlParameterSource =
  MapSqlParameterSource()
    .addValue("id", item.id)
    .addValue("pid", item.projectId)
    .addValue("name", item.name)
    .addValue("kid", item.keyId)
    .addValue("url", item.url)