package com.hdt.sleepsound.di

import android.content.Context
import android.content.SharedPreferences
import com.hdt.sleepsound.data.local.MixSoundDatabase
import com.hdt.sleepsound.data.local.RecordDatabase
import com.hdt.sleepsound.data.local.provideAppDb
import com.hdt.sleepsound.data.local.provideAppDbMix
import com.hdt.sleepsound.data.repository.MixSoundRepository
import com.hdt.sleepsound.data.repository.RecordRepository
import com.hdt.sleepsound.utils.PreferenceHelper
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single<SharedPreferences> {
        androidApplication().getSharedPreferences(
            PreferenceHelper.PREFS_NAME,
            Context.MODE_PRIVATE
        )
    }
    single { PreferenceHelper(get()) }
    single {
        provideAppDb(androidApplication())
    }
    single { get<RecordDatabase>().recordDao() }
    single { RecordRepository(dao = get()) }

    single {
        provideAppDbMix(androidApplication())
    }
    single { get<MixSoundDatabase>().mixSoundDao() }
    single { MixSoundRepository(dao = get()) }

}
