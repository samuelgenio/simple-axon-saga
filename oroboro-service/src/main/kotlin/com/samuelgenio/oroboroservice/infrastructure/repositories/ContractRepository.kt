package com.samuelgenio.oroboroservice.infrastructure.repositories

import com.samuelgenio.oroboroservice.domain.Contract
import org.springframework.data.jpa.repository.JpaRepository

interface ContractRepository : JpaRepository<Contract, String>