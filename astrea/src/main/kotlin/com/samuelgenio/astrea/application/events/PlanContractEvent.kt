package com.samuelgenio.astrea.application.events

import com.samuelgenio.astrea.application.enums.PaymentTypeEnum
import com.samuelgenio.astrea.application.enums.PlanStatusEnum
import com.samuelgenio.astrea.application.enums.PlanTypeEnum
import com.samuelgenio.astrea.domain.PendingContract

data class PlanContractEvent(
    val pendingContractId: String,
    val planType: PlanTypeEnum,
    val userId: String,
    val paymentType: PaymentTypeEnum,
    val status: PlanStatusEnum
) {
    fun toDomain(): PendingContract {
        return PendingContract(
            pendingContractId = pendingContractId,
            planType = planType.name,
            userid = userId,
            paymentType = paymentType.name,
            status = status.name
        )
    }
}