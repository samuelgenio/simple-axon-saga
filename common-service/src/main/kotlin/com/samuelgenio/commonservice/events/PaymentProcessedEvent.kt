package com.samuelgenio.commonservice.events

data class PaymentProcessedEvent(
    val paymentId: String,
    val pendingContractId: String
)
