package com.rarible.protocol.union.dto

import java.math.BigInteger

data class ItemIdDto(
    override val blockchain: BlockchainDto,
    override val value: String
) : UnionBlockchainId {

    constructor(
        blockchain: BlockchainDto,
        contract: String,
        tokenId: BigInteger
    ) : this(blockchain = blockchain, value = "$contract:$tokenId")

    fun toOwnership(owner: String): OwnershipIdDto {
        return OwnershipIdDto(
            blockchain = blockchain,
            itemIdValue = value,
            owner = UnionAddress(blockchain.group(), owner)
        )
    }

}