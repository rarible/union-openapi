package com.rarible.protocol.union.dto

import com.rarible.protocol.union.dto.parser.CurrencyIdParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UnionCurrencyIdParserTest {

    @Test
    fun `parse full`() {
        val value = "ETHEREUM:abc:123"
        val itemId = CurrencyIdParser.parse(value)
        assertEquals(BlockchainDto.ETHEREUM, itemId.blockchain)
        assertEquals("abc", itemId.contract)
        assertEquals("123", itemId.tokenId.toString())
    }

    @Test
    fun `parse short`() {
        val value = "POLYGON:abc"
        val itemId = CurrencyIdParser.parse(value)
        assertEquals(BlockchainDto.POLYGON, itemId.blockchain)
        assertEquals("abc", itemId.contract)
        assertEquals(null, itemId.tokenId)
    }
}
