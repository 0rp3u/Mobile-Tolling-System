package pt.isel.ps.g30.tollingsystem.view.login

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import pt.isel.ps.g30.tollingsystem.R

import kotlinx.android.synthetic.main.activity_login.*
import pt.isel.ps.g30.tollingsystem.extension.app
import pt.isel.ps.g30.tollingsystem.presenter.login.LoginPresenter
import pt.isel.ps.g30.tollingsystem.view.base.BaseActivity
import pt.isel.ps.g30.tollingsystem.view.main.MainActivity
import javax.inject.Inject
import android.content.Intent
import android.net.Uri
import pt.isel.ps.g30.tollingsystem.extension.startActivity
import pt.isel.ps.g30.tollingsystem.injection.module.PresentersModule


class LoginActivity : BaseActivity<LoginPresenter,LoginView>(), LoginView {


    @Inject
    override lateinit var presenter: LoginPresenter

    override fun injectDependencies() {
        app.applicationComponent
                .plus(PresentersModule())
                .injectTo(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

       link_signup.setOnClickListener {
           startActivity(Intent(
                   Intent.ACTION_VIEW,
                   Uri.parse("https://www.viaverde.pt/outros/adesao?s=dados_contracto")
           ))
        }
    }

    fun onLogin(view: View){
        presenter.authenticate(input_login.text.toString(), input_password.text.toString())
    }

    override fun successfullLogin() {
        finish()
        startActivity<MainActivity>()
    }

    override fun failedLogin() {
        btn_login.apply {
            text = "failed to login, try again?"
            startAnimation(AnimationUtils.loadAnimation(this@LoginActivity, R.anim.shake))
        }

    }

    override fun showLoadingIndicator() {
        btn_login.apply {
            isEnabled = false
            text = "loading..."
            alpha = 0.50f
        }

    }

    override fun hideLoadingIndicator() {
        btn_login.apply {
            alpha = 1f
            isEnabled = true
            text = "login"
        }
    }

    override fun onDestroy() {
        presenter.cancelRequest()
        super.onDestroy()
    }
}
