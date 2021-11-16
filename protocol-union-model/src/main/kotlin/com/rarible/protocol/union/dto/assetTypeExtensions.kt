package com.rarible.protocol.union.dto

val AssetTypeDto.ext: AssetTypeExtension
    get() = when (this) {
        //---- ETHEREUM - currencies
        is EthEthereumAssetTypeDto -> AssetTypeExtension(
            isNft = false,
            isCurrency = true,
            isCollection = false,
            contract = "0x0000000000000000000000000000000000000000",
            tokenId = null
        )
        is EthErc20AssetTypeDto -> AssetTypeExtension(
            isNft = false,
            isCurrency = true,
            isCollection = false,
            contract = this.contract.value,
            tokenId = null
        )
        //---- ETHEREUM - NFTs
        is EthCryptoPunksAssetTypeDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            isCollection = false,
            contract = this.contract.value,
            tokenId = this.tokenId.toBigInteger()
        )
        is EthErc1155AssetTypeDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            isCollection = false,
            contract = this.contract.value,
            tokenId = this.tokenId
        )
        is EthErc1155LazyAssetTypeDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            isCollection = false,
            contract = this.contract.value,
            tokenId = this.tokenId
        )
        is EthErc721AssetTypeDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            isCollection = false,
            contract = this.contract.value,
            tokenId = this.tokenId
        )
        is EthErc721LazyAssetTypeDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            isCollection = false,
            contract = this.contract.value,
            tokenId = this.tokenId
        )
        //---- ETHEREUM - other
        is EthGenerativeArtAssetTypeDto -> AssetTypeExtension(
            isNft = false,
            isCurrency = false,
            isCollection = false,
            contract = this.contract.value,
            tokenId = null
        )
        is EthCollectionAssetTypeDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            isCollection = true,
            contract = this.contract.value,
            tokenId = null
        )

        //---- FLOW
        is FlowAssetTypeFtDto -> AssetTypeExtension(
            isNft = false,
            isCurrency = true,
            isCollection = false,
            contract = this.contract.value,
            tokenId = null
        )
        is FlowAssetTypeNftDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            isCollection = false,
            contract = this.contract.value,
            tokenId = this.tokenId
        )

        //---- TEZOS - currencies
        is TezosXTZAssetTypeDto -> AssetTypeExtension(
            isNft = false,
            isCurrency = true,
            isCollection = false,
            contract = "XTZ",
            tokenId = null
        )
        is TezosFA12AssetTypeDto -> AssetTypeExtension(
            isNft = false,
            isCurrency = true,
            isCollection = false,
            contract = this.contract.value,
            tokenId = null
        )
        //---- TEZOS - NFTs
        is TezosFA2AssetTypeDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            isCollection = false,
            contract = this.contract.value,
            tokenId = this.tokenId
        )
    }