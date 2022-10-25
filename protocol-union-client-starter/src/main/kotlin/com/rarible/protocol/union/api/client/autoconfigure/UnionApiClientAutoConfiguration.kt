package com.rarible.protocol.union.api.client.autoconfigure

import com.rarible.core.application.ApplicationEnvironmentInfo
import com.rarible.protocol.union.api.client.CompositeWebClientCustomizer
import com.rarible.protocol.union.api.client.DefaultUnionWebClientCustomizer
import com.rarible.protocol.union.api.client.K8sUnionApiServiceUriProvider
import com.rarible.protocol.union.api.client.NoopWebClientCustomizer
import com.rarible.protocol.union.api.client.SwarmUnionApiServiceUriProvider
import com.rarible.protocol.union.api.client.UnionApiClientFactory
import com.rarible.protocol.union.api.client.UnionApiServiceUriProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer
import org.springframework.context.annotation.Bean

const val UNION_WEB_CLIENT_CUSTOMIZER = "UNION_WEB_CLIENT_CUSTOMIZER"

class UnionApiClientAutoConfiguration(
    private val applicationEnvironmentInfo: ApplicationEnvironmentInfo
) {

    @Autowired(required = false)
    @Qualifier(UNION_WEB_CLIENT_CUSTOMIZER)
    private var webClientCustomizer: WebClientCustomizer = NoopWebClientCustomizer()

    @Bean
    @ConditionalOnMissingBean(UnionApiServiceUriProvider::class)
    fun unionApiServiceUriProvider(
        @Value("\${rarible.core.client.k8s:true}") k8s: Boolean
    ): UnionApiServiceUriProvider {
        return if (k8s)
            K8sUnionApiServiceUriProvider()
        else
            SwarmUnionApiServiceUriProvider(applicationEnvironmentInfo.name)
    }

    @Bean
    @ConditionalOnMissingBean(UnionApiClientFactory::class)
    fun unionApiClientFactory(
        @Value("\${rarible.core.client.name:}") clientName: String,
        unionApiServiceUriProvider: UnionApiServiceUriProvider
    ): UnionApiClientFactory {
        val customizers = listOf(DefaultUnionWebClientCustomizer(clientName), webClientCustomizer)
        val customizer = CompositeWebClientCustomizer(customizers)
        return UnionApiClientFactory(unionApiServiceUriProvider, customizer)
    }
}