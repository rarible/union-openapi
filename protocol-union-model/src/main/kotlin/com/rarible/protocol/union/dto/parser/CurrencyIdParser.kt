package com.rarible.protocol.union.dto.parser

import com.rarible.protocol.union.dto.CurrencyIdDto
import java.math.BigInteger

object CurrencyIdParser {
    /**
     * For full qualifiers like "ETHEREUM:abc[:123]"
     */
    fun parse(value: String): CurrencyIdDto {
        val parts = IdParser.split(value, CURRENCY_ID_RANGE)
        val blockchain = IdParser.parseBlockchain(parts[0])
        return CurrencyIdDto(
            blockchain = blockchain,
            contract = parts[1],
            tokenId = parts.getOrNull(2)?.let { part -> BigInteger(part) }
        )
    }

    private val CURRENCY_ID_RANGE = IntRange(2, 3)
}
