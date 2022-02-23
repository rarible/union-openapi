package com.rarible.protocol.union.dto

import java.math.BigInteger

data class CurrencyIdDto(
    override val blockchain: BlockchainDto,
    val contract: String,
    val tokenId: BigInteger?
) : UnionBlockchainId {

    override val value = if (tokenId != null) "${contract}:${tokenId}" else contract

    override fun toString(): String = "$blockchain:$value"
}
