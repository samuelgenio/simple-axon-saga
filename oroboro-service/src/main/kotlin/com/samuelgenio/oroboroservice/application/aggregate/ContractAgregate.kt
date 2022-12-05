package com.samuelgenio.oroboroservice.application.aggregate

import com.samuelgenio.commonservice.commands.CreateContractCommand
import com.samuelgenio.commonservice.events.ContractCreatedEvent
import com.samuelgenio.oroboroservice.application.enums.ContractStatusEnum
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class ContractAgregate @CommandHandler constructor(
    val createContractCommand: CreateContractCommand) {

    @AggregateIdentifier lateinit var contractId: String
    lateinit var pendinContractId: String
    lateinit var status: ContractStatusEnum

    init {
        ContractCreatedEvent(
            contractId = createContractCommand.contractId,
            pendingContractId = createContractCommand.pendingContractId,
            status = ContractStatusEnum.ACTIVE.toString()
        ).let { AggregateLifecycle.apply(it) }
    }

    @EventSourcingHandler
    fun on (contractCreatedEvent: ContractCreatedEvent) {
        this.contractId = contractCreatedEvent.contractId
        this.pendinContractId = contractCreatedEvent.pendingContractId
        this.status = ContractStatusEnum.valueOf(contractCreatedEvent.status)
    }

}
