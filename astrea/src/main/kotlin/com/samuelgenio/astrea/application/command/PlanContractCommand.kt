package com.samuelgenio.astrea.application.command

import com.samuelgenio.astrea.application.enums.PaymentTypeEnum
import com.samuelgenio.astrea.application.enums.PlanStatusEnum
import com.samuelgenio.astrea.application.enums.PlanTypeEnum
import com.samuelgenio.astrea.application.events.PlanContractEvent
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class PlanContractCommand(
    @TargetAggregateIdentifier val pendingContractId: String,
    val planType: PlanTypeEnum,
    val userId: String,
    val paymentType: PaymentTypeEnum,
    val status: PlanStatusEnum
) {

    fun toEvent(): PlanContractEvent {
        return PlanContractEvent(
            pendingContractId = this.pendingContractId,
            status = this.status,
            paymentType = this.paymentType,
            userId = this.userId,
            planType = this.planType
        )
    }
}
