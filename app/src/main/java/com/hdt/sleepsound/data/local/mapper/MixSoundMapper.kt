package com.hdt.sleepsound.data.local.mapper

import com.hdt.sleepsound.data.local.entity.MixSoundEntity
import com.hdt.sleepsound.data.model.MixModel

object MixSoundMapper {

    fun mapToEntity(mixModel: MixModel): MixSoundEntity {
        return MixSoundEntity(
            id = mixModel.id,
            name = mixModel.name,
            uri1 = mixModel.uri1,
            uri2 = mixModel.uri2,
            uri3 = mixModel.uri3,
            volume1 = mixModel.volume1,
            volume2 = mixModel.volume2,
            volume3 = mixModel.volume3
        )
    }

    fun mapToModel(mixSoundEntity: MixSoundEntity): MixModel {
        return MixModel(
            id = mixSoundEntity.id,
            name = mixSoundEntity.name,
            uri1 = mixSoundEntity.uri1,
            uri2 = mixSoundEntity.uri2,
            uri3 = mixSoundEntity.uri3,
            volume1 = mixSoundEntity.volume1,
            volume2 = mixSoundEntity.volume2,
            volume3 = mixSoundEntity.volume3
        )
    }

}