package com.rarible.protocol.union.dto.continuation

import com.rarible.protocol.union.dto.OwnershipDto

object OwnershipContinuation {

    object ByLastUpdatedAndId : ContinuationFactory<OwnershipDto, DateIdContinuation> {
        override fun getContinuation(entity: OwnershipDto): DateIdContinuation {
            return DateIdContinuation(entity.createdAt, entity.id.value)
        }
    }
}