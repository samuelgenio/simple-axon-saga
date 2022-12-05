package com.samuelgenio.astrea.domain

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "pendingContract")
data class PendingContract(
    @Id
    val pendingContractId: String,
    var planType: String,
    val userid: String,
    var paymentType: String,
    var status: String
)
