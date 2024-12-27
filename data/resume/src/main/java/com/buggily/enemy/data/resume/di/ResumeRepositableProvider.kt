package com.buggily.enemy.data.resume.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.buggily.enemy.data.resume.ResumeRepositable
import com.buggily.enemy.data.resume.ResumeRepository
import com.buggily.enemy.local.resume.LocalResume
import com.buggily.enemy.local.resume.LocalResumeSerializer
import com.buggily.enemy.local.resume.LocalResumeSourceable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object ResumeRepositableProvider {

    @Provides
    fun providesResumeRepositable(
        localResumeSource: LocalResumeSourceable,
    ): ResumeRepositable = ResumeRepository(
        localResumeSource = localResumeSource,
    )

    @Provides
    fun providesLocalResumeDataStore(
        @ApplicationContext context: Context,
    ): DataStore<LocalResume> = context.resumeDataStore

    private const val FILE_NAME = "local_resume.pb"
    private val Context.resumeDataStore: DataStore<LocalResume> by dataStore(
        fileName = FILE_NAME,
        serializer = LocalResumeSerializer,
    )
}
