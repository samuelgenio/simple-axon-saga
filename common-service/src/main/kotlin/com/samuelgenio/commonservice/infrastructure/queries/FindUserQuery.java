package com.samuelgenio.commonservice.infrastructure.queries;

public class FindUserQuery {

    public String getUserId() {
        return userId;
    }

    private String userId;

    public FindUserQuery(String userId) {
        this.userId = userId;
    }
}
