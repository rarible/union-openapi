package com.rarible.protocol.union.dto

data class CollectionIdDto(
    override val blockchain: BlockchainDto,
    override val value: String
) : UnionBlockchainId {
    override fun toString(): String = "$blockchain:$value"
}
