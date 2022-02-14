package com.rarible.protocol.union.dto.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.rarible.protocol.union.dto.CollectionIdDto
import com.rarible.protocol.union.dto.parser.IdParser

object CollectionIdDeserializer : StdDeserializer<CollectionIdDto>(CollectionIdDto::class.java) {

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): CollectionIdDto? {
        val value = p.codec.readValue(p, String::class.java) ?: return null
        return IdParser.parseCollectionId(value)
    }
}