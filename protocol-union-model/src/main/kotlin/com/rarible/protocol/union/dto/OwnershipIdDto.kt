package com.rarible.protocol.union.dto

import java.math.BigInteger

data class OwnershipIdDto(
    override val blockchain: BlockchainDto,
    val itemIdValue: String,
    val owner: UnionAddress
) : UnionBlockchainId {

    override val value = "${itemIdValue}:${owner.value}"

    constructor(
        blockchain: BlockchainDto,
        contract: String,
        tokenId: BigInteger,
        owner: String
    ) : this(
        blockchain = blockchain,
        contract = contract,
        tokenId = tokenId,
        owner = UnionAddress(blockchain.group(), owner)
    )

    constructor(
        blockchain: BlockchainDto,
        contract: String,
        tokenId: BigInteger,
        owner: UnionAddress
    ) : this(
        blockchain = blockchain,
        itemIdValue = "$contract:$tokenId",
        owner = owner
    )

    fun getItemId(): ItemIdDto {
        return ItemIdDto(blockchain, itemIdValue)
    }

}