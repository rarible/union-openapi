package com.rarible.protocol.union.dto.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.rarible.protocol.union.dto.CurrencyIdDto
import com.rarible.protocol.union.dto.parser.CurrencyIdParser

object CurrencyIdDeserializer : StdDeserializer<CurrencyIdDto>(CurrencyIdDto::class.java) {

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): CurrencyIdDto? {
        val value = p.codec.readValue(p, String::class.java) ?: return null
        return CurrencyIdParser.parse(value)
    }
}
