package com.rarible.protocol.union.dto

private val subchains = mapOf(
    // First Blockchain specified in the list considered as PRIMARY -
    // it means, there should not be situation like "Polygon is enabled, Ethereum is disabled"
    // We using first blockchain of the group to validate user's signature
    BlockchainGroupDto.ETHEREUM to listOf(BlockchainDto.ETHEREUM, BlockchainDto.POLYGON, BlockchainDto.IMMUTABLEX),
    BlockchainGroupDto.FLOW to listOf(BlockchainDto.FLOW),
    BlockchainGroupDto.TEZOS to listOf(BlockchainDto.TEZOS),
    BlockchainGroupDto.SOLANA to listOf(BlockchainDto.SOLANA)
)

private val groups = subchains.entries
    .flatMap { group -> group.value.map { it to group.key } }
    .associateBy({ it.first }, { it.second })

fun BlockchainDto.group(): BlockchainGroupDto {
    return groups[this]!!
}

fun BlockchainGroupDto.subchains(): List<BlockchainDto> {
    return subchains[this]!!
}