package com.rarible.protocol.union.dto.continuation

import java.time.Instant

data class DateContinuation(
    val date: Instant,
    val id: String,
    val asc: Boolean = false
) : Continuation<DateContinuation> {

    private val sign = if (asc) 1 else -1

    override fun toString(): String {
        return "${date.toEpochMilli()}_${id}"
    }

    override fun compareTo(other: DateContinuation): Int {
        val dateDiff = this.date.compareTo(other.date)
        if (dateDiff != 0) {
            return sign * dateDiff
        }
        return 0
    }

    companion object {
        fun parse(str: String?): DateContinuation? {
            val pair = Continuation.splitBy(str, "_") ?: return null
            val timestamp = pair.first
            val id = pair.second
            return DateContinuation(Instant.ofEpochMilli(timestamp.toLong()), id)
        }
    }


}
