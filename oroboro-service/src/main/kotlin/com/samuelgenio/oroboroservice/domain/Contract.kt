package com.samuelgenio.oroboroservice.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Contract(
    @Id val contractId: String,
    val pendinContractId: String,
    val status: String
)
