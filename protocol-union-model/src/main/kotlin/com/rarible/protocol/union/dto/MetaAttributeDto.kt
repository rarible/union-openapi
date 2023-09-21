package com.rarible.protocol.union.dto

data class MetaAttributeDto(
    val key: String,
    val value: String? = null,
    val type: String? = null,
    val format: String? = null
) {
    override fun toString(): String {
        val base = "${this.key}: ${this.value}"
        if (this.format == null && this.type == null) {
            return base
        }
        return base + " (type=${this.type}, format=${this.format})"
    }
}