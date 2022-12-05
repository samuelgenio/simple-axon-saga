package com.samuelgenio.astrea.application.events

data class PlanCancelledEvent(
    val pendingContractId: String,
    val status: String
)
