package com.example.xgbuddy

import android.content.Context
import com.example.xgbuddy.util.MidiDataUtility
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
    fun provideMidiDataUtility(@ApplicationContext context: Context): MidiDataUtility = MidiDataUtility(context)
}