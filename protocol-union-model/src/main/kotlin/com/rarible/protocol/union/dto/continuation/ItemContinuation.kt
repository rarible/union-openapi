package com.rarible.protocol.union.dto.continuation

import com.rarible.protocol.union.dto.ItemDto

object ItemContinuation {

    object ByLastUpdatedAndId : ContinuationFactory<ItemDto, DateIdContinuation> {
        override fun getContinuation(entity: ItemDto): DateIdContinuation {
            return DateIdContinuation(entity.lastUpdatedAt, entity.id.value)
        }
    }
}