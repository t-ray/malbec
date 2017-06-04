package malbec.core.data

import malbec.core.biz.ObjectNotFoundException
import malbec.core.domain.Project
import malbec.core.domain.SshKey
import org.funktionale.either.Either
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import java.sql.ResultSet

/**
 * Dao that performs CRUB operations for keys
 */
@Component
open class KeystoreDao(val template: NamedParameterJdbcTemplate) {

    /**
     * Finds a key by its id
     */
    fun find(id: Int): Either<ObjectNotFoundException, SshKey> {
        return try {
            val item = template.queryForObject(
              "SELECT id, project_id, name, username, passphrase, public_key, private_key" +
                " FROM keystore WHERE id = :id",
              mapIntIdParameter(id),
              ::mapKeyRow)
            Either.right(item)
        }
        catch (e: EmptyResultDataAccessException) {
            Either.left(ObjectNotFoundException("No keys found by id $id"))
        }
    }   //END find

    /**
     * Returns all ssh keys that belong to the given project
     */
    fun selectAll(project: Project) = selectAll(project.id)

    /**
     * Returns all ssh keys that belong to the given project id
     */
    fun selectAll(projectId: Int): List<SshKey> {
        return template.query(
            "SELECT id, project_id, name, username, passphrase, public_key, private_key FROM keystore WHERE project_id = :pid",
            mapIntIdParameter(projectId, "pid"),
            ::mapKeyRow)
    }   //END selectAll

    /**
     * Inserts a single ssh key
     */
    fun insert(item: SshKey): SshKey {
        val sql = "INSERT INTO keystore(project_id, name, username, passphrase, public_key, private_key) VALUES(" +
          ":pid, :name, :username, :passphrase, :pubkey, :privkey)"
        val id = template.insertAndGenerateIntKey(sql, mapKeyParameters(item))
        return item.copy(id = id)
    }   //END insert

    /**
     * Updates a single ssh key
     */
    fun update(item: SshKey): Either<ObjectNotFoundException, SshKey> {
        val sql = "UPDATE keystore SET name=:name, username=:username, passphrase=:passphrase, " +
          "public_key=:pubkey, private_key=:privkey WHERE id = :id"
        val modified = template.update(sql, mapKeyParameters(item))

        return when {
            modified > 0 -> Either.right(item)
            else -> Either.left(ObjectNotFoundException("No key found by id ${item.id}"))
        }
    }   //END update

    /**
     * Deletes a single ssh key
     */
    fun delete(id: Int): Either<ObjectNotFoundException, Int> {
        val sql = "DELETE FROM keystore WHERE id = :id"
        val modified = template.update(sql, mapIntIdParameter(id))

        return when {
            modified > 0 -> Either.right(id)
            else -> Either.left(ObjectNotFoundException("No keys found by id $id"))
        }
    }   //END delete

}   //END KeystoreDay

private fun mapKeyRow(rs: ResultSet, rowNum: Int) = SshKey(
  id = rs.getInt("id"),
  projectId = rs.getInt("project_id"),
  name = rs.getString("name"),
  username = rs.getString("username"),
  passphrase = rs.getString("passphrase"),
  publicKey = rs.getString("public_key"),
  privateKey = rs.getString("private_key")
)   //END mapKeyRow

private fun mapKeyParameters(item: SshKey) = MapSqlParameterSource()
  .addValue("id", item.id)
  .addValue("pid", item.projectId)
  .addValue("name", item.name)
  .addValue("username", item.username)
  .addValue("passphrase", item.passphrase)
  .addValue("pubkey", item.publicKey)
  .addValue("privkey", item.privateKey)
