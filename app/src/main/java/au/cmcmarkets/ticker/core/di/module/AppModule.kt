package au.cmcmarkets.ticker.core.di.module

import android.content.Context
import au.cmcmarkets.ticker.CmcApp
import au.cmcmarkets.ticker.utils.AndroidSchedulersProvider
import au.cmcmarkets.ticker.utils.SchedulersProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(app: CmcApp): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulersProvider = AndroidSchedulersProvider()

}