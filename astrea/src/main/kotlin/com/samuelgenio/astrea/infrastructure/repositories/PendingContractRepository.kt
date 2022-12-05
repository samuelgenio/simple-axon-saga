package com.samuelgenio.astrea.infrastructure.repositories

import com.samuelgenio.astrea.domain.PendingContract
import org.springframework.data.jpa.repository.JpaRepository

interface PendingContractRepository : JpaRepository<PendingContract, String>