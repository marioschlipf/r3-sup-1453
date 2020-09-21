package com.example.server

import net.corda.client.rpc.CordaRPCClient
import net.corda.client.rpc.CordaRPCConnection
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.utilities.NetworkHostAndPort
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
open class RPCConnection(val configuration: RPCConfiguration) {

    lateinit var rpcConnection: CordaRPCConnection
        private set

    lateinit var proxy: CordaRPCOps
        private set

    @PostConstruct
    fun connect() {
        val rpcAddress = NetworkHostAndPort(configuration.host, configuration.port)
        val rpcClient = CordaRPCClient(rpcAddress)
        val rpcConnection = rpcClient.start(configuration.username, configuration.password)
        proxy = rpcConnection.proxy
    }

    @PreDestroy
    fun close() {
        rpcConnection.notifyServerAndClose()
    }


}