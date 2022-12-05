package com.samuelgenio.bankservice.application.events

import com.samuelgenio.bankservice.application.enums.PaymentStatusEnum
import com.samuelgenio.bankservice.domain.Payment
import com.samuelgenio.bankservice.infrastructure.repositories.PaymentRepository
import com.samuelgenio.commonservice.events.PaymentCancelledEvent
import com.samuelgenio.commonservice.events.PaymentProcessedEvent
import org.axonframework.eventhandling.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.Date

@Component
class PaymentsEventHandler @Autowired constructor(
    val paymentRepository: PaymentRepository
) {

    @EventHandler
    fun on(event: PaymentProcessedEvent) {
        Payment(
            payment = event.paymentId,
            pendingContractId = event.pendingContractId,
            timestamp = Date(),
            status = PaymentStatusEnum.PAID.name
        ).let(paymentRepository::save)
            .let { println("paymentProcessedEvent save") }
    }

    @EventHandler
    fun on(event: PaymentCancelledEvent) {
        paymentRepository.findById(event.paymentId).get()
            .apply { status = event.status }
            .let(paymentRepository::save)
    }

}