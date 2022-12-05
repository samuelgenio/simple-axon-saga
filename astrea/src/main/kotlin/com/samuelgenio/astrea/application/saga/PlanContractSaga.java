package com.samuelgenio.astrea.application.saga;

import com.samuelgenio.astrea.application.enums.PlanStatusEnum;
import com.samuelgenio.astrea.application.events.PlanCancelledEvent;
import com.samuelgenio.astrea.application.events.PlanContractEvent;
import com.samuelgenio.astrea.infrastructure.log.Logger;
import com.samuelgenio.commonservice.commands.*;
import com.samuelgenio.commonservice.entities.User;
import com.samuelgenio.commonservice.events.ContractCreatedEvent;
import com.samuelgenio.commonservice.events.PaymentCancelledEvent;
import com.samuelgenio.commonservice.events.PaymentProcessedEvent;
import com.samuelgenio.commonservice.events.PlanContractedEvent;
import com.samuelgenio.commonservice.infrastructure.queries.FindUserQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
public class PlanContractSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    @Autowired
    private transient Logger logger;

    public PlanContractSaga() {
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "pendingContractId")
    public void handle(PlanContractEvent event) {
        logger.info(
                String.format(Logger.START_SAGA, PlanContractEvent.class.getName(), event.getPendingContractId()), null);

        FindUserQuery query
                = new FindUserQuery(event.getUserId());

        User user = null;

        try {
            user = queryGateway.query(
                    query,
                    ResponseTypes.instanceOf(User.class)
            ).join();

        } catch (Exception e) {
            //Start the Compensating transaction
            cancelPlanCommand(event.getPendingContractId());
        }

        ValidatePaymentCommand validatePaymentCommand = new ValidatePaymentCommand(
                UUID.randomUUID().toString(),
                event.getPendingContractId(),
                user.getCardBankSlipDetails());

        commandGateway.sendAndWait(validatePaymentCommand);
    }

    private void cancelPlanCommand(String pendingContractId) {
        CancelPlanCommand command = new CancelPlanCommand(pendingContractId);
        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "pendingContractId")
    public void handle(PaymentProcessedEvent event) {
        logger.info(
                String.format(Logger.SAGA_EVENT, PaymentProcessedEvent.class.getName(), event.getPendingContractId()), null);
        try{

            //if(true)
                //throw new Exception();

            CreateContractCommand command = new CreateContractCommand(UUID.randomUUID().toString(), event.getPendingContractId());
            commandGateway.send(command);
        } catch (Exception e) {
            //Start the Compensating transaction
            cancelPaymentCommand(event);
        }
    }

    private void cancelPaymentCommand(PaymentProcessedEvent event) {
        CancelPaymentCommand command
                = new CancelPaymentCommand(
                event.getPaymentId(),
                event.getPendingContractId());

        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "pendingContractId")
    @EndSaga
    public void handle(PlanContractedEvent planContractedEvent) {
        logger.info("Fim do processo de contratração, disparar alertar", null);
    }

    @SagaEventHandler(associationProperty = "pendingContractId")
    public void handle(ContractCreatedEvent event) {
        logger.info(
                String.format(Logger.SAGA_EVENT, ContractCreatedEvent.class.getName(), event.getPendingContractId()), null);

        CreatedContractCommand command = new CreatedContractCommand(event.getPendingContractId(), PlanStatusEnum.CONTRACT.toString());
        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "pendingContractId")
    @EndSaga
    public void handle(PlanCancelledEvent event) {
        logger.info(
                String.format(Logger.SAGA_EVENT, PlanCancelledEvent.class.getName(), event.getPendingContractId()), null);
        cancelPlanCommand(event.getPendingContractId());
    }

    @SagaEventHandler(associationProperty = "pendingContractId")
    public void handle(PaymentCancelledEvent event) {
        logger.info(
                String.format(Logger.SAGA_EVENT, PaymentCancelledEvent.class.getName(), event.getPendingContractId()), null);
        cancelPlanCommand(event.getPendingContractId());
    }

}
