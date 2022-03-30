package com.rarible.protocol.union.dto

import java.math.BigInteger

val AssetTypeDto.ext: AssetTypeExtension
    get() = when (this) {
        //---- ETHEREUM - currencies
        is EthEthereumAssetTypeDto -> AssetTypeExtension(
            isNft = false,
            isCurrency = true,
            currencyId = "0x0000000000000000000000000000000000000000"
        )
        is EthErc20AssetTypeDto -> AssetTypeExtension(
            isNft = false,
            isCurrency = true,
            currencyId = this.contract.value
        )
        //---- ETHEREUM - NFTs
        is EthCryptoPunksAssetTypeDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            itemId = toItemId(this.contract, this.tokenId.toBigInteger())
        )
        is EthErc1155AssetTypeDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            itemId = toItemId(this.contract, this.tokenId)
        )
        is EthErc1155LazyAssetTypeDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            itemId = toItemId(this.contract, this.tokenId)
        )
        is EthErc721AssetTypeDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            itemId = toItemId(this.contract, this.tokenId)
        )
        is EthErc721LazyAssetTypeDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            itemId = toItemId(this.contract, this.tokenId)
        )
        //---- ETHEREUM - other
        is EthGenerativeArtAssetTypeDto -> AssetTypeExtension(
            isNft = false,
            isCurrency = false
        )
        is EthCollectionAssetTypeDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            collectionId = toCollectionId(contract, contract.value)
        )

        //---- FLOW
        is FlowAssetTypeFtDto -> AssetTypeExtension(
            isNft = false,
            isCurrency = true,
            currencyId = this.contract.value
        )
        is FlowAssetTypeNftDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            itemId = toItemId(this.contract, this.tokenId)
        )

        //---- TEZOS - currencies
        is TezosXTZAssetTypeDto -> AssetTypeExtension(
            isNft = false,
            isCurrency = true,
            currencyId = "XTZ"
        )
        // TEZOS-specific currency with tokenId
        is TezosFTAssetTypeDto -> AssetTypeExtension(
            isNft = false,
            isCurrency = true,
            currencyId = tokenId?.let { "${this.contract.value}:${it}" } ?: this.contract.value
        )
        //---- TEZOS - NFTs
        is TezosNFTAssetTypeDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            itemId = toItemId(this.contract, this.tokenId)
        )
        is TezosMTAssetTypeDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            itemId = toItemId(this.contract, this.tokenId)
        )
        is SolanaNftAssetTypeDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            itemId = itemId
        )
        is SolanaFtAssetTypeDto -> AssetTypeExtension(
            isNft = false,
            isCurrency = true,
            currencyId = address.value
        )
        is SolanaSolAssetTypeDto -> AssetTypeExtension(
            isNft = false,
            isCurrency = true,
            currencyId = "So11111111111111111111111111111111111111112"
        )
    }

private fun toItemId(contract: ContractAddress, tokenId: BigInteger): ItemIdDto {
    return ItemIdDto(
        blockchain = contract.blockchain,
        value = "${contract.value}:$tokenId"
    )
}

private fun toCollectionId(contract: ContractAddress, collectionId: String): CollectionIdDto {
    return CollectionIdDto(
        blockchain = contract.blockchain,
        value = collectionId
    )
}