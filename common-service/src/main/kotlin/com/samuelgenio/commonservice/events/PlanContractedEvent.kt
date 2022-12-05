package com.samuelgenio.commonservice.events

data class PlanContractedEvent(
    val pendingContractId: String,
    val status: String
)
