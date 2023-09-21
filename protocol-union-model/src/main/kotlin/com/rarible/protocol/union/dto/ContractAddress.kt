package com.rarible.protocol.union.dto

data class ContractAddress(
    override val blockchain: BlockchainDto,
    override val value: String
) : UnionBlockchainId {

    override fun toString(): String {
        return fullId()
    }
}