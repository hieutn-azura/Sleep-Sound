package com.hdt.sleepsound.di

import com.hdt.sleepsound.ui.mixsound.MixSoundViewModel
import com.hdt.sleepsound.ui.record.RecordViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { RecordViewModel(recordRepository = get()) }
    viewModel { MixSoundViewModel(get()) }
}
