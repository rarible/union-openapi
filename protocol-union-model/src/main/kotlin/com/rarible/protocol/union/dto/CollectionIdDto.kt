package com.rarible.protocol.union.dto

data class CollectionIdDto(
    override val blockchain: BlockchainDto,
    override val value: String
) : UnionBlockchainId {

    override fun toString(): String = "$blockchain:$value"

    object Comparators {

        val LEXICOGRAPHICAL = Comparator<CollectionIdDto> { c1, c2 ->
            val byChain = BlockchainComparators.LEXICOGRAPHICAL.compare(c1.blockchain, c2.blockchain)
            if (byChain != 0) {
                byChain
            } else {
                c1.value.compareTo(c2.value)
            }
        }
    }
}
