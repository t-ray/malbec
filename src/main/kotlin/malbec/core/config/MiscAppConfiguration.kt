package malbec.core.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Defines spring configuration/spring beans that are not otherwise grouped with other
 * logical configuration
 */
@Configuration
open class MiscAppConfiguration {

    /**
     * Enables kotlin support with jackson
     */
    @Bean
    open fun objectMapper() = ObjectMapper().registerKotlinModule()

}   //END MiscAppConfiguration