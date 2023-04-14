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

    override fun toString(): String = "$blockchain:$value"

    object Comparators {

        val LEXICOGRAPHICAL = Comparator<ItemIdDto> { i1, i2 ->
            val byChain = BlockchainComparators.LEXICOGRAPHICAL.compare(i1.blockchain, i2.blockchain)
            if (byChain != 0) {
                byChain
            } else {
                i1.value.compareTo(i2.value)
            }
        }
    }
}
