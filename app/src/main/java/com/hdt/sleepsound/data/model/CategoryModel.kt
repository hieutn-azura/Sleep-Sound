package com.hdt.sleepsound.data.model

import androidx.annotation.Keep
import com.hdt.sleepsound.R

@Keep
enum class CategoryModel(
    val nameCategory: Int,
    val imageCategory: Int
) {
    NOISE(
        nameCategory = R.string.abstact_noises,
        imageCategory = R.drawable.category_noise
    ),
    ENVIRONMENT(
        nameCategory = R.string.ambient_environment,
        imageCategory = R.drawable.category_ambient
    ),
    ANIMAL(
        nameCategory = R.string.animals,
        imageCategory = R.drawable.category_animal
    ),
    ASRM(
        nameCategory = R.string.asmr,
        imageCategory = R.drawable.category_asmr
    ),
    MECHANICAL(
        nameCategory = R.string.mechanical,
        imageCategory = R.drawable.category_mechanic
    ),
    MUSICAL(
        nameCategory = R.string.musical,
        imageCategory = R.drawable.category_musical
    ),
    NATURAL(
        nameCategory = R.string.nature_sounds,
        imageCategory = R.drawable.category_natural
    ),
    SEASONAL(
        nameCategory = R.string.seasonal_ambience,
        imageCategory = R.drawable.category_seasonal
    ),
    SPACE(
        nameCategory = R.string.space,
        imageCategory = R.drawable.category_space
    ),
    SPIRITUAL(
        nameCategory = R.string.spiritual,
        imageCategory = R.drawable.category_spiritual
    )
}