package com.rarible.protocol.union.dto

data class OrderIdDto(
    override val blockchain: BlockchainDto,
    override val value: String
) : UnionBlockchainId {
    override fun toString(): String = "$blockchain:$value"
}
