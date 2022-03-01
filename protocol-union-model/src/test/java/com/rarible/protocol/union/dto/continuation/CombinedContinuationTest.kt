package com.rarible.protocol.union.dto.continuation

import com.rarible.protocol.union.dto.ActivityDto
import com.rarible.protocol.union.dto.ActivityIdDto
import com.rarible.protocol.union.dto.BlockchainDto
import com.rarible.protocol.union.dto.BlockchainGroupDto
import com.rarible.protocol.union.dto.ContractAddress
import com.rarible.protocol.union.dto.MintActivityDto
import com.rarible.protocol.union.dto.UnionAddress
import com.rarible.protocol.union.dto.continuation.page.ArgSlice
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigInteger
import java.time.Instant

class CombinedContinuationTest {

    @Test
    fun `print - empty`() {
        val continuation = CombinedContinuation(emptyMap())

        assertThat(continuation.toString()).isEqualTo("")
        assertThat(CombinedContinuation.parse("")).isEqualTo(continuation)
        assertThat(CombinedContinuation.parse(null)).isEqualTo(continuation)
    }

    @Test
    fun `several fields`() {
        val continuation = CombinedContinuation(
            mapOf(
                "a" to "1",
                "b" to "2:1",
                "c" to "3_4:5"
            )
        )

        val expected = "a:1;b:2:1;c:3_4:5"
        assertThat(continuation.toString()).isEqualTo(expected)

        val parsed = CombinedContinuation.parse(expected)
        assertThat(parsed).isEqualTo(continuation)
    }

    @Test
    fun `single field`() {
        val continuation = CombinedContinuation(
            mapOf(
                "a" to "1"
            )
        )

        val expected = "a:1"
        assertThat(continuation.toString()).isEqualTo(expected)

        val parsed = CombinedContinuation.parse(expected)
        assertThat(parsed).isEqualTo(continuation)
    }

    @Test
    fun `next page`() {
        val create: (id: String) -> MintActivityDto = { id ->
            MintActivityDto(
                id = ActivityIdDto(BlockchainDto.ETHEREUM, id),
                date = Instant.now(),
                owner = UnionAddress(BlockchainGroupDto.ETHEREUM, "test"),
                contract = ContractAddress(BlockchainDto.ETHEREUM, "test"),
                tokenId = BigInteger.ONE,
                value = BigInteger.ONE,
                transactionHash = "test",
                reverted = false
            )
        }
        val dto = create("1")
        val blockchains: List<BlockchainDto> = listOf(BlockchainDto.ETHEREUM, BlockchainDto.FLOW, BlockchainDto.TEZOS)
        val activities: List<ActivityDto> = listOf(dto)
        val prevContinuation = CombinedContinuation(mapOf(
            BlockchainDto.FLOW.name to ArgSlice.COMPLETED,
            BlockchainDto.TEZOS.name to "tezos"
        )).toString()

        val nextContinuation = CombinedContinuation.next(blockchains, activities, prevContinuation)
        assertThat(CombinedContinuation.parse(nextContinuation).continuations).isEqualTo(mapOf(
            BlockchainDto.ETHEREUM.name to "${dto.date.toEpochMilli()}_${dto.id.value}",
            BlockchainDto.FLOW.name to ArgSlice.COMPLETED,
            BlockchainDto.TEZOS.name to "tezos",
        ))
    }
}
