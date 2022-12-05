package com.samuelgenio.bankservice.domain

import java.util.Date
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Payment (
    @Id val payment: String,
    val pendingContractId: String,
    val timestamp: Date,
    var status: String
)