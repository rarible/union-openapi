package com.rarible.protocol.union.dto

object BlockchainComparators {

    val LEXICOGRAPHICAL = Comparator<BlockchainDto> { o1, o2 ->
        o1.name.compareTo(o2.name)
    }

}