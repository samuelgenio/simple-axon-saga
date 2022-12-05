package com.samuelgenio.commonservice.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class CreatedContractCommand(
    @TargetAggregateIdentifier val pendingContractId: String,
    val status: String
)
