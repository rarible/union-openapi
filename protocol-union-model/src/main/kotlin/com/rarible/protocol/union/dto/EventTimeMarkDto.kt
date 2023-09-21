package com.rarible.protocol.union.dto

import java.time.Instant

data class EventTimeMarkDto(
    val name: String,
    val date: Instant
) {

    override fun toString(): String {
        return "[$name:$date]"
    }
}