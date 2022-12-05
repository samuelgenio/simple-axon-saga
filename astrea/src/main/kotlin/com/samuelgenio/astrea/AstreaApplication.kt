package com.samuelgenio.astrea

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AstreaApplication

fun main(args: Array<String>) {
	runApplication<AstreaApplication>(*args)
}
