package com.buggily.enemy.local.resume.di

import androidx.datastore.core.DataStore
import com.buggily.enemy.local.resume.LocalResume
import com.buggily.enemy.local.resume.LocalResumeSource
import com.buggily.enemy.local.resume.LocalResumeSourceable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object LocalResumeSourceableProvider {

    @Provides
    fun provides(
        dataStore: DataStore<LocalResume>,
    ): LocalResumeSourceable = LocalResumeSource(
        dataStore = dataStore,
    )
}
