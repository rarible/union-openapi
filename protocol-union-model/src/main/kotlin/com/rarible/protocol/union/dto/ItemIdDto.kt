package com.rarible.protocol.union.dto

import java.math.BigInteger

data class ItemIdDto(
    override val blockchain: BlockchainDto,
    val contract: String,
    val tokenId: BigInteger
) : UnionBlockchainId {

    override val value = "${contract}:${tokenId}"

}