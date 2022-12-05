package com.samuelgenio.commonservice.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class CreateContractCommand(
    @TargetAggregateIdentifier val contractId: String,
    val pendingContractId: String

)
