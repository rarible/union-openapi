package com.rarible.protocol.union.dto.parser

import com.rarible.protocol.union.dto.ActivityIdDto
import com.rarible.protocol.union.dto.BlockchainDto
import com.rarible.protocol.union.dto.BlockchainGroupDto
import com.rarible.protocol.union.dto.BlockchainIdFormatException
import com.rarible.protocol.union.dto.ContractAddress
import com.rarible.protocol.union.dto.OrderIdDto
import com.rarible.protocol.union.dto.UnionAddress

object IdParser {

    private const val DELIMITER = ":"

    fun parseBlockchain(value: String): BlockchainDto {
        try {
            return BlockchainDto.valueOf(value)
        } catch (e: Throwable) {
            throw BlockchainIdFormatException("Unsupported blockchain value: $value, supported are:" +
                    " ${BlockchainDto.values().map { it.name }.joinToString { "|" }}"
            )
        }
    }

    fun parseBlockchainGroup(value: String): BlockchainGroupDto {
        try {
            return BlockchainGroupDto.valueOf(value)
        } catch (e: Throwable) {
            throw BlockchainIdFormatException("Unsupported blockchain group value: $value, supported are:" +
                    " ${BlockchainGroupDto.values().map { it.name }.joinToString { "|" }}"
            )
        }
    }

    fun parseAddress(value: String): UnionAddress {
        val pair = split(value, 2)
        return UnionAddress(parseBlockchainGroup(pair[0]), pair[1])
    }

    fun parseOrderId(value: String): OrderIdDto {
        val pair = split(value, 2)
        return OrderIdDto(parseBlockchain(pair[0]), pair[1])
    }

    fun parseActivityId(value: String): ActivityIdDto {
        val pair = split(value, 2)
        return ActivityIdDto(parseBlockchain(pair[0]), pair[1])
    }

    fun parseContract(value: String): ContractAddress {
        val pair = split(value, 2)
        return ContractAddress(parseBlockchain(pair[0]), pair[1])
    }

    fun split(value: String, expectedSize: Int): List<String> {
        val parts = value.split(DELIMITER)
        assertSize(value, parts, expectedSize)
        return parts
    }

    private fun assertSize(
        value: String,
        parts: List<String>,
        expectedSize: Int
    ) {
        if (parts.size != expectedSize) {
            throw BlockchainIdFormatException(
                "Illegal format for ID: '$value', " +
                    "expected $expectedSize parts in ID, concatenated by '$DELIMITER'"
            )
        }
    }

}