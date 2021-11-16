package com.rarible.protocol.union.dto.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.rarible.protocol.union.dto.AuctionIdDto

object AuctionIdSerializer : StdSerializer<AuctionIdDto>(AuctionIdDto::class.java) {

    override fun serialize(value: AuctionIdDto?, gen: JsonGenerator, provider: SerializerProvider?) {
        if (value == null) {
            gen.writeNull()
            return
        }
        gen.writeString(value.fullId())
    }
}
