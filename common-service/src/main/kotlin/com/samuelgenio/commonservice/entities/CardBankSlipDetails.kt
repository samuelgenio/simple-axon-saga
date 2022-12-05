package com.samuelgenio.commonservice.entities

data class CardBankSlipDetails(
    val cardBankslipNumber: String?,
    val name: String?,
    val validUntilMonth: String?,
    val validUntilYear: String?,
    val cvv: String?,
)
