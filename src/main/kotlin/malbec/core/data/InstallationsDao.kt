package malbec.core.data

import malbec.core.domain.FileInstallation
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
    fun find(id: Int): FileInstallation =
      template.queryForObject("SELECT id, name, path, version FROM installations WHERE id = :id",
                              mapIntIdParameter(id),
                              ::mapInstallationRow)

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
    fun update(item: FileInstallation) {
        val sql = "UPDATE installations SET name=:name, path=:path, version=:version WHERE " +
          "id = :id"
        template.update(sql, mapInstallationParameters(item))
    }   //END update

    /**
     * Deletes a single record
     */
    fun delete(id: Int) {
        val sql = "DELETE FROM installations WHERE id=:id"
        template.update(sql, mapIntIdParameter(id))
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