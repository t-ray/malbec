package malbec.core.domain

import java.io.File
import javax.validation.constraints.Size

/**
 * Represents the location of an ansible installation
 * somewhere on the system
 */
sealed class AnsibleInstallation(val command: String) {

    /**
     * The actual file on the file system that represents the ansible playbook command
     */
    val executable = File(command)

}   //END AnsibleInstallation

/**
 * Represents an ansible instance at a specific location
 */
data class FileInstallation(
  /**
   * Record id
   */
  val id: Int,

  /**
   * How the installation is referenced in the user interface
   */
  @get:Size(min = 1, max = 50)
  val name: String = "",

  /**
   * The version of the installation; just used for informational purposes. Optional.
   */
  @get:Size(min = 0, max = 255)
  val version: String = "",

  /**
   * The fully path to the ansible playbook command on the host. Required.
   */
  @get:Size(min = 1, max = 50)
  val path: String = "") : AnsibleInstallation(path)

/**
 * Installation that assumes the ansible-playbook command is already present on the path
 */
object DefaultInstallation : AnsibleInstallation("ansible-playbook")