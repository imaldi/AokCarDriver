package com.surelabsid.mrjempootdriver.dagger_hilt

import android.app.Application
import android.content.Context
import com.surelabsid.mrjempootdriver.network.ApiService
import com.surelabsid.mrjempootdriver.utils.SessionManager
import com.surelabsid.mrjempootdriver.utils.GPSTracker
import com.surelabsid.mrjempootdriver.utils.LocationUpdater
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideSessionManager(context: Context): SessionManager = SessionManager(context)

    @Provides
    @Singleton
    fun provideGPSTracker(context: Context): GPSTracker = GPSTracker(context)

    @Provides
    @Singleton
    fun provideLocationUpdater(
        context: Context,
        api: ApiService,
        compositeDisposable: CompositeDisposable
    ): LocationUpdater {
        return LocationUpdater(api, compositeDisposable, context)
    }

}