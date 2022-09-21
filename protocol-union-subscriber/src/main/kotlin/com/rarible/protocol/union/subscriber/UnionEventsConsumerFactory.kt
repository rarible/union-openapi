package com.rarible.protocol.union.subscriber

import com.rarible.core.kafka.RaribleKafkaConsumer
import com.rarible.protocol.union.dto.ActivityDto
import com.rarible.protocol.union.dto.CollectionEventDto
import com.rarible.protocol.union.dto.ItemEventDto
import com.rarible.protocol.union.dto.OrderEventDto
import com.rarible.protocol.union.dto.OwnershipEventDto
import com.rarible.protocol.union.dto.UnionEventTopicProvider
import java.util.*

class UnionEventsConsumerFactory(
    private val brokerReplicaSet: String,
    host: String,
    private val environment: String
) {

    private val clientIdPrefix = "$environment.$host.${UUID.randomUUID()}"

    fun createItemConsumer(consumerGroup: String): RaribleKafkaConsumer<ItemEventDto> {
        return RaribleKafkaConsumer(
            clientId = "$clientIdPrefix.union-item-consumer",
            valueDeserializerClass = UnionKafkaJsonDeserializer::class.java,
            valueClass = ItemEventDto::class.java,
            consumerGroup = consumerGroup,
            defaultTopic = UnionEventTopicProvider.getItemTopic(environment),
            bootstrapServers = brokerReplicaSet,
            properties = mapOf(
                "session.timeout.ms" to "60000",
                "heartbeat.interval.ms" to "3000"
            )
        )
    }

    fun createOwnershipConsumer(consumerGroup: String): RaribleKafkaConsumer<OwnershipEventDto> {
        return RaribleKafkaConsumer(
            clientId = "$clientIdPrefix.union-ownership-consumer",
            valueDeserializerClass = UnionKafkaJsonDeserializer::class.java,
            valueClass = OwnershipEventDto::class.java,
            consumerGroup = consumerGroup,
            defaultTopic = UnionEventTopicProvider.getOwnershipTopic(environment),
            bootstrapServers = brokerReplicaSet,
            properties = mapOf(
                "session.timeout.ms" to "60000",
                "heartbeat.interval.ms" to "3000"
            )
        )
    }

    fun createOrderConsumer(consumerGroup: String): RaribleKafkaConsumer<OrderEventDto> {
        return RaribleKafkaConsumer(
            clientId = "$clientIdPrefix.union-order-consumer",
            valueDeserializerClass = UnionKafkaJsonDeserializer::class.java,
            valueClass = OrderEventDto::class.java,
            consumerGroup = consumerGroup,
            defaultTopic = UnionEventTopicProvider.getOrderTopic(environment),
            bootstrapServers = brokerReplicaSet,
            properties = mapOf(
                "session.timeout.ms" to "60000",
                "heartbeat.interval.ms" to "3000"
            )
        )
    }

    fun createCollectionConsumer(consumerGroup: String): RaribleKafkaConsumer<CollectionEventDto> {
        return RaribleKafkaConsumer(
            clientId = "$clientIdPrefix.union-collection-consumer",
            valueDeserializerClass = UnionKafkaJsonDeserializer::class.java,
            valueClass = CollectionEventDto::class.java,
            consumerGroup = consumerGroup,
            defaultTopic = UnionEventTopicProvider.getCollectionTopic(environment),
            bootstrapServers = brokerReplicaSet,
            properties = mapOf(
                "session.timeout.ms" to "60000",
                "heartbeat.interval.ms" to "3000"
            )
        )
    }

    fun createActivityConsumer(consumerGroup: String): RaribleKafkaConsumer<ActivityDto> {
        return RaribleKafkaConsumer(
            clientId = "$clientIdPrefix.union-activity-consumer",
            valueDeserializerClass = UnionKafkaJsonDeserializer::class.java,
            valueClass = ActivityDto::class.java,
            consumerGroup = consumerGroup,
            defaultTopic = UnionEventTopicProvider.getActivityTopic(environment),
            bootstrapServers = brokerReplicaSet,
            properties = mapOf(
                "session.timeout.ms" to "60000",
                "heartbeat.interval.ms" to "3000"
            )
        )
    }
}
