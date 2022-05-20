package com.rarible.protocol.union.dto

import com.fasterxml.jackson.databind.module.SimpleModule
import com.rarible.protocol.union.dto.deserializer.ActivityIdDeserializer
import com.rarible.protocol.union.dto.deserializer.AuctionIdDeserializer
import com.rarible.protocol.union.dto.deserializer.CollectionIdDeserializer
import com.rarible.protocol.union.dto.deserializer.ContractAddressDeserializer
import com.rarible.protocol.union.dto.deserializer.CurrencyIdDeserializer
import com.rarible.protocol.union.dto.deserializer.ItemIdDeserializer
import com.rarible.protocol.union.dto.deserializer.OrderIdDeserializer
import com.rarible.protocol.union.dto.deserializer.OwnershipIdDeserializer
import com.rarible.protocol.union.dto.serializer.ActivityIdSerializer
import com.rarible.protocol.union.dto.serializer.AuctionIdSerializer
import com.rarible.protocol.union.dto.serializer.CollectionIdSerializer
import com.rarible.protocol.union.dto.serializer.ContractAddressSerializer
import com.rarible.protocol.union.dto.serializer.CurrencyIdSerializer
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

        addSerializer(CollectionIdSerializer)
        addDeserializer(CollectionIdDto::class.java, CollectionIdDeserializer)

        addSerializer(OrderIdSerializer)
        addDeserializer(OrderIdDto::class.java, OrderIdDeserializer)

        addSerializer(AuctionIdSerializer)
        addDeserializer(AuctionIdDto::class.java, AuctionIdDeserializer)

        addSerializer(ActivityIdSerializer)
        addDeserializer(ActivityIdDto::class.java, ActivityIdDeserializer)

        addSerializer(CurrencyIdSerializer)
        addDeserializer(CurrencyIdDto::class.java, CurrencyIdDeserializer)
    }

}
