package com.samuelgenio.bankservice.infrastructure.http.api

import com.samuelgenio.bankservice.infrastructure.http.dto.PaymentDTO
import com.samuelgenio.commonservice.commands.ValidatePaymentCommand
import com.samuelgenio.commonservice.entities.User
import com.samuelgenio.commonservice.infrastructure.queries.FindUserQuery
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("payment")
class PaymentAPI @Autowired constructor(
    private val commandGateway: CommandGateway,
    private val queryGateway: QueryGateway) {

    @PostMapping
    fun contract(@RequestBody paymentDTO: PaymentDTO): String {

        val filter = FindUserQuery("1")

        var user: User? = null

        runCatching {
            user = queryGateway.query(
                filter,
                ResponseTypes.instanceOf(User::class.java)).get()
        }.onFailure {
            it.printStackTrace()
            //do compensating transaction
        }

        val planContractCommandDTO = ValidatePaymentCommand(
            pendingContractId = paymentDTO.pendingContractId,
            paymentId = paymentDTO.paymentId,
            cardbankSlipDetails = user!!.cardBankSlipDetails
        )

        commandGateway.sendAndWait<Any>(planContractCommandDTO)

        return "Payment applied"
    }

}