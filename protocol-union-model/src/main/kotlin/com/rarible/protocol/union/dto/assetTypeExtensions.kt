package com.rarible.protocol.union.dto

import java.math.BigInteger

val AssetTypeDto.ext: AssetTypeExtension
    get() = when (this) {
        //---- ETHEREUM - currencies
        is EthEthereumAssetTypeDto -> AssetTypeExtension(
            isNft = false,
            isCurrency = true,
            isCollection = false,
            contract = "0x0000000000000000000000000000000000000000",
            tokenId = null,
            itemId = null
        )
        is EthErc20AssetTypeDto -> AssetTypeExtension(
            isNft = false,
            isCurrency = true,
            isCollection = false,
            contract = this.contract.value,
            tokenId = null,
            itemId = null
        )
        //---- ETHEREUM - NFTs
        is EthCryptoPunksAssetTypeDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            isCollection = false,
            contract = this.contract.value,
            tokenId = this.tokenId.toBigInteger(),
            itemId = toItemId(this.contract, this.tokenId.toBigInteger())
        )
        is EthErc1155AssetTypeDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            isCollection = false,
            contract = this.contract.value,
            tokenId = this.tokenId,
            itemId = toItemId(this.contract, this.tokenId)
        )
        is EthErc1155LazyAssetTypeDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            isCollection = false,
            contract = this.contract.value,
            tokenId = this.tokenId,
            itemId = toItemId(this.contract, this.tokenId)
        )
        is EthErc721AssetTypeDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            isCollection = false,
            contract = this.contract.value,
            tokenId = this.tokenId,
            itemId = toItemId(this.contract, this.tokenId)
        )
        is EthErc721LazyAssetTypeDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            isCollection = false,
            contract = this.contract.value,
            tokenId = this.tokenId,
            itemId = toItemId(this.contract, this.tokenId)
        )
        //---- ETHEREUM - other
        is EthGenerativeArtAssetTypeDto -> AssetTypeExtension(
            isNft = false,
            isCurrency = false,
            isCollection = false,
            contract = this.contract.value,
            tokenId = null,
            itemId = null
        )
        is EthCollectionAssetTypeDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            isCollection = true,
            contract = this.contract.value,
            tokenId = null,
            itemId = null
        )

        //---- FLOW
        is FlowAssetTypeFtDto -> AssetTypeExtension(
            isNft = false,
            isCurrency = true,
            isCollection = false,
            contract = this.contract.value,
            tokenId = null,
            itemId = null
        )
        is FlowAssetTypeNftDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            isCollection = false,
            contract = this.contract.value,
            tokenId = this.tokenId,
            itemId = toItemId(this.contract, this.tokenId)
        )

        //---- TEZOS - currencies
        is TezosXTZAssetTypeDto -> AssetTypeExtension(
            isNft = false,
            isCurrency = true,
            isCollection = false,
            contract = "XTZ",
            tokenId = null,
            itemId = null
        )
        is TezosFTAssetTypeDto -> AssetTypeExtension(
            isNft = false,
            isCurrency = true,
            isCollection = false,
            contract = this.contract.value,
            tokenId = this.tokenId,
            itemId = null
        )
        //---- TEZOS - NFTs
        is TezosNFTAssetTypeDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            isCollection = false,
            contract = this.contract.value,
            tokenId = this.tokenId,
            itemId = toItemId(this.contract, this.tokenId)
        )
        is TezosMTAssetTypeDto -> AssetTypeExtension(
            isNft = true,
            isCurrency = false,
            isCollection = false,
            contract = this.contract.value,
            tokenId = this.tokenId,
            itemId = toItemId(this.contract, this.tokenId)
        )
    }

private fun toItemId(contract: ContractAddress, tokenId: BigInteger): ItemIdDto {
    return ItemIdDto(
        blockchain = contract.blockchain,
        contract = contract.value,
        tokenId = tokenId
    )
}