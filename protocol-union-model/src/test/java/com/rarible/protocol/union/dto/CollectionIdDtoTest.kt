package com.rarible.protocol.union.dto

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CollectionIdDtoTest {

    @Test
    fun `sort - lexicographically`() {
        val id1 = CollectionIdDto(BlockchainDto.ETHEREUM, "3")
        val id2 = CollectionIdDto(BlockchainDto.FLOW, "1")
        val id3 = CollectionIdDto(BlockchainDto.FLOW, "2")
        val id4 = CollectionIdDto(BlockchainDto.SOLANA, "1")
        val id5 = CollectionIdDto(BlockchainDto.TEZOS, "1")

        val sorted = mutableListOf(id1, id2, id3, id4, id5)
        sorted.shuffle()
        sorted.sortWith(CollectionIdDto.Comparators.LEXICOGRAPHICAL)

        assertThat(sorted).isEqualTo(listOf(id1, id2, id3, id4, id5))
    }

}