package malbec.core.domain

import javax.validation.constraints.Size

/**
 * Represents a source code repository that contains ansible artifacts
 */
data class Repository(

  /**
   * The record id
   */
  val id: Int,

  /**
   * The id of the parent project
   */
  val projectId: Int,

  /**
   * The id of the parent key
   */
  val keyId: Int?,

  /**
   * The user specified name of the repo
   */
  @get:Size(min = 1, max = 50)
  val name: String,

  /**
   * The url through which the repo will be contacted
   */
  @get:Size(min = 1, max = 255)
  val url: String)