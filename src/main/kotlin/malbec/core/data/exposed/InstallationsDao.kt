package malbec.core.data.exposed

import malbec.core.domain.FileInstallation
import org.funktionale.option.Option
import org.funktionale.option.firstOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class InstallationsDao {

    /**
     * fun find(id: Int): Either<ObjectNotFoundException, FileInstallation> {
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
     */
    fun find(id: Int): Option<FileInstallation> {
    //: Either<ObjectNotFoundException, FileInstallation> {

        return transaction {
            Installations.select { Installations.id.eq(id) }
              .map(::mapInstallationRow)
              .firstOption()
        }

//            Installations.select { Installations.id.eq(id) }
//              .map {  }
//              .firstOption()
//              .map { FileInstallation(id) }







    }   //END find

}   //END InstallationRow

class SimpleIntIdTable(name: String = "", columnName: String = "id"): Table(name) {
    val id = integer(columnName).autoIncrement().primaryKey()

    fun findById(rowId: Int) = select { id.eq(rowId) }
}


/**
 * Reads a single row into a FileInstallation object
 */
private fun mapInstallationRow(row: ResultRow) = FileInstallation(
  id = row[Installations.id],
  name = row[Installations.name],
  path = row[Installations.path],
  version = row[Installations.version]
)   //END mapInstallationRow

