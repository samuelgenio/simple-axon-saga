package com.samuelgenio.commonservice.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class CancelPaymentCommand(
    @TargetAggregateIdentifier val paymentId: String,
    val pendingContractId: String,
    val status: String = "CANCELLED"
) {
    constructor(paymentId: String, pendingContractId: String) : this(paymentId, pendingContractId, "CANCELLED")
}
