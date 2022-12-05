package com.samuelgenio.commonservice.events

data class ContractCreatedEvent(
    val contractId: String,
    val pendingContractId: String,
    val status: String
)
