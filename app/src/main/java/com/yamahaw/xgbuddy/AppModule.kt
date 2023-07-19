package com.yamahaw.xgbuddy

import android.content.Context
import com.yamahaw.xgbuddy.midi.MidiSession
import com.yamahaw.xgbuddy.util.QS300Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideMidiSession(@ApplicationContext context: Context): MidiSession = MidiSession(context)

    @Provides
    @Singleton
    fun provideQS300Repository(@ApplicationContext context: Context): QS300Repository =
        QS300Repository(context)
}