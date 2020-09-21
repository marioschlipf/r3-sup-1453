package com.example.server

import net.corda.core.contracts.ContractState
import net.corda.core.contracts.StateAndRef
import net.corda.core.messaging.vaultQueryBy
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/support/")
class MainController(rpc: RPCConnection) {

    private val myLegalName = rpc.proxy.nodeInfo().legalIdentities.first().name
    private val proxy = rpc.proxy

    // Quick check to see if the server is up
    @GetMapping(value = ["me"], produces = [APPLICATION_JSON_VALUE])
    fun me() = mapOf("me" to myLegalName)

    // This WORKS!
    @GetMapping(value = ["states-working"], produces = [APPLICATION_JSON_VALUE])
    fun statesBroken(): ResponseEntity<List<StateAndRef<ContractState>>> {
        return ResponseEntity.ok(proxy.vaultQuery(ContractState::class.java).states)
    }

    // This DOES NOT WORK!!!
    @GetMapping(value = ["states-broken"], produces = [APPLICATION_JSON_VALUE])
    fun statesWorking(): ResponseEntity<List<StateAndRef<ContractState>>> {
        return ResponseEntity.ok(proxy.vaultQueryBy<ContractState>().states)
    }
}
