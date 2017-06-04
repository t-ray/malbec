package malbec.core.data

import malbec.core.biz.ObjectNotFoundException
import malbec.core.domain.FileInstallation
import org.funktionale.either.Either
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import java.sql.ResultSet

/**
 * Simple DAO that provides CRUD operations for FileInstallation objects
 */
@Component
open class InstallationsDao(val template: NamedParameterJdbcTemplate) {

    /**
     * Finds a single FileInstallation by its id
     */
    fun find(id: Int): Either<ObjectNotFoundException, FileInstallation> {
        return try {
            val installation = template.queryForObject(
              "SELECT id, name, path, version FROM installations WHERE id = :id",
              mapIntIdParameter(id),
              ::mapInstallationRow)
            Either.right(installation)
        }
        catch (e: EmptyResultDataAccessException) {
            Either.left(ObjectNotFoundException("No installation found by id $id"))
        }
    }   //END find

    /**
     * Returns all known FileInstallation instances
     */
    fun selectAll(): List<FileInstallation> {
        val sql = "SELECT id, name, path, version FROM installations"
        return template.query(sql, MapSqlParameterSource(), ::mapInstallationRow)
    }   //END selectAll

    /**
     * Inserts a single FileInstallation record into the database
     */
    fun insert(item: FileInstallation): FileInstallation {
        val sql = "INSERT INTO installations(name, path, version) VALUES(:name, :path, :version)"

        val id = template.insertAndGenerateIntKey(sql, mapInstallationParameters(item))
        return item.copy(id = id)
    }   //END insert

    /**
     * Updates a single record
     */
    fun update(item: FileInstallation): Either<ObjectNotFoundException, FileInstallation> {
        val sql = "UPDATE installations SET name=:name, path=:path, version=:version WHERE " +
          "id = :id"
        val modified = template.update(sql, mapInstallationParameters(item))

        return when {
            modified > 0 -> Either.right(item)
            else -> Either.left(ObjectNotFoundException("No installation found by id ${item.id}"))
        }
    }   //END update

    /**
     * Deletes a single record
     */
    fun delete(id: Int): Either<ObjectNotFoundException, Int> {
        val sql = "DELETE FROM installations WHERE id=:id"
        val modified = template.update(sql, mapIntIdParameter(id))

        return when {
            modified > 0 -> Either.right(id)
            else -> Either.left(ObjectNotFoundException("No installation found by id $id"))
        }
    }   //END delete

}   //END InstallationsDao

/**
 * Reads a single row into a FileInstallation object
 */
private fun mapInstallationRow(rs: ResultSet, rowNum: Int) = FileInstallation(
  id = rs.getInt("id"),
  name = rs.getString("name"),
  path = rs.getString("path"),
  version = rs.getString("version")
)   //END mapInstallationRow

/**
 * Populates a MapSqlParameterSource with named parameters for a File Installation
 */
private fun mapInstallationParameters(item: FileInstallation) = MapSqlParameterSource()
  .addValue("id", item.id)
  .addValue("name", item.name)
  .addValue("path", item.path)
  .addValue("version", item.version)