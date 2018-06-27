package pt.isel.ps.g30.tollingsystem.view.splash

import android.os.Bundle
import org.jetbrains.anko.startActivity
import pt.isel.ps.g30.tollingsystem.extension.app
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


    override fun injectDependencies() {
        app.applicationComponent
                .plus(PresentersModule())
                .injectTo(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.authenticate("","")
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
