package com.rarible.protocol.union.api.client

import java.net.URI

class K8sUnionApiServiceUriProvider : UnionApiServiceUriProvider {

    override fun getUri(): URI {
        return URI.create(String.format("http://protocol-union-api:8080"))
    }
}