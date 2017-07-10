package malbec.core.data.exposed

import org.jetbrains.exposed.sql.Table

object Installations : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", length = 50)
    val path = varchar("path", length = 255)
    val version = varchar("version", length = 50).nullable()
}

object Projects : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", length = 50)
    val description = varchar("description", length = 4096)
}

object Keystore : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val projectId = (integer("project_id") references Projects.id)
    val name = varchar("name", length = 50)
    val username = varchar("username", length = 50).nullable()
    val passphrase = varchar("passphrase", length = 50).nullable()
    val privateKey = text("private_key").nullable()
    val publicKey = text("public_key").nullable()
}

object Repositories : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val projectId = (integer("project_id") references Projects.id)
    val name = varchar("name", length = 50)
    val keyId = (integer("keyId") references Keystore.id).nullable()
    val url = varchar("url", length = 255)
}