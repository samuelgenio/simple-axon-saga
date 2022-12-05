package com.samuelgenio.astrea.application.aggregate;

import com.samuelgenio.astrea.application.command.PlanContractCommand;
import com.samuelgenio.astrea.application.enums.PaymentTypeEnum;
import com.samuelgenio.astrea.application.enums.PlanStatusEnum;
import com.samuelgenio.astrea.application.enums.PlanTypeEnum;
import com.samuelgenio.astrea.application.events.PlanCancelledEvent;
import com.samuelgenio.astrea.application.events.PlanContractEvent;
import com.samuelgenio.commonservice.commands.CancelPlanCommand;
import com.samuelgenio.commonservice.commands.CreatedContractCommand;
import com.samuelgenio.commonservice.events.PlanContractedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class PlanContractAggregateJava {
    @AggregateIdentifier
    private String pendingContractId;
    private PlanTypeEnum planType;
    private String userId;
    private PaymentTypeEnum paymentType;
    private PlanStatusEnum status;

    public PlanContractAggregateJava() {
    }

    @CommandHandler
    public PlanContractAggregateJava(PlanContractCommand planContractCommand) {
        PlanContractEvent event = planContractCommand.toEvent();
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(CreatedContractCommand createdContractCommand) {
        PlanContractedEvent event = new PlanContractedEvent(createdContractCommand.getPendingContractId(), createdContractCommand.getStatus());
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(PlanContractEvent event) {
        this.pendingContractId = event.getPendingContractId();
        this.planType = event.getPlanType();
        this.userId = event.getUserId();
        this.paymentType = event.getPaymentType();
        this.status = event.getStatus();
    }

    @EventSourcingHandler
    public void on(PlanContractedEvent event) {
        this.status = PlanStatusEnum.valueOf(event.getStatus());
    }

    @CommandHandler
    public void handle(CancelPlanCommand cancelPlanCommand) {
        PlanCancelledEvent event =
                new PlanCancelledEvent(
                        cancelPlanCommand.getPendingContractId(),
                        cancelPlanCommand.getStatus());

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(PlanCancelledEvent event) {
        this.status = PlanStatusEnum.valueOf(event.getStatus());
    }

}