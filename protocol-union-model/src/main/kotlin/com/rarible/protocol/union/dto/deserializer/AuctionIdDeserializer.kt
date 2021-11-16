package com.rarible.protocol.union.dto.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.rarible.protocol.union.dto.AuctionIdDto
import com.rarible.protocol.union.dto.parser.IdParser

object AuctionIdDeserializer : StdDeserializer<AuctionIdDto>(AuctionIdDto::class.java) {

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): AuctionIdDto? {
        val value = p.codec.readValue(p, String::class.java) ?: return null
        return IdParser.parseAuctionId(value)
    }
}
