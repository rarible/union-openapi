package com.rarible.protocol.union.dto.continuation

import com.rarible.protocol.union.dto.ActivityDto

object ActivityContinuation {

    object ByLastUpdatedAndIdDesc :
        ContinuationFactory<ActivityDto, DateIdContinuation> {
        override fun getContinuation(entity: ActivityDto): DateIdContinuation {
            return DateIdContinuation(
                entity.date,
                entity.id.value,
                false
            )
        }
    }

    object ByLastUpdatedAndIdAsc :
        ContinuationFactory<ActivityDto, DateIdContinuation> {
        override fun getContinuation(entity: ActivityDto): DateIdContinuation {
            return DateIdContinuation(
                entity.date,
                entity.id.value,
                true
            )
        }
    }

    object ByLastUpdatedSyncAndIdDesc :
        ContinuationFactory<ActivityDto, DateIdContinuation> {
        override fun getContinuation(entity: ActivityDto): DateIdContinuation {
            return DateIdContinuation(
                entity.lastUpdatedAt ?: entity.date,
                entity.id.value,
                false
            )
        }
    }

    object ByLastUpdatedSyncAndIdAsc :
        ContinuationFactory<ActivityDto, DateIdContinuation> {
        override fun getContinuation(entity: ActivityDto): DateIdContinuation {
            return DateIdContinuation(
                entity.lastUpdatedAt ?: entity.date,
                entity.id.value,
                true
            )
        }
    }

    object ByLastUpdatedDesc :
        ContinuationFactory<ActivityDto, DateContinuation> {
        override fun getContinuation(entity: ActivityDto): DateContinuation {
            return DateContinuation(
                entity.date,
                entity.id.value,
                false
            )
        }
    }

    object ByLastUpdatedAsc :
        ContinuationFactory<ActivityDto, DateContinuation> {
        override fun getContinuation(entity: ActivityDto): DateContinuation {
            return DateContinuation(
                entity.date,
                entity.id.value,
                true
            )
        }
    }
}
