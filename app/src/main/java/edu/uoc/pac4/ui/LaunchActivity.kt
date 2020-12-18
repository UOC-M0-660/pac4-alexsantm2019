package edu.uoc.pac4.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.uoc.pac4.R
import edu.uoc.pac4.ui.login.LoginActivity
import edu.uoc.pac4.data.SessionManager
import edu.uoc.pac4.data.di.dataModule
import edu.uoc.pac4.ui.di.uiModule
import edu.uoc.pac4.ui.streams.StreamsActivity
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin

class LaunchActivity : AppCompatActivity() {

    private val launchViewModel by viewModel<LaunchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        startKoin {
            // declare used Android context
            androidContext(this@LaunchActivity)
            // declare modules
            modules(dataModule, uiModule)
            checkUserSession()
        }

    }

    private fun checkUserSession(){
        launchViewModel.getUserAvailability()
        launchViewModel.isUserAvailable.observe(this){
            if (it){
                startActivity(Intent(this, StreamsActivity::class.java))
            }else{
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }
    }

}