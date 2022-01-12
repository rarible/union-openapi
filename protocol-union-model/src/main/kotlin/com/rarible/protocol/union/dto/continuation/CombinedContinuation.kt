package com.rarible.protocol.union.dto.continuation

import com.rarible.protocol.union.dto.ActivityDto
import com.rarible.protocol.union.dto.BlockchainDto
import com.rarible.protocol.union.dto.continuation.page.ArgPaging
import com.rarible.protocol.union.dto.continuation.page.ArgSlice
import com.rarible.protocol.union.dto.continuation.page.Slice

data class CombinedContinuation(
    val continuations: Map<String, String>
) {

    override fun toString(): String {
        return continuations.entries.joinToString(separator = ";") {
            "${it.key}:${it.value}"
        }
    }

    companion object {
        fun parse(str: String?): CombinedContinuation {
            if (str == null || str.isEmpty()) {
                return CombinedContinuation(emptyMap())
            }
            val continuations = str.split(";")
            val mapped = continuations.mapNotNull {
                Continuation.splitBy(it, ":")
            }.associateBy({ it.first }, { it.second })

            return CombinedContinuation(mapped)
        }

        fun next(
            blockchains: List<BlockchainDto>,
            activities: List<ActivityDto>,
            continuation: String?
        ): String? {
            val combinedContinuation = parse(continuation)
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
            val argPaging = ArgPaging(factory = ActivityContinuation.ByLastUpdatedAndIdDesc, slices = argSlices)

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
}
