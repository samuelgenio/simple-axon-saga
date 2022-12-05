package com.samuelgenio.commonservice.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class CancelPlanCommand(
    @TargetAggregateIdentifier val pendingContractId: String,
    val status: String = "ERROR"
) {
    constructor(pendingContractId: String) : this(pendingContractId, "ERROR")
}
