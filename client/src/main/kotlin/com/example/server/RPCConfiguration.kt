package com.example.server

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "client.rpc")
open class RPCConfiguration {
    lateinit var username: String
    lateinit var password: String
    lateinit var host: String
    var port: Int = 10000
}