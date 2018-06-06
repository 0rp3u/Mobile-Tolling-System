package pt.isel.ps.g30.tollingsystem.view.splash

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import org.jetbrains.anko.startActivity
import pt.isel.ps.g30.tollingsystem.extensions.app
import pt.isel.ps.g30.tollingsystem.injection.module.PresentersModule
import pt.isel.ps.g30.tollingsystem.presenter.splash.SplashPresenter
import pt.isel.ps.g30.tollingsystem.view.base.BaseActivity
import pt.isel.ps.g30.tollingsystem.view.login.LoginActivity
import pt.isel.ps.g30.tollingsystem.view.main.MainActivity

import javax.inject.Inject

class SplashActivity : BaseActivity<SplashPresenter, SplashView>(), SplashView {

    //TODO use @String values for the literals used on the UI


    @Inject
    override lateinit var presenter: SplashPresenter

    @Inject
    lateinit var preferences: SharedPreferences

    override fun injectDependencies() {
        app.applicationComponent
                .plus(PresentersModule())
                .injectTo(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.authenticate("","")


//        preferences.apply {
//           /* if(contains("login") && contains("password")){ //TODO this will be on the database
//                presenter.authenticate(getString("login",""), getString("password",""))
//            }else  failedLogin()*/
//            presenter.authenticate(getString("login",""), getString("password",""))
//
//        }
    }

    override fun successfullLogin() {
        startActivity<MainActivity>()
        finish()
    }

    override fun failedLogin() {
        startActivity<LoginActivity>()
        finish()
    }
}
