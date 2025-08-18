package com.hdt.sleepsound.di

import android.content.Context
import android.content.SharedPreferences
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
}
