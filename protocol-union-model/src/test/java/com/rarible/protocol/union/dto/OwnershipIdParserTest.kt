package com.rarible.protocol.union.dto

import com.rarible.protocol.union.dto.parser.OwnershipIdParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OwnershipIdParserTest {

    @Test
    fun `parse full`() {
        val value = "SOLANA:abc123:xyz"
        val ownershipId = OwnershipIdParser.parseFull(value)
        assertEquals(BlockchainDto.SOLANA, ownershipId.blockchain)
        assertEquals("abc123", ownershipId.itemIdValue)
        assertEquals("xyz", ownershipId.owner.value)
    }

    @Test
    fun `parse short`() {
        val value = "abc123:xyz"
        val ownershipId = OwnershipIdParser.parseShort(value, BlockchainDto.SOLANA)
        assertEquals(BlockchainDto.SOLANA, ownershipId.blockchain)
        assertEquals("abc123", ownershipId.itemIdValue)
        assertEquals("xyz", ownershipId.owner.value)
    }

    @Test
    fun `parse full - legacy`() {
        val value = "POLYGON:abc:123:xyz"
        val ownershipId = OwnershipIdParser.parseFull(value)
        assertEquals(BlockchainDto.POLYGON, ownershipId.blockchain)
        assertEquals("abc:123", ownershipId.itemIdValue)
        assertEquals("xyz", ownershipId.owner.value)
    }

    @Test
    fun `parse short - legacy`() {
        val value = "abc:123:xyz"
        val ownershipId = OwnershipIdParser.parseShort(value, BlockchainDto.ETHEREUM)
        assertEquals(BlockchainDto.ETHEREUM, ownershipId.blockchain)
        assertEquals("abc:123", ownershipId.itemIdValue)
        assertEquals("xyz", ownershipId.owner.value)
    }

}