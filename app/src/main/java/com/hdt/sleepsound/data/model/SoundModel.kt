package com.hdt.sleepsound.data.model

import android.os.Parcelable
import com.hdt.sleepsound.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class SoundModel(
    val nameSound: Int,
    val imageSound: Int,
    val sound: Int,
    val categoryModel: CategoryModel,
    var isSelected: Boolean = false
): Parcelable {

    companion object {
        val listSound = listOf(
            SoundModel(
                nameSound = R.string.brown_noise,
                imageSound = R.drawable.brown_noise,
                sound = R.raw.brown_noise,
                categoryModel = CategoryModel.NOISE
            ),
            SoundModel(
                nameSound = R.string.brown_noise_350hz,
                imageSound = R.drawable.brown_noise_350hz,
                sound = R.raw.brown_noise_350hz,
                categoryModel = CategoryModel.NOISE
            ),
            SoundModel(
                nameSound = R.string.grey_noise,
                imageSound = R.drawable.grey_noise,
                sound = R.raw.grey_noise,
                categoryModel = CategoryModel.NOISE
            ),
            SoundModel(
                nameSound = R.string.pink_noise,
                imageSound = R.drawable.pink_noise,
                sound = R.raw.pink_noise,
                categoryModel = CategoryModel.NOISE
            ),
            SoundModel(
                nameSound = R.string.soft_grey_noise,
                imageSound = R.drawable.soft_grey_noise,
                sound = R.raw.soft_grey_noise,
                categoryModel = CategoryModel.NOISE
            ),
            SoundModel(
                nameSound = R.string.white_noise,
                imageSound = R.drawable.white_noise,
                sound = R.raw.white_noise,
                categoryModel = CategoryModel.NOISE
            ),
            SoundModel(
                nameSound = R.string.coffee_shop,
                imageSound = R.drawable.coffee_shop,
                sound = R.raw.coffee_shop,
                categoryModel = CategoryModel.ENVIRONMENT
            ),
            SoundModel(
                nameSound = R.string.people_talking,
                imageSound = R.drawable.people_talking,
                sound = R.raw.people_talking,
                categoryModel = CategoryModel.ENVIRONMENT
            ),
            SoundModel(
                nameSound = R.string.highway,
                imageSound = R.drawable.highway,
                sound = R.raw.highway,
                categoryModel = CategoryModel.ENVIRONMENT
            ),
            SoundModel(
                nameSound = R.string.public_places,
                imageSound = R.drawable.public_places,
                sound = R.raw.public_places,
                categoryModel = CategoryModel.ENVIRONMENT
            ),
            SoundModel(
                nameSound = R.string.subway,
                imageSound = R.drawable.subway,
                sound = R.raw.subway,
                categoryModel = CategoryModel.ENVIRONMENT
            ),
            SoundModel(
                nameSound = R.string.bird_chirping,
                imageSound = R.drawable.bird_chirping,
                sound = R.raw.bird_chirping,
                categoryModel = CategoryModel.ANIMAL
            ),
            SoundModel(
                nameSound = R.string.morning_bird,
                imageSound = R.drawable.morning_bird,
                sound = R.raw.morning_bird,
                categoryModel = CategoryModel.ANIMAL
            ),
            SoundModel(
                nameSound = R.string.cricket,
                imageSound = R.drawable.cricket,
                sound = R.raw.cricket,
                categoryModel = CategoryModel.ANIMAL
            ),
            SoundModel(
                nameSound = R.string.cricket_2,
                imageSound = R.drawable.cricket_2,
                sound = R.raw.cricket_2,
                categoryModel = CategoryModel.ANIMAL
            ),
            SoundModel(
                nameSound = R.string.frog,
                imageSound = R.drawable.frog,
                sound = R.raw.frog,
                categoryModel = CategoryModel.ANIMAL
            ),
            SoundModel(
                nameSound = R.string.frog_2,
                imageSound = R.drawable.frog_2,
                sound = R.raw.frog_2,
                categoryModel = CategoryModel.ANIMAL
            ),
            SoundModel(
                nameSound = R.string.asmr,
                imageSound = R.drawable.asmr,
                sound = R.raw.asmr,
                categoryModel = CategoryModel.ASRM
            ),
            SoundModel(
                nameSound = R.string.asmr_2,
                imageSound = R.drawable.asmr_2,
                sound = R.raw.asmr_2,
                categoryModel = CategoryModel.ASRM
            ),
            SoundModel(
                nameSound = R.string.asmr_3,
                imageSound = R.drawable.asmr_3,
                sound = R.raw.asmr_3,
                categoryModel = CategoryModel.ASRM
            ),
            SoundModel(
                nameSound = R.string.asmr_4,
                imageSound = R.drawable.asmr_4,
                sound = R.raw.asmr_4,
                categoryModel = CategoryModel.ASRM
            ),
            SoundModel(
                nameSound = R.string.asmr_5,
                imageSound = R.drawable.asmr_5,
                sound = R.raw.asmr_5,
                categoryModel = CategoryModel.ASRM
            ),
            SoundModel(
                nameSound = R.string.asmr_6,
                imageSound = R.drawable.asmr_6,
                sound = R.raw.asmr_6,
                categoryModel = CategoryModel.ASRM
            ),
            SoundModel(
                nameSound = R.string.grill_bbq,
                imageSound = R.drawable.grill_bbq,
                sound = R.raw.grill_bbq,
                categoryModel = CategoryModel.ASRM
            ),
            SoundModel(
                nameSound = R.string.airplane,
                imageSound = R.drawable.airplane,
                sound = R.raw.airplane,
                categoryModel = CategoryModel.MECHANICAL
            ),
            SoundModel(
                nameSound = R.string.celling_fan,
                imageSound = R.drawable.celling_fan,
                sound = R.raw.celling_fan,
                categoryModel = CategoryModel.MECHANICAL
            ),
            SoundModel(
                nameSound = R.string.coffee_maker,
                imageSound = R.drawable.coffee_maker,
                sound = R.raw.coffee_maker,
                categoryModel = CategoryModel.MECHANICAL
            ),
            SoundModel(
                nameSound = R.string.heater,
                imageSound = R.drawable.heater,
                sound = R.raw.heater,
                categoryModel = CategoryModel.MECHANICAL
            ),
            SoundModel(
                nameSound = R.string.oscillating_fan,
                imageSound = R.drawable.oscillating_fan,
                sound = R.raw.oscillating_fan,
                categoryModel = CategoryModel.MECHANICAL
            ),
            SoundModel(
                nameSound = R.string.train,
                imageSound = R.drawable.train,
                sound = R.raw.train,
                categoryModel = CategoryModel.MECHANICAL
            ),
            SoundModel(
                nameSound = R.string.train_2,
                imageSound = R.drawable.train_2,
                sound = R.raw.train_2,
                categoryModel = CategoryModel.MECHANICAL
            ),
            SoundModel(
                nameSound = R.string.vacuum_cleaner,
                imageSound = R.drawable.vacuum_cleaner,
                sound = R.raw.vacuum_cleaner,
                categoryModel = CategoryModel.MECHANICAL
            ),
            SoundModel(
                nameSound = R.string.binalural_beats,
                imageSound = R.drawable.binalural_beats,
                sound = R.raw.binalural_beats,
                categoryModel = CategoryModel.MUSICAL
            ),
            SoundModel(
                nameSound = R.string.piano,
                imageSound = R.drawable.piano,
                sound = R.raw.piano,
                categoryModel = CategoryModel.MUSICAL
            ),
            SoundModel(
                nameSound = R.string.piano_2,
                imageSound = R.drawable.piano_2,
                sound = R.raw.piano_2,
                categoryModel = CategoryModel.MUSICAL
            ),
            SoundModel(
                nameSound = R.string.piano_3,
                imageSound = R.drawable.piano_3,
                sound = R.raw.piano_3,
                categoryModel = CategoryModel.MUSICAL
            ),
            SoundModel(
                nameSound = R.string.piano_4,
                imageSound = R.drawable.piano_4,
                sound = R.raw.piano_4,
                categoryModel = CategoryModel.MUSICAL
            ),
            SoundModel(
                nameSound = R.string.rain_drum,
                imageSound = R.drawable.rain_drum,
                sound = R.raw.rain_drum,
                categoryModel = CategoryModel.MUSICAL
            ),
            SoundModel(
                nameSound = R.string.synth_pad,
                imageSound = R.drawable.synth_pad,
                sound = R.raw.synth_pad,
                categoryModel = CategoryModel.MUSICAL
            ),
            SoundModel(
                nameSound = R.string.gentle_breeze,
                imageSound = R.drawable.gentle_breeze,
                sound = R.raw.gentle_breeze,
                categoryModel = CategoryModel.NATURAL
            ),
            SoundModel(
                nameSound = R.string.heavy_rain_with_thunder,
                imageSound = R.drawable.heavy_rain_with_thunder,
                sound = R.raw.heavy_rain_with_thunder,
                categoryModel = CategoryModel.NATURAL
            ),
            SoundModel(
                nameSound = R.string.heavy_rain,
                imageSound = R.drawable.heavy_rain,
                sound = R.raw.heavy_rain,
                categoryModel = CategoryModel.NATURAL
            ),
            SoundModel(
                nameSound = R.string.ocean_waves,
                imageSound = R.drawable.ocean_waves,
                sound = R.raw.ocean_waves,
                categoryModel = CategoryModel.NATURAL
            ),
            SoundModel(
                nameSound = R.string.rain_with_thunder,
                imageSound = R.drawable.rain_with_thunder,
                sound = R.raw.rain_with_thunder,
                categoryModel = CategoryModel.NATURAL
            ),
            SoundModel(
                nameSound = R.string.rain,
                imageSound = R.drawable.rain,
                sound = R.raw.rain,
                categoryModel = CategoryModel.NATURAL
            ),
            SoundModel(
                nameSound = R.string.soothing_wind,
                imageSound = R.drawable.soothing_wind,
                sound = R.raw.soothing_wind,
                categoryModel = CategoryModel.NATURAL
            ),
            SoundModel(
                nameSound = R.string.strong_wind,
                imageSound = R.drawable.strong_wind,
                sound = R.raw.strong_wind,
                categoryModel = CategoryModel.NATURAL
            ),

            SoundModel(
                nameSound = R.string.thunder,
                imageSound = R.drawable.thunder,
                sound = R.raw.thunder,
                categoryModel = CategoryModel.NATURAL
            ),
            SoundModel(
                nameSound = R.string.water_fall,
                imageSound = R.drawable.water_fall,
                sound = R.raw.water_fall,
                categoryModel = CategoryModel.NATURAL
            ),
            SoundModel(
                nameSound = R.string.wind,
                imageSound = R.drawable.wind,
                sound = R.raw.wind,
                categoryModel = CategoryModel.NATURAL
            ),
            SoundModel(
                nameSound = R.string.blizzard,
                imageSound = R.drawable.blizzard,
                sound = R.raw.blizzard,
                categoryModel = CategoryModel.SEASONAL
            ),
            SoundModel(
                nameSound = R.string.dried_leaves,
                imageSound = R.drawable.dried_leaves,
                sound = R.raw.dried_leaves,
                categoryModel = CategoryModel.SEASONAL
            ),
            SoundModel(
                nameSound = R.string.heavy_snowstorm,
                imageSound = R.drawable.heavy_snowstorm,
                sound = R.raw.heavy_snowstorm,
                categoryModel = CategoryModel.SEASONAL
            ),
            SoundModel(
                nameSound = R.string.leaves_falling,
                imageSound = R.drawable.leaves_falling,
                sound = R.raw.leaves_falling,
                categoryModel = CategoryModel.SEASONAL
            ),
            SoundModel(
                nameSound = R.string.snow_storm,
                imageSound = R.drawable.snow_storm,
                sound = R.raw.snow_storm,
                categoryModel = CategoryModel.SEASONAL
            ),
            SoundModel(
                nameSound = R.string.black_hole,
                imageSound = R.drawable.black_hole,
                sound = R.raw.black_hole,
                categoryModel = CategoryModel.SPACE
            ),
            SoundModel(
                nameSound = R.string.earth,
                imageSound = R.drawable.earth,
                sound = R.raw.earth,
                categoryModel = CategoryModel.SPACE
            ),
            SoundModel(
                nameSound = R.string.saturn,
                imageSound = R.drawable.saturn,
                sound = R.raw.saturn,
                categoryModel = CategoryModel.SPACE
            ),
            SoundModel(
                nameSound = R.string.spaceship_ambience,
                imageSound = R.drawable.spaceship_ambience,
                sound = R.raw.spaceship_ambience,
                categoryModel = CategoryModel.SPACE
            ),
            SoundModel(
                nameSound = R.string.spaceship_cockpit,
                imageSound = R.drawable.spaceship_cockpit,
                sound = R.raw.spaceship_cockpit,
                categoryModel = CategoryModel.SPACE
            ),
            SoundModel(
                nameSound = R.string.sun,
                imageSound = R.drawable.sun,
                sound = R.raw.sun,
                categoryModel = CategoryModel.SPACE
            ),
            SoundModel(
                nameSound = R.string.om_chanting,
                imageSound = R.drawable.om_chanting,
                sound = R.raw.om_chanting,
                categoryModel = CategoryModel.SPIRITUAL
            ),
            SoundModel(
                nameSound = R.string.temple_bell,
                imageSound = R.drawable.temple_bell,
                sound = R.raw.temple_bell,
                categoryModel = CategoryModel.SPIRITUAL
            ),
            SoundModel(
                nameSound = R.string.temple_bell_2,
                imageSound = R.drawable.temple_bell_2,
                sound = R.raw.temple_bell_2,
                categoryModel = CategoryModel.SPIRITUAL
            ),
            SoundModel(
                nameSound = R.string.tibetan_bowls,
                imageSound = R.drawable.tibetan_bowls,
                sound = R.raw.tibetan_bowls,
                categoryModel = CategoryModel.SPIRITUAL
            ),
            SoundModel(
                nameSound = R.string.tibetan_bowls_2,
                imageSound = R.drawable.tibetan_bowls_2,
                sound = R.raw.tibetan_bowls_2,
                categoryModel = CategoryModel.SPIRITUAL
            ),
            SoundModel(
                nameSound = R.string.tibetan_bowls_3,
                imageSound = R.drawable.tibetan_bowls_3,
                sound = R.raw.tibetan_bowls_3,
                categoryModel = CategoryModel.SPIRITUAL
            )
        )
    }
}