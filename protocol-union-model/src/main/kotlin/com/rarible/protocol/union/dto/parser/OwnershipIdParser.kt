package com.rarible.protocol.union.dto.parser

import com.rarible.protocol.union.dto.BlockchainDto
import com.rarible.protocol.union.dto.BlockchainIdFormatException
import com.rarible.protocol.union.dto.OwnershipIdDto
import com.rarible.protocol.union.dto.UnionAddress
import com.rarible.protocol.union.dto.group
import com.rarible.protocol.union.dto.normalizeAddress
import com.rarible.protocol.union.dto.normalizeId

object OwnershipIdParser {

    /**
     * For full qualifiers like "ETHEREUM:abc:123:xyz"
     * Since Solana introduced, ownership identifiers can be like "SOLANA:a7cb462:xyz"
     */
    fun parseFull(value: String): OwnershipIdDto {
        val (blockchain, id) = IdParser.extractBlockchain(value)
        return parseShort(id, blockchain)
    }

    /**
     * For short qualifiers like "abc:123:xyz", blockchain should be defined manually
     */
    fun parseShort(value: String, blockchain: BlockchainDto): OwnershipIdDto {
        val (itemId, owner) = parseItemAndOwner(value)
        val group = blockchain.group()
        return OwnershipIdDto(
            blockchain = blockchain,
            itemIdValue = blockchain.normalizeId(itemId),
            owner = UnionAddress(group, blockchain.normalizeAddress(owner))
        )
    }

    private fun parseItemAndOwner(ownershipId: String): Pair<String, String> {
        val delim = ownershipId.lastIndexOf(IdParser.DELIMITER)
        if (delim < 0) {
            throw BlockchainIdFormatException(
                "Illegal format for Ownership ID: '$ownershipId', owner part not found"
            )
        }
        val itemId = ownershipId.substring(0, delim)
        val owner = ownershipId.substring(delim + IdParser.DELIMITER.length)
        return Pair(itemId, owner)
    }
}
