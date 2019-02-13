package com.android.coinranking.core.functional

interface Mapper<InType, OutType> {
  fun map(param: InType): OutType
  fun mapReverse(param: OutType): InType
  fun mapList(param: List<InType>): List<OutType> = param.map(::map)
  fun mapListReverse(param: List<OutType>): List<InType> = param.map(::mapReverse)
}