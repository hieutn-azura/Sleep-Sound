package com.hdt.sleepsound.data.local.mapper

import com.hdt.sleepsound.data.local.entity.RecordEntity
import com.hdt.sleepsound.data.model.RecordModel

object RecordMapper {

    fun mapToEntity(recordModel: RecordModel): RecordEntity {
        return RecordEntity(
            id = recordModel.id,
            name = recordModel.name,
            uri = recordModel.uri
        )
    }

    fun mapToModel(recordEntity: RecordEntity): RecordModel {
        return RecordModel(
            id = recordEntity.id,
            name = recordEntity.name,
            uri = recordEntity.uri
        )
    }
}