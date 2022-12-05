package com.samuelgenio.userservice.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class XStream {

    @Bean
    fun xStream(): com.thoughtworks.xstream.XStream {
        return com.thoughtworks.xstream.XStream().apply {
            this.allowTypesByWildcard(listOf("com.samuelgenio.**").toTypedArray())
        }
    }

}