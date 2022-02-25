package com.rarible.protocol.union.api.client

import java.net.URI

class K8sUnionApiServiceUriProvider(
    private val namespace: String?
) : UnionApiServiceUriProvider {

    companion object {

        const val INTERNAL_URL = "http://protocol-union-api"
        const val PORT = 8080
    }

    override fun getUri(): URI {
        val url = namespace?.let {
            "$INTERNAL_URL.$namespace:$PORT"
        } ?: "$INTERNAL_URL:$PORT"

        return URI.create(url)
    }
}