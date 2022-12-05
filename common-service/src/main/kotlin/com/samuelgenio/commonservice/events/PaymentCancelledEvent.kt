package com.samuelgenio.commonservice.events

data class PaymentCancelledEvent(
    val paymentId: String,
    val pendingContractId: String,
    val status: String
)

