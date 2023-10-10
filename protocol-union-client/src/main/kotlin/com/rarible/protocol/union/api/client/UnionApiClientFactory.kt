package com.rarible.protocol.union.api.client

import com.rarible.protocol.union.api.ApiClient
import com.rarible.protocol.union.dto.UnionModelJacksonModule
import com.rarible.protocol.union.dto.UnionPrimitivesJacksonModule
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer

open class UnionApiClientFactory(
    private val uriProvider: UnionApiServiceUriProvider,
    private val webClientCustomizer: WebClientCustomizer = NoopWebClientCustomizer()
) {

    fun createCollectionApiClient(): CollectionControllerApi {
        return CollectionControllerApi(createApiClient())
    }

    fun createItemApiClient(): ItemControllerApi {
        return ItemControllerApi(createApiClient())
    }

    fun createOwnershipApiClient(): OwnershipControllerApi {
        return OwnershipControllerApi(createApiClient())
    }

    fun createOrderApiClient(): OrderControllerApi {
        return OrderControllerApi(createApiClient())
    }

    fun createAuctionApiClient(): AuctionControllerApi {
        return AuctionControllerApi(createApiClient())
    }

    fun createSignatureApiClient(): SignatureControllerApi {
        return SignatureControllerApi(createApiClient())
    }

    fun createDomainApiClient(): DomainControllerApi {
        return DomainControllerApi(createApiClient())
    }

    fun createActivityApiClient(): ActivityControllerApi {
        return ActivityControllerApi(createApiClient())
    }

    fun createCurrencyApiClient(): CurrencyControllerApi {
        return CurrencyControllerApi(createApiClient())
    }

    fun createAdminApiClient(): AdminControllerApi {
        return AdminControllerApi(createApiClient())
    }

    fun createBalanceApiClient(): BalanceControllerApi {
        return BalanceControllerApi(createApiClient())
    }

    private fun createApiClient(): ApiClient {
        val jacksonMapper = ApiClient.createDefaultObjectMapper()
            .registerModule(UnionPrimitivesJacksonModule)
            .registerModule(UnionModelJacksonModule)

        return ApiClient(webClientCustomizer, jacksonMapper)
            .setBasePath(uriProvider.getUri().toASCIIString())
    }

}

