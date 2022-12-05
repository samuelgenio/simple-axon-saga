package com.samuelgenio.bankservice.application.aggregate

import com.samuelgenio.bankservice.application.enums.PaymentStatusEnum
import com.samuelgenio.bankservice.infrastructure.log.Logger
import com.samuelgenio.commonservice.commands.CancelPaymentCommand
import com.samuelgenio.commonservice.commands.ValidatePaymentCommand
import com.samuelgenio.commonservice.events.PaymentCancelledEvent
import com.samuelgenio.commonservice.events.PaymentProcessedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class PaymentAggregate {

    @AggregateIdentifier private var paymentId: String? = null
    private var pendingContractId: String? = null
    private var status: PaymentStatusEnum? = null

    private var logger: Logger = Logger()

    constructor() {
    }

    @CommandHandler
    constructor(validatePaymentCommand: ValidatePaymentCommand) {
        logger.info(
            Logger.SAGA_EVENT.format(ValidatePaymentCommand::class.java, validatePaymentCommand.pendingContractId))

        val paymentProcessedEvent = PaymentProcessedEvent(
            paymentId = validatePaymentCommand.paymentId,
            pendingContractId = validatePaymentCommand.pendingContractId
        )
        paymentId = paymentProcessedEvent.paymentId
        pendingContractId = paymentProcessedEvent.pendingContractId
        AggregateLifecycle.apply(paymentProcessedEvent)
        logger.info("${PaymentProcessedEvent::class.java} applied!")


    }

    @EventSourcingHandler
    fun on(paymentProcessedEvent: PaymentProcessedEvent) {
        paymentId = paymentProcessedEvent.paymentId
        pendingContractId = paymentProcessedEvent.pendingContractId
    }

    @CommandHandler
    fun handle(cancelPaymentCommand: CancelPaymentCommand) {
        val event = PaymentCancelledEvent(
            cancelPaymentCommand.paymentId,
            cancelPaymentCommand.pendingContractId,
            cancelPaymentCommand.status!!)

        AggregateLifecycle.apply(event)
    }

    @EventSourcingHandler
    fun on(paymentCancelledEvent: PaymentCancelledEvent) {
        this.status = PaymentStatusEnum.valueOf(paymentCancelledEvent.status)
    }

}