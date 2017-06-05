package malbec.core.data

import malbec.core.biz.ObjectNotFoundException
import malbec.core.domain.Project
import malbec.core.domain.Repository
import org.funktionale.either.Either
import org.springframework.dao.EmptyResultDataAccessException
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
    fun find(id: Int): Either<ObjectNotFoundException, Repository> {
        return try {
            val item = template.queryForObject(
              "SELECT id, project_id, name, key_id, url FROM repositories WHERE id = :id",
              mapIntIdParameter(id),
              ::mapRepositoryRow)
            Either.right(item)
        }
        catch (e: EmptyResultDataAccessException) {
            Either.left(ObjectNotFoundException("No repository found by id $id"))
        }
    }   //END find

    /**
     * Returns all known Repository instances belonging to the given project
     */
    fun selectAll(project: Project): List<Repository> = selectAll(project.id)

    /**
     * Returns all known Repository instances belonging to the given project id
     */
    fun selectAll(projectId: Int): List<Repository> {
        val sql = "SELECT id, project_id, name, key_id, url FROM repositories WHERE project_id = :pid"
        return template.query(
          sql,
          mapIntIdParameter(projectId, "pid"),
          ::mapRepositoryRow)
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
    fun update(item: Repository): Either<ObjectNotFoundException, Repository> {
        val sql = "UPDATE repositories SET name=:name, url=:url, key_id=:kid WHERE " +
          "id = :id"
        val modified = template.update(sql, mapRepositoryParameters(item))

        return when {
            modified > 0 -> Either.right(item)
            else -> Either.left(ObjectNotFoundException("No repository found by id ${item.id}"))
        }
    }   //END update

    /**
     * Deletes a single record
     */
    fun delete(id: Int): Either<ObjectNotFoundException, Int> {
        val sql = "DELETE FROM repositories WHERE id=:id"
        val modified = template.update(sql, mapIntIdParameter(id))

        return when {
            modified > 0 -> Either.right(id)
            else -> Either.left(ObjectNotFoundException("No repository found by id $id"))
        }
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