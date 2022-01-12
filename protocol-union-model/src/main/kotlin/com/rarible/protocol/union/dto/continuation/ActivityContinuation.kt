package com.rarible.protocol.union.dto.continuation

import com.rarible.protocol.union.dto.ActivityDto
import com.rarible.protocol.union.dto.BlockchainDto
import com.rarible.protocol.union.dto.continuation.page.ArgPaging
import com.rarible.protocol.union.dto.continuation.page.ArgSlice
import com.rarible.protocol.union.dto.continuation.page.Slice

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

    fun next(
        blockchains: List<BlockchainDto>,
        activities: List<ActivityDto>,
        continuation: String?
    ): String? {
        val combinedContinuation = CombinedContinuation.parse(continuation)
        val argSlices = blockchains.map { blockchain ->
            val argContinuation = combinedContinuation.continuations[blockchain.name]
            val blockchainActivities = activities.filter { it.id.blockchain == blockchain }

            val sliceContinuation = if (blockchainActivities.isNotEmpty()) {
                ActivityContinuation.ByLastUpdatedAndIdDesc.getContinuation(blockchainActivities.last()).toString()
            } else if (argContinuation == null || argContinuation == ArgSlice.COMPLETED) {
                // completely read or not even started
                null
            } else {
                // no entities, using continuation from prev response
                argContinuation
            }

            val slice = Slice(continuation = sliceContinuation, entities = blockchainActivities)
            ArgSlice(arg = blockchain.name, argContinuation = argContinuation, slice = slice)
        }
        val argPaging = ArgPaging(factory = ByLastUpdatedAndIdDesc, slices = argSlices)

        val trimmedSlice = if (activities.isNotEmpty()) {
            // size of trimmed list
            argPaging.getSlice(activities.size)
        } else {
            Slice.empty()
        }

        // continuation for next page
        return trimmedSlice.continuation
    }
}
