package com.backend.managers

interface Manager

interface SimpleManager<Param, Request, Response> : Manager {
    suspend operator fun invoke(param: Param, request: Request): Response
}