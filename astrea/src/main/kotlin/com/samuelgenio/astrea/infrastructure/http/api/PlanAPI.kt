package com.samuelgenio.astrea.infrastructure.http.api

import com.samuelgenio.astrea.infrastructure.http.dto.PlanContractDTO
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("plan")
class PlanAPI @Autowired constructor(
    private val commandGateway: CommandGateway) {

    @PostMapping("contract")
    fun planContract(@RequestBody @Valid planContractDTO: PlanContractDTO): String {

        val planContractCommandDTO = planContractDTO.toCommand()

        commandGateway.sendAndWait<Any>(planContractCommandDTO)

        return "Plan Contract"
    }

}