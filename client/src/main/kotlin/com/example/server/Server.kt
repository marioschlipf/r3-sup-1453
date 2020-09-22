package com.example.server

import net.corda.client.jackson.JacksonSupport
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

@SpringBootApplication(
    exclude = [ArtemisAutoConfiguration::class]
)
open class Server {
    @Bean
    open fun mappingJackson2HttpMessageConverter(@Autowired rpcConnection: RPCConnection) =
        MappingJackson2HttpMessageConverter().apply {
            objectMapper = JacksonSupport.createDefaultMapper(rpcConnection.proxy)
        }
}

fun main(args: Array<String>) {
    SpringApplication(Server::class.java).run(*args)
}
