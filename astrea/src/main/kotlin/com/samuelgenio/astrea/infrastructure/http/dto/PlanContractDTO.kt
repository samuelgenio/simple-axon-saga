package com.samuelgenio.astrea.infrastructure.http.dto

import com.samuelgenio.astrea.application.command.PlanContractCommand
import com.samuelgenio.astrea.application.enums.PaymentTypeEnum
import com.samuelgenio.astrea.application.enums.PlanStatusEnum
import com.samuelgenio.astrea.application.enums.PlanTypeEnum
import javax.validation.constraints.NotNull

data class PlanContractDTO(

    @NotNull
    private val planType: PlanTypeEnum,
    @NotNull
    private val userId: String,
    @NotNull
    private val paymentType: PaymentTypeEnum,
    private val pendingContractId: String

) {
    fun toCommand(): PlanContractCommand {
        return PlanContractCommand(
            pendingContractId = pendingContractId,
            planType = planType,
            userId = userId,
            paymentType = paymentType,
            status = PlanStatusEnum.PENDING
        )
    }
}
