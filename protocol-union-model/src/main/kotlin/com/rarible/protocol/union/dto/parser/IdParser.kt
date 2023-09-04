package com.rarible.protocol.union.dto.parser

import com.rarible.protocol.union.dto.ActivityIdDto
import com.rarible.protocol.union.dto.AuctionIdDto
import com.rarible.protocol.union.dto.BlockchainDto
import com.rarible.protocol.union.dto.BlockchainGroupDto
import com.rarible.protocol.union.dto.BlockchainIdFormatException
import com.rarible.protocol.union.dto.CollectionIdDto
import com.rarible.protocol.union.dto.ContractAddress
import com.rarible.protocol.union.dto.ItemIdDto
import com.rarible.protocol.union.dto.OrderIdDto
import com.rarible.protocol.union.dto.UnionAddress
import com.rarible.protocol.union.dto.normalizeAddress
import com.rarible.protocol.union.dto.normalizeContract
import com.rarible.protocol.union.dto.normalizeId

object IdParser {

    const val DELIMITER = ":"

    fun parseBlockchain(value: String): BlockchainDto {
        try {
            return BlockchainDto.valueOf(value)
        } catch (e: Throwable) {
            throw BlockchainIdFormatException(
                "Unsupported blockchain value: $value, supported are:" +
                    " ${BlockchainDto.values().map { it.name }.joinToString("|")}"
            )
        }
    }

    fun parseBlockchainGroup(value: String): BlockchainGroupDto {
        try {
            return BlockchainGroupDto.valueOf(value)
        } catch (e: Throwable) {
            throw BlockchainIdFormatException(
                "Unsupported blockchain group value: $value, supported are:" +
                    " ${BlockchainGroupDto.values().map { it.name }.joinToString("|")}"
            )
        }
    }

    fun parseContract(value: String): ContractAddress {
        val pair = split(value, 2)
        val blockchain = parseBlockchain(pair[0])
        return ContractAddress(blockchain, blockchain.normalizeAddress(pair[1]))
    }

    fun parseAddress(value: String): UnionAddress {
        val pair = split(value, 2)
        val blockchainGroup = parseBlockchainGroup(pair[0])
        return UnionAddress(blockchainGroup, blockchainGroup.normalizeContract(pair[1]))
    }

    fun parseItemId(value: String): ItemIdDto {
        // For ItemId there can be ':' in value (token:tokenId for most of the blockchains)
        val (blockchain, id) = extractBlockchain(value)
        if (blockchain != BlockchainDto.SOLANA) {
            val parts = id.split(":")
            if (parts.size != 2 || !parts[1].all { it.isDigit() } && parts[1] != "-1") {
                throw BlockchainIdFormatException("Invalid item id format: $value")
            }
        }
        return ItemIdDto(blockchain, blockchain.normalizeId(id))
    }

    fun parseCollectionId(value: String): CollectionIdDto {
        val (blockchain, id) = extractBlockchain(value)
        return CollectionIdDto(blockchain, blockchain.normalizeId(id))
    }

    fun parseOrderId(value: String): OrderIdDto {
        val pair = split(value, 2)
        val blockchain = parseBlockchain(pair[0])
        return OrderIdDto(blockchain, blockchain.normalizeId(pair[1]))
    }

    fun parseAuctionId(value: String): AuctionIdDto {
        val pair = split(value, 2)
        val blockchain = parseBlockchain(pair[0])
        return AuctionIdDto(blockchain, blockchain.normalizeId(pair[1]))
    }

    fun parseActivityId(value: String): ActivityIdDto {
        val (blockchain, id) = extractBlockchain(value)
        return ActivityIdDto(blockchain, blockchain.normalizeId(id))
    }

    fun split(value: String, expectedSize: Int): List<String> {
        return split(value, IntRange(expectedSize, expectedSize))
    }

    fun split(value: String, expectedRange: IntRange): List<String> {
        val parts = value.split(DELIMITER)
        assertSize(value, parts, expectedRange)
        return parts
    }

    fun extractBlockchain(value: String): Pair<BlockchainDto, String> {
        val delim = value.indexOf(DELIMITER)
        if (delim < 0) {
            throw BlockchainIdFormatException(
                "Illegal format for ID: '$value', blockchain prefix not found"
            )
        }
        val blockchain = parseBlockchain(value.substring(0, delim))
        val id = value.substring(delim + DELIMITER.length)
        return Pair(blockchain, id)
    }

    fun extractContract(value: ItemIdDto): String? {
        // For ItemId there can be ':' in value (token:tokenId for most of the blockchains)
        // in some cases (Solana) there is no contract part
        val id = value.value
        val parts = id.split(DELIMITER)
        return if (parts.size >= 2) parts.first() else null
    }

    private fun assertSize(
        value: String,
        parts: List<String>,
        expectedRange: IntRange
    ) {
        if (expectedRange.contains(parts.size).not()) {
            val expectedRangeToMessage: () -> String = {
                if (expectedRange.isSingle()) "${expectedRange.first}" else "${expectedRange.first} or ${expectedRange.last}"
            }
            throw BlockchainIdFormatException(
                "Illegal format for ID: '$value', " +
                    "expected ${expectedRangeToMessage()} parts in ID, concatenated by '$DELIMITER'"
            )
        }
    }

    private fun IntRange.isSingle(): Boolean = first == last
}
