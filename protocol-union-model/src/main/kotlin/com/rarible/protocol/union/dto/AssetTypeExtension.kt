package com.rarible.protocol.union.dto

import java.math.BigInteger

data class AssetTypeExtension(
    val isCurrency: Boolean,
    val isNft: Boolean,
    val contract: String,
    val tokenId: BigInteger? = null,
    val itemId: ItemIdDto? = null,
    val collectionId: CollectionIdDto? = null
) {

    val isCollection = collectionId != null

    fun currencyAddress(): String {
        if (!isCurrency) {
            throw IllegalArgumentException("Not a currency AssetType: $this")
        }
        return tokenId?.let { "$contract:$it" } ?: contract
    }
}
