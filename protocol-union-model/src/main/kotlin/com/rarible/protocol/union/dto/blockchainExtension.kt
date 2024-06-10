package com.rarible.protocol.union.dto

private val subchains = mapOf(
    // First Blockchain specified in the list considered as PRIMARY -
    // it means, there should not be situation like "Polygon is enabled, Ethereum is disabled"
    // We using first blockchain of the group to validate user's signature
    BlockchainGroupDto.ETHEREUM to listOf(
        BlockchainDto.ETHEREUM,
        BlockchainDto.POLYGON,
        BlockchainDto.IMMUTABLEX,
        BlockchainDto.MANTLE,
        BlockchainDto.ARBITRUM,
        BlockchainDto.CHILIZ,
        BlockchainDto.ZKSYNC,
        BlockchainDto.ASTARZKEVM,
        BlockchainDto.BASE,
        BlockchainDto.LIGHTLINK,
        BlockchainDto.RARI,
        BlockchainDto.CELO,
        BlockchainDto.FIEF,
        BlockchainDto.XAI,
        BlockchainDto.KROMA,
        BlockchainDto.ZKLINK,
        BlockchainDto.OASYS,
        BlockchainDto.QUAI,
        BlockchainDto.SAAKURU,
        BlockchainDto.OASIS,
        BlockchainDto.PALM,
        BlockchainDto.MATCH,
        BlockchainDto.FIVIRE,
        BlockchainDto.SEI,
        BlockchainDto.CAMP,
        BlockchainDto.LISK,
        BlockchainDto.ETHERLINK,
        BlockchainDto.MOONBEAM,
    ),
    BlockchainGroupDto.FLOW to listOf(BlockchainDto.FLOW),
    BlockchainGroupDto.TEZOS to listOf(BlockchainDto.TEZOS),
    BlockchainGroupDto.SOLANA to listOf(
        BlockchainDto.SOLANA,
        BlockchainDto.ECLIPSE,
    ),
    BlockchainGroupDto.APTOS to listOf(BlockchainDto.APTOS),
)

private val groups = subchains.entries
    .flatMap { group -> group.value.map { it to group.key } }
    .associateBy({ it.first }, { it.second })

private val nativeCurrencies = mapOf(
    BlockchainGroupDto.ETHEREUM to "0x0000000000000000000000000000000000000000",
    //BlockchainGroupDto.FLOW to "" // there is no 'native' currency for flow, it is bind to contract like Erc20
    BlockchainGroupDto.SOLANA to "So11111111111111111111111111111111111111112",
    BlockchainGroupDto.TEZOS to "XTZ",
    // Base64 (Basic) encoding of UT8-encoding of the string "0x1::aptos_coin::AptosCoin"
    BlockchainGroupDto.APTOS to "MHgxOjphcHRvc19jb2luOjpBcHRvc0NvaW4=",
)

fun BlockchainGroupDto.nativeCurrency(): String {
    return nativeCurrencies[this]
        ?: throw IllegalArgumentException("There is no native currency for $this")
}

fun BlockchainDto.nativeCurrency(): String {
    return this.group().nativeCurrency()
}

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
        BlockchainGroupDto.APTOS,
        BlockchainGroupDto.FLOW,
        BlockchainGroupDto.TEZOS,
        BlockchainGroupDto.SOLANA -> contract
    }
}