package edu.uoc.pac4
import android.app.Application
import edu.uoc.pac4.data.di.dataModule
import edu.uoc.pac4.ui.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class InitApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@InitApp)
            modules(dataModule, uiModule)
        }
    }

}