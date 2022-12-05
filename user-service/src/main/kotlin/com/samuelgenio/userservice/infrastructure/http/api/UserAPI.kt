package com.samuelgenio.userservice.infrastructure.http.api


import com.samuelgenio.commonservice.entities.User
import com.samuelgenio.commonservice.infrastructure.queries.FindUserQuery
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("user")
class UserAPI @Autowired constructor(val queryGateway: QueryGateway){

    @GetMapping("{userId}")
    fun getUser(@PathVariable userId: String): User? {

        val filter = FindUserQuery(userId)

        var user: User? = null

        runCatching {
            user = queryGateway.query(filter, ResponseTypes.instanceOf(User::class.java)).get()
        }.onFailure {
            it.stackTrace
            println(it.message)
        }

        return user

    }

}