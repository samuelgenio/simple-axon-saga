package com.samuelgenio.commonservice.entities

data class User(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val cardBankSlipDetails: CardBankSlipDetails
)
