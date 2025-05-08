package io.pressurediary.server.backend.managers

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

interface Manager

interface SimpleManager<Param, Request, Response> : Manager {
    suspend fun request(
        param: Param,
        request: Request
    ): Response

    suspend fun <T> requestTransaction(
        block: suspend Transaction.() -> T,
    ): T = newSuspendedTransaction(Dispatchers.IO) {
        block()
    }
}