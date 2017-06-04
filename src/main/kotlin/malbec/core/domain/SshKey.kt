package malbec.core.domain

/**
 * Defines a single key that is used for two purposes:
 *  a) fetch contents of remote repo
 *  b) execute ansible playbooks
 *
 * An ssh key belongs to a project and must have a name.
 */
data class SshKey(

  /**
   * The record id
   */
  val id: Int,

  /**
   * The id of the parent project
   */
  val projectId: Int,

  /**
   * The name of the key
   */
  val name: String,

  /**
   * The username/identity of the key. Optional.
   */
  val username: String?,

  /**
   * The passphrase used to generate the key. Optional.
   */
  val passphrase: String?,

  /**
   * The public key.
   */
  val privateKey: String?,

  /**
   * The private key.
   */
  val publicKey: String?)