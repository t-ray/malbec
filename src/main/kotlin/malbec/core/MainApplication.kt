package malbec.core

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class MainApplication

fun main(args: Array<String>) {
    SpringApplication.run(MainApplication::class.java, *args)
}   //END main