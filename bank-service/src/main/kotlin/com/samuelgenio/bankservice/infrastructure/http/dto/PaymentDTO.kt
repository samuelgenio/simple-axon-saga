package com.samuelgenio.bankservice.infrastructure.http.dto

import com.samuelgenio.commonservice.commands.ValidatePaymentCommand

data class PaymentDTO(

    val paymentId: String,
    val pendingContractId: String,

)