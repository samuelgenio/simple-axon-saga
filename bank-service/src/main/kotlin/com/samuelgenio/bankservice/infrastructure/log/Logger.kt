package com.samuelgenio.bankservice.infrastructure.log

import com.samuelgenio.commonservice.infrastructure.log.dto.LogDTO
import com.samuelgenio.commonservice.infrastructure.extensions.toJson
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component
class Logger @Autowired constructor(){

    companion object {
        const val START_SAGA = "Start Start Event %s for id :{%s}"
        const val SAGA_EVENT = "Do Saga Event %s for for id :{%s}"
    }

    init {
        println("Samuel")
    }

    fun info(message: String, payload: Any? = null) {
        val logger = KotlinLogging.logger {}
        logger.info(LogDTO(message, payload).toJson())
    }

    fun error(t:Throwable?, message: () -> Any?) {
        val logger = KotlinLogging.logger {}
        logger.error(t, message);
    }

}