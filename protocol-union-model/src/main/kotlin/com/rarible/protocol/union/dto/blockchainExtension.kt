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

fun BlockchainDto.normalizeContract(contract: String): String {
    return group().normalize(contract)
}

fun BlockchainDto.normalizeAddress(address: String): String {
    return group().normalize(address)
}

fun BlockchainDto.normalizeId(id: String): String {
    return group().normalize(id)
}

fun BlockchainGroupDto.normalizeContract(contract: String): String {
    return this.normalize(contract)
}

fun BlockchainGroupDto.normalizeAddress(address: String): String {
    return this.normalize(address)
}

fun BlockchainGroupDto.normalizeId(id: String): String {
    return this.normalize(id)
}

// ATM there is no difference between normalization of contract/address/ids
private fun BlockchainGroupDto.normalize(contract: String): String {
    return when (this) {
        BlockchainGroupDto.ETHEREUM -> contract.lowercase()
        // These groups have case-sensitive identifiers
        BlockchainGroupDto.FLOW,
        BlockchainGroupDto.TEZOS,
        BlockchainGroupDto.SOLANA -> contract
    }
}