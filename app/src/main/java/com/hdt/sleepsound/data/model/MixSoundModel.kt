package com.hdt.sleepsound.data.model

sealed class MixSoundModel {
    data class TextItem(val text: Int) : MixSoundModel()
    data class CategoryItem(val categoryId: Int, val items: List<SoundModel>) : MixSoundModel()

    companion object {
        val listMixSound = listOf(
            MixSoundModel.TextItem(
                CategoryModel.NOISE.nameCategory
            ),
            MixSoundModel.CategoryItem(0,
                SoundModel.listSound.filter { it.categoryModel == CategoryModel.NOISE }
            ),
            MixSoundModel.TextItem(
                CategoryModel.ENVIRONMENT.nameCategory
            ),
            MixSoundModel.CategoryItem(1,
                SoundModel.listSound.filter { it.categoryModel == CategoryModel.ENVIRONMENT }
            ),
            MixSoundModel.TextItem(
                CategoryModel.ANIMAL.nameCategory
            ),
            MixSoundModel.CategoryItem(2,
                SoundModel.listSound.filter { it.categoryModel == CategoryModel.ANIMAL }
            ),
            MixSoundModel.TextItem(
                CategoryModel.ASRM.nameCategory
            ),
            MixSoundModel.CategoryItem(3,
                SoundModel.listSound.filter { it.categoryModel == CategoryModel.ASRM }
            ),
            MixSoundModel.TextItem(
                CategoryModel.MECHANICAL.nameCategory
            ),
            MixSoundModel.CategoryItem(4,
                SoundModel.listSound.filter { it.categoryModel == CategoryModel.MECHANICAL }
            ),
            MixSoundModel.TextItem(
                CategoryModel.MUSICAL.nameCategory
            ),
            MixSoundModel.CategoryItem(5,
                SoundModel.listSound.filter { it.categoryModel == CategoryModel.MUSICAL }
            ),
            MixSoundModel.TextItem(
                CategoryModel.NATURAL.nameCategory
            ),
            MixSoundModel.CategoryItem(6,
                SoundModel.listSound.filter { it.categoryModel == CategoryModel.NATURAL }
            ),
            MixSoundModel.TextItem(
                CategoryModel.SEASONAL.nameCategory
            ),
            MixSoundModel.CategoryItem(7,
                SoundModel.listSound.filter { it.categoryModel == CategoryModel.SEASONAL }
            ),
            MixSoundModel.TextItem(
                CategoryModel.SPACE.nameCategory
            ),
            MixSoundModel.CategoryItem(8,
                SoundModel.listSound.filter { it.categoryModel == CategoryModel.SPACE }
            ),
            MixSoundModel.TextItem(
                CategoryModel.SPIRITUAL.nameCategory
            ),
            MixSoundModel.CategoryItem(9,
                SoundModel.listSound.filter { it.categoryModel == CategoryModel.SPIRITUAL }
            )
        )
    }
}