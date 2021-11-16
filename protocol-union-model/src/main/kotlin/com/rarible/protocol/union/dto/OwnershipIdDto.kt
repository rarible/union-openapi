package com.rarible.protocol.union.dto

import java.math.BigInteger

data class OwnershipIdDto(
    override val blockchain: BlockchainDto,
    val contract: String,
    val tokenId: BigInteger,
    val owner: UnionAddress
) : UnionBlockchainId {

    override val value = "${contract}:${tokenId}:${owner.value}"

}