package com.rarible.protocol.union.dto.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.rarible.protocol.union.dto.ContractAddress
import com.rarible.protocol.union.dto.parser.IdParser

object ContractAddressDeserializer : StdDeserializer<ContractAddress>(ContractAddress::class.java) {

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): ContractAddress? {
        val value = p.codec.readValue(p, String::class.java) ?: return null
        return IdParser.parseContract(value)
    }
}