package malbec.core.data

import malbec.core.biz.ObjectNotFoundException
import malbec.core.domain.Project
import org.funktionale.either.Either
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import java.sql.ResultSet

/**
 * Simple DAO that provides CRUD operations for Project objects
 */
@Component
open class ProjectsDao(val template: NamedParameterJdbcTemplate) {

    /**
     * Finds a single Project by its id
     */
    fun find(id: Int): Either<ObjectNotFoundException, Project> {
        return try {
            val item = template.queryForObject(
              "SELECT id, name, description FROM projects WHERE id = :id",
              mapIntIdParameter(id),
              ::mapProjectRow)
            Either.right(item)
        }
        catch (e: EmptyResultDataAccessException) {
            Either.left(ObjectNotFoundException("No project found by id $id"))
        }
    }   //END find

    /**
     * Returns all known Project instances
     */
    fun selectAll(): List<Project> {
        val sql = "SELECT id, name, description FROM projects"
        return template.query(sql, MapSqlParameterSource(), ::mapProjectRow)
    }   //END selectAll

    /**
     * Inserts a single Project record into the database
     */
    fun insert(item: Project): Project {
        val sql = "INSERT INTO Projects(name, description) VALUES(:name, :description)"

        val id = template.insertAndGenerateIntKey(sql, mapProjectParameters(item))
        return item.copy(id = id)
    }   //END insert

    /**
     * Updates a single record
     */
    fun update(item: Project): Either<ObjectNotFoundException, Project> {
        val sql = "UPDATE projects SET name=:name, description=:description WHERE " +
          "id = :id"
        val modified = template.update(sql, mapProjectParameters(item))

        return when {
            modified > 0 -> Either.right(item)
            else -> Either.left(ObjectNotFoundException("No project found by id ${item.id}"))
        }
    }   //END update

    /**
     * Deletes a single record
     */
    fun delete(id: Int): Either<ObjectNotFoundException, Int> {
        val sql = "DELETE FROM projects WHERE id=:id"
        val modified = template.update(sql, mapIntIdParameter(id))

        return when {
            modified > 0 -> Either.right(id)
            else -> Either.left(ObjectNotFoundException("No project found by id $id"))
        }
    }   //END delete

}   //END ProjectsDao

/**
 * Reads a single row into a Project object
 */
private fun mapProjectRow(rs: ResultSet, rowNum: Int) = Project(
  id = rs.getInt("id"),
  name = rs.getString("name"),
  description = rs.getString("description")
)   //END mapProjectRow

/**
 * Populates a MapSqlParameterSource with named parameters for a Project
 */
private fun mapProjectParameters(item: Project) = MapSqlParameterSource()
  .addValue("id", item.id)
  .addValue("name", item.name)
  .addValue("description", item.description)