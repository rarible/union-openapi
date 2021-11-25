package com.rarible.protocol.union.dto

import java.math.BigInteger

data class AssetTypeExtension(
    val isCurrency: Boolean,
    val isNft: Boolean,
    val isCollection: Boolean,
    val contract: String,
    val tokenId: BigInteger?,
    val itemId: ItemIdDto?
) {
    fun currencyAddress(): String {
        if (!isCurrency) {
            throw IllegalArgumentException("Not a currency AssetType: $this")
        }
        return tokenId?.let { "$contract:$it" } ?: contract
    }
}
