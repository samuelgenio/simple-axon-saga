package com.samuelgenio.bankservice.infrastructure.repositories

import com.samuelgenio.bankservice.domain.Payment
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentRepository : JpaRepository<Payment, String> {

    fun getReferenceByPendingContractId(id: String): List<Payment>

}