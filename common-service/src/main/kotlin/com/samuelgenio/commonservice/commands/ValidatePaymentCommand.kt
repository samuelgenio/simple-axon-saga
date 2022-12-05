package com.samuelgenio.commonservice.commands

import com.samuelgenio.commonservice.entities.CardBankSlipDetails
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class ValidatePaymentCommand(
    @TargetAggregateIdentifier val paymentId: String,
    val pendingContractId: String,
    val cardbankSlipDetails: CardBankSlipDetails
)
