package com.surelabsid.mrjempootdriver.dagger_hilt

import android.app.Application
import androidx.room.Room
import com.surelabsid.mrjempootdriver.room.Database
import com.surelabsid.mrjempootdriver.room.dao.DaoOrderan
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.database.NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): Database {
        return Room.databaseBuilder(application, Database::class.java, NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDaoOrderan(database: Database): DaoOrderan {
        return database.daoOrderan()
    }

}