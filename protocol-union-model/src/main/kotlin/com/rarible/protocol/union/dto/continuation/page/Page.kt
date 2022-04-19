package com.rarible.protocol.union.dto.continuation.page

@Deprecated("Will be removed into BRAVO-2055")
data class Page<T>(
    val total: Long,
    val continuation: String?,
    val entities: List<T>
) {
    companion object {
        fun <T> empty() = Page<T>(0, null, emptyList<T>())
    }
}
