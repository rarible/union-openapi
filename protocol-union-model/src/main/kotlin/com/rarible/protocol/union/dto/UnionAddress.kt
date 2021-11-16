package com.rarible.protocol.union.dto

data class UnionAddress(
    val blockchainGroup: BlockchainGroupDto,
    val value: String
) {

    fun fullId(): String {
        return "${blockchainGroup.name}:${value}"
    }

}
