package com.samuelgenio.astrea.infrastructure.log

import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class Logger {

    companion object {
        const val START_SAGA = "Start Start Event %s for id :{%s}"
        const val SAGA_EVENT = "Do Saga Event %s for for id :{%s}"
    }

    init {
        println("Samuel")
    }

    private val logger = KotlinLogging.logger {}

    fun info(message: String, payload: Any? = null) {
        println(message)
        //logger.info(LogDTO(message, payload).toJson())
    }

    fun error(t:Throwable?, message: () -> Any?) {
        val logger = KotlinLogging.logger {}
        logger.error(t, message);
    }

}