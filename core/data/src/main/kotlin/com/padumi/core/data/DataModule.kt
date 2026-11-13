package com.padumi.core.data

import android.content.Context
import androidx.room.Room
import com.padumi.core.domain.GreetingRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {
    @Binds
    @Singleton
    abstract fun bindGreetingLocalDataSource(implementation: RoomGreetingLocalDataSource): GreetingLocalDataSource

    @Binds
    @Singleton
    abstract fun bindGreetingRepository(implementation: DefaultGreetingRepository): GreetingRepository

    companion object {
        @Provides
        @Singleton
        fun providePadumiDatabase(
            @ApplicationContext context: Context,
        ): PadumiDatabase =
            Room
                .databaseBuilder(context, PadumiDatabase::class.java, PadumiDatabase.DATABASE_NAME)
                .build()

        @Provides
        fun provideGreetingDao(database: PadumiDatabase): GreetingDao = database.greetingDao()
    }
}
