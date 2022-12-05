package com.samuelgenio.userservice.infrastructure.projection

import com.samuelgenio.commonservice.entities.CardBankSlipDetails
import com.samuelgenio.commonservice.entities.User
import com.samuelgenio.commonservice.infrastructure.queries.GetUserPaymentDetailsQuery
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

@Component
class UserProjection {

    init {
        println("init UserProjection")
    }

    @QueryHandler
    fun getUserPaymentDetails(query: GetUserPaymentDetailsQuery): String {
        val cardBankSlipDetails = CardBankSlipDetails(
            cardBankslipNumber = "0123456",
            name = "Samuel Eugênio",
            validUntilMonth = "03",
            validUntilYear = "2029",
            cvv = "007"
        )

        val user = User(
            userId = query.userId,
            cardBankSlipDetails = cardBankSlipDetails,
            firstName = "Samuel",
            lastName = "Eugênio"
        )

        return "retorno"
    }


}