package malbec.core.config

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.io.File
import javax.annotation.PostConstruct

/**
 * Defines the configuration that is stored outside of the application/archive.
 */
@Configuration
open class ExternalizedAppConfig (
  @Value("\${working_dir}") val workingDir: String) {

    private val logger = LoggerFactory.getLogger(ExternalizedAppConfig::class.java)

    @PostConstruct
    fun init() {
        validateDirectory(workingDir)
    }   //END init

    fun validateDirectory(dir: String) {

        val d = File(dir)

        when {
            d.exists() && d.isDirectory -> logger.info("Directory $dir exists. No action necessary.")
            d.exists() && !d.isDirectory -> {
                val message = "File exists with the configured working directory. Cannot continue."
                logger.error(message)
                throw IllegalStateException(message)
            }
            !d.exists() -> {
                logger.info("Directory $dir does not exist. Creating.")
                d.mkdir()
            }
        }

        logger.info("Directory $dir exists: ${d.exists()}")
    }   //END validateDirectory

}   //END ExternalizedAppConfig