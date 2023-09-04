package com.rarible.protocol.union.dto

import com.rarible.protocol.union.dto.parser.IdParser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.math.BigInteger

class IdParserTest {

    @Test
    fun `parse address`() {
        val id = "FLOW:gtt"
        val address = IdParser.parseAddress(id)

        assertEquals(BlockchainGroupDto.FLOW, address.blockchainGroup)
        assertEquals("gtt", address.value)
    }

    @Test
    fun `parse address - no blockchain`() {
        val id = "abc"
        assertThrows(BlockchainIdFormatException::class.java) {
            IdParser.parseAddress(id)
        }
    }

    @Test
    fun `parse address - case insensitive`() {
        val address = "ETHEREUM:AbC"
        val parsed = IdParser.parseAddress(address)

        assertEquals(BlockchainGroupDto.ETHEREUM, parsed.blockchainGroup)
        assertEquals("abc", parsed.value)
    }

    @Test
    fun `parse address - case sensitive`() {
        val address = "FLOW:aBC"
        val parsed = IdParser.parseAddress(address)

        assertEquals(BlockchainGroupDto.FLOW, parsed.blockchainGroup)
        assertEquals("aBC", parsed.value)
    }

    @Test
    fun `parse address - too many parts`() {
        val id = "ETHEREUM:abc:123"
        assertThrows(BlockchainIdFormatException::class.java) {
            IdParser.parseAddress(id)
        }
    }

    @Test
    fun `parse address - sub-chain specified`() {
        val id = "POLYGON:abc:123"
        assertThrows(BlockchainIdFormatException::class.java) {
            IdParser.parseAddress(id)
        }
    }

    @Test
    fun `parse contract - case insensitive`() {
        val contract = "IMMUTABLEX:ABC"
        val parsed = IdParser.parseContract(contract)

        assertEquals(BlockchainDto.IMMUTABLEX, parsed.blockchain)
        assertEquals("abc", parsed.value)
    }

    @Test
    fun `parse contract - case sensitive`() {
        val contact = "TEZOS:ABc"
        val parsed = IdParser.parseAddress(contact)

        assertEquals(BlockchainGroupDto.TEZOS, parsed.blockchainGroup)
        assertEquals("ABc", parsed.value)
    }

    @Test
    fun `parse order id - case sensitive`() {
        val id = "TEZOS:Aa231"
        val orderId = IdParser.parseOrderId(id)

        assertEquals(BlockchainDto.TEZOS, orderId.blockchain)
        assertEquals("Aa231", orderId.value)
    }

    @Test
    fun `parse order id - case insensitive`() {
        val id = "POLYGON:Aa231"
        val orderId = IdParser.parseOrderId(id)

        assertEquals(BlockchainDto.POLYGON, orderId.blockchain)
        assertEquals("aa231", orderId.value)
    }

    @Test
    fun `parse order id - too many parts`() {
        val id = "ETHEREUM:abc:123"
        assertThrows(BlockchainIdFormatException::class.java) {
            IdParser.parseOrderId(id)
        }
    }

    @Test
    fun `parse activity id - case insensitive`() {
        val id = "ETHEREUM:aBc"
        val activityId = IdParser.parseActivityId(id)

        assertEquals(BlockchainDto.ETHEREUM, activityId.blockchain)
        assertEquals("abc", activityId.value)
    }

    @Test
    fun `parse activity id - case sensitive`() {
        val id = "SOLANA:aBc"
        val activityId = IdParser.parseActivityId(id)

        assertEquals(BlockchainDto.SOLANA, activityId.blockchain)
        assertEquals("aBc", activityId.value)
    }

    @Test
    fun `parse activityId - with semicolon`() {
        val id = "ETHEREUM:abc:123"
        val activityId = IdParser.parseActivityId(id)

        assertEquals(BlockchainDto.ETHEREUM, activityId.blockchain)
        assertEquals("abc:123", activityId.value)
    }

    @Test
    fun `parse collection id - case insensitive`() {
        val id = "POLYGON:Aa111"
        val orderId = IdParser.parseCollectionId(id)

        assertEquals(BlockchainDto.POLYGON, orderId.blockchain)
        assertEquals("aa111", orderId.value)
    }

    @Test
    fun `parse collection id - case sensitive`() {
        val id = "FLOW:Aa111"
        val orderId = IdParser.parseCollectionId(id)

        assertEquals(BlockchainDto.FLOW, orderId.blockchain)
        assertEquals("Aa111", orderId.value)
    }

    @Test
    fun `parse collection id - with semicolon`() {
        val id = "FLOW:abc:123"
        val collectionId = IdParser.parseCollectionId(id)

        assertEquals(BlockchainDto.FLOW, collectionId.blockchain)
        assertEquals("abc:123", collectionId.value)
    }

    @Test
    fun `parse item id - case insensitive`() {
        val id = "IMMUTABLEX:aBc:1"
        val activityId = IdParser.parseItemId(id)

        assertEquals(BlockchainDto.IMMUTABLEX, activityId.blockchain)
        assertEquals("abc:1", activityId.value)
    }

    @Test
    fun `parse item id - fake id`() {
        val id = "ETHEREUM:aBc:-1"
        val activityId = IdParser.parseItemId(id)

        assertEquals(BlockchainDto.ETHEREUM, activityId.blockchain)
        assertEquals("abc:-1", activityId.value)
    }

    @Test
    fun `parse item id - non digit`() {
        val id = "ETHEREUM:aBc:abc"

        assertThrows(BlockchainIdFormatException::class.java) {
            IdParser.parseItemId(id)
        }
    }

    @Test
    fun `parse item id - case sensitive`() {
        val id = "SOLANA:aBc:1"
        val activityId = IdParser.parseItemId(id)

        assertEquals(BlockchainDto.SOLANA, activityId.blockchain)
        assertEquals("aBc:1", activityId.value)
    }

    @Test
    fun `split - ok`() {
        val id = "ETHEREUM:abc:123"
        val parts = IdParser.split(id, 3)

        assertEquals("ETHEREUM", parts[0])
        assertEquals("abc", parts[1])
        assertEquals("123", parts[2])
    }

    @Test
    fun `split with blockchain - wrong size`() {
        val id = "ETHEREUM:abc:123"
        assertThrows(BlockchainIdFormatException::class.java) {
            IdParser.split(id, 4)
        }
    }

    @Test
    fun `extract contract from itemId from eth`() {
        val contract = "abc"
        val id = ItemIdDto(BlockchainDto.ETHEREUM, contract, BigInteger.TEN)
        val extractedContract = IdParser.extractContract(id)
        assertThat(extractedContract).isEqualTo(contract)
    }

    @Test
    fun `extract token from itemId from eth`() {
        val token = "abc"
        val id = ItemIdDto(BlockchainDto.ETHEREUM, token)
        val extractedContract = IdParser.extractContract(id)
        assertThat(extractedContract).isNull()
    }
}
