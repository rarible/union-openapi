package com.rarible.protocol.union.api.client

import java.net.URI

class K8sUnionApiServiceUriProvider(
    private val env: String
) : UnionApiServiceUriProvider {

    override fun getUri(): URI {
        return URI.create("http://protocol-union-api.${env}-protocol:8080")
    }
}