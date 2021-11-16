package com.rarible.protocol.union.dto

data class AuctionIdDto(
    override val blockchain: BlockchainDto,
    override val value: String
) : UnionBlockchainId
