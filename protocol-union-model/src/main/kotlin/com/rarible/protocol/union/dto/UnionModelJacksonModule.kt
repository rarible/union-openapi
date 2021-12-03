package com.rarible.protocol.union.dto

import com.fasterxml.jackson.databind.module.SimpleModule
import com.rarible.protocol.union.dto.deserializer.*
import com.rarible.protocol.union.dto.serializer.ActivityIdSerializer
import com.rarible.protocol.union.dto.serializer.AuctionIdSerializer
import com.rarible.protocol.union.dto.serializer.ContractAddressSerializer
import com.rarible.protocol.union.dto.serializer.ItemIdSerializer
import com.rarible.protocol.union.dto.serializer.OrderIdSerializer
import com.rarible.protocol.union.dto.serializer.OwnershipIdSerializer

object UnionModelJacksonModule : SimpleModule() {

    init {
        addSerializer(ItemIdSerializer)
        addDeserializer(ItemIdDto::class.java, ItemIdDeserializer)

        addSerializer(ContractAddressSerializer)
        addDeserializer(ContractAddress::class.java, ContractAddressDeserializer)

        addSerializer(OwnershipIdSerializer)
        addDeserializer(OwnershipIdDto::class.java, OwnershipIdDeserializer)

        addSerializer(OrderIdSerializer)
        addDeserializer(OrderIdDto::class.java, OrderIdDeserializer)

        addSerializer(AuctionIdSerializer)
        addDeserializer(AuctionIdDto::class.java, AuctionIdDeserializer)

        addSerializer(ActivityIdSerializer)
        addDeserializer(ActivityIdDto::class.java, ActivityIdDeserializer)

        addDeserializer(CurrencyIdDto::class.java, CurrencyIdDeserializer)
    }

}
