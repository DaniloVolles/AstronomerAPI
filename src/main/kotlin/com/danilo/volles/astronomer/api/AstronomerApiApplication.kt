package com.danilo.volles.astronomer.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class AstronomerApiApplication

fun main(args: Array<String>) {
    runApplication<AstronomerApiApplication>(*args)
}
