package com.samuelgenio.userservice.infrastructure.projection;

import com.samuelgenio.commonservice.entities.CardBankSlipDetails;
import com.samuelgenio.commonservice.entities.User;
import com.samuelgenio.commonservice.infrastructure.queries.FindUserQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

@Service
public class InMemoryUserEventHandler {
    @QueryHandler
    public User handle(FindUserQuery query) {
        CardBankSlipDetails cardBankSlipDetails = new CardBankSlipDetails(
                "0123456",
                "Samuel Eugênio",
                "03",
                "2029",
                "007"
        );

        User user = new User(
                query.getUserId(),
                "Samuel",
                "Eugênio",
                cardBankSlipDetails
        );

        return user;
    }
}