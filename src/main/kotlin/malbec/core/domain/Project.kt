package malbec.core.domain

import javax.validation.constraints.Size

/**
 * Highest level unit that organizes keys, tasks, and repositories together
 */
data class Project(

  /**
   * The record id
   */
  val id: Int,

  /**
   * The name of the project
   */
  @get:Size(min = 1, max = 50)
  val name: String,

  /**
   * The user defined project description
   */
  @get:Size(min = 0, max = 4096)
  val description: String

)   //END Project