package com.rarible.protocol.union.dto.continuation

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Instant

class DateContinuationTest {

    @Test
    fun `parse continuation`() {
        val continuation = DateContinuation.parse("123_abc")!!

        assertEquals(123L, continuation.date.toEpochMilli())
        assertEquals("abc", continuation.id)
    }

    @Test
    fun `parse continuation - with second underscore`() {
        val continuation = DateContinuation.parse("123_abc_ef")!!

        assertEquals(123L, continuation.date.toEpochMilli())
        assertEquals("abc_ef", continuation.id)
    }

    @Test
    fun `parse continuation - incorrect format`() {
        assertNull(DateContinuation.parse(null))
        assertNull(DateContinuation.parse(""))
        assertNull(DateContinuation.parse("abc"))
        assertThrows(IllegalArgumentException::class.java) {
            DateContinuation.parse("abc_abc")
        }
    }

    @Test
    fun `compare records with preserved id order`() {
        val now = Instant.now()

        val a = DateContinuation(now.plusMillis(1), "a")
        val b = DateContinuation(now, "z")
        val c = DateContinuation(now, "a")
        val d = DateContinuation(now.minusMillis(1), "2")
        val e = DateContinuation(now.minusMillis(1), "10")

        val list = listOf(d, e, b, c, a).sorted()

        assertThat(list).isEqualTo(listOf(a, b, c, d, e))
    }
}
