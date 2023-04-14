package com.rarible.protocol.union.dto

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ItemIdDtoTest {

    @Test
    fun `sort - lexicographically`() {
        val id1 = ItemIdDto(BlockchainDto.ETHEREUM, "5")
        val id2 = ItemIdDto(BlockchainDto.ETHEREUM, "6")
        val id3 = ItemIdDto(BlockchainDto.FLOW, "3")
        val id4 = ItemIdDto(BlockchainDto.POLYGON, "1")
        val id5 = ItemIdDto(BlockchainDto.SOLANA, "2")

        val sorted = mutableListOf(id1, id2, id3, id4, id5)
        sorted.shuffle()
        sorted.sortWith(ItemIdDto.Comparators.LEXICOGRAPHICAL)

        assertThat(sorted).isEqualTo(listOf(id1, id2, id3, id4, id5))
    }

}