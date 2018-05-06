package pt.isel.ps.g30.tollingsystem.view.splash

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.edit
import pt.isel.ps.g30.tollingsystem.R

import org.jetbrains.anko.startActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.textColor
import org.jetbrains.anko.vibrator
import pt.isel.ps.g30.tollingsystem.extensions.app
import pt.isel.ps.g30.tollingsystem.injection.module.ApplicationModule_ProvideSharedPreferencesFactory
import pt.isel.ps.g30.tollingsystem.injection.module.AuthModule
import pt.isel.ps.g30.tollingsystem.presenter.login.LoginPresenter
import pt.isel.ps.g30.tollingsystem.presenter.login.SplashPresenter
import pt.isel.ps.g30.tollingsystem.view.base.BaseActivity
import pt.isel.ps.g30.tollingsystem.view.login.LoginActivity
import pt.isel.ps.g30.tollingsystem.view.main.MainActivity
import javax.inject.Inject

class SplashActivity : BaseActivity<SplashPresenter,SplashView>(), SplashView {

    //TODO use @String values for the literals used on the UI


    @Inject
    override lateinit var presenter: SplashPresenter

    @Inject
    lateinit var preferences: SharedPreferences

    override fun injectDependencies() {
        app.applicationComponent
                .plus(AuthModule())
                .injectTo(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences.apply {
           /* if(contains("login") && contains("password")){ //TODO this will be on the database
                presenter.authenticate(getString("login",""), getString("password",""))
            }else  failedLogin()*/
            presenter.authenticate(getString("login",""), getString("password",""))

        }
    }

    override fun successfullLogin() {
        startActivity<MainActivity>()
        finish()
    }

    override fun failedLogin() {
        startActivity<LoginActivity>()
        finish()
    }

    override fun showLoadingIndicator() {

    }

    override fun hideLoadingIndicator() {

    }


}
