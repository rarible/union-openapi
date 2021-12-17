package com.rarible.protocol.union.dto.continuation.page

import com.rarible.protocol.union.dto.ItemDto
import com.rarible.protocol.union.dto.continuation.ItemContinuation
import com.rarible.protocol.union.dto.parser.ItemIdParser
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Instant

class PagingTest {

    @Test
    fun `get page - trimmed and ordered`() {
        val lastUpdated = Instant.now()
        val items = (1..15L).map { createItem(lastUpdated.minusMillis(it)) }

        val paging = Paging(ItemContinuation.ByLastUpdatedAndId, items.shuffled())

        val page = paging.getSlice(5)
        val oldestTs = page.entities.map { it.lastUpdatedAt.toEpochMilli() }.minOrNull()
        val last = page.entities.last()

        assertThat(page.entities).hasSize(5)
        assertThat(oldestTs).isEqualTo(lastUpdated.toEpochMilli() - 5)
        assertThat(oldestTs).isEqualTo(last.lastUpdatedAt.toEpochMilli())
        assertThat(page.continuation).isEqualTo(ItemContinuation.ByLastUpdatedAndId.getContinuation(last).toString())
    }

    @Test
    fun `get page - last page`() {
        val lastUpdated = Instant.now()
        val items = (1..15L).map { createItem(lastUpdated.minusMillis(it)) }

        val paging = Paging(ItemContinuation.ByLastUpdatedAndId, items.shuffled())

        val page = paging.getSlice(20)
        assertThat(page.entities).hasSize(15)
        assertThat(page.continuation).isNull()
    }

    @Test
    fun `get page - no items`() {
        val paging = Paging(ItemContinuation.ByLastUpdatedAndId, listOf())

        val page = paging.getSlice(10)
        assertThat(page.entities).hasSize(0)
        assertThat(page.continuation).isNull()
    }

    private fun createItem(lastUpdated: Instant): ItemDto {

        val item: ItemDto = mockk()
        val id = ItemIdParser.parseFull("ETHEREUM:lkjfdh:123")
        every { item.lastUpdatedAt } returns lastUpdated
        every { item.id } returns id

        return item
    }

}