package com.dragonguard.alert

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AlertApplication

fun main(args: Array<String>) {
	runApplication<AlertApplication>(*args)
}
