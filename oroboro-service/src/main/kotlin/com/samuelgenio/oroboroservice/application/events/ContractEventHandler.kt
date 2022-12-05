package com.samuelgenio.oroboroservice.application.events

import com.samuelgenio.commonservice.events.ContractCreatedEvent
import com.samuelgenio.oroboroservice.domain.Contract
import com.samuelgenio.oroboroservice.infrastructure.repositories.ContractRepository
import org.axonframework.eventhandling.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ContractEventHandler @Autowired constructor(
        val contractRepository: ContractRepository) {

    @EventHandler
    fun on(contractCreatedEvent: ContractCreatedEvent) {
        Contract(
            contractId = contractCreatedEvent.contractId,
            pendinContractId = contractCreatedEvent.pendingContractId,
            status = contractCreatedEvent.status
        ).let(contractRepository::save)
    }
}