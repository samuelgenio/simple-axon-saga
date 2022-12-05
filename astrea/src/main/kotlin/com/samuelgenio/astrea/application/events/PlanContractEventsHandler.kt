package com.samuelgenio.astrea.application.events

import com.samuelgenio.astrea.infrastructure.repositories.PendingContractRepository
import com.samuelgenio.commonservice.events.PlanContractedEvent
import org.axonframework.eventhandling.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PlanContractEventsHandler @Autowired constructor(
    val pendingContractRepository: PendingContractRepository
) {

    @EventHandler
    fun on(planContractEvent: PlanContractEvent) {
        pendingContractRepository.save(planContractEvent.toDomain())
    }

    @EventHandler
    fun on(planContractedEvent: PlanContractedEvent) {
        pendingContractRepository.findById(planContractedEvent.pendingContractId).get()
            .apply { status = planContractedEvent.status }
            .let(pendingContractRepository::save)
    }

    @EventHandler
    fun on(planCancelledEvent: PlanCancelledEvent) {
        pendingContractRepository.findById(planCancelledEvent.pendingContractId).get()
            .apply { status = planCancelledEvent.status }
            .let(pendingContractRepository::save)
    }

}