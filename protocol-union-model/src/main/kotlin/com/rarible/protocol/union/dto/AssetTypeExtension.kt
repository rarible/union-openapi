package com.rarible.protocol.union.dto

import java.math.BigInteger

class AssetTypeExtension(
    val isCurrency: Boolean,
    val isNft: Boolean,
    val isCollection: Boolean,
    val contract: String,
    val tokenId: BigInteger?
) {
    fun itemId(blockchain: BlockchainDto): ItemIdDto? {
        if (!isNft || tokenId == null) {
            return null
        }
        return ItemIdDto(
            blockchain = blockchain,
            contract = contract,
            tokenId = tokenId
        )
    }
}
