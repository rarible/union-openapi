package com.rarible.protocol.union.dto

data class AssetTypeExtension(
    val isCurrency: Boolean,
    val isNft: Boolean,
    val itemId: ItemIdDto? = null,
    val collectionId: CollectionIdDto? = null,
    private val currencyId: String? = null
) {

    val isCollection = collectionId != null

    fun currencyAddress(): String {
        if (!isCurrency) {
            throw IllegalArgumentException("Not a currency AssetType: $this")
        }
        return currencyId!!
    }
}
