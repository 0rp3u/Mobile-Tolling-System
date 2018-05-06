package pt.isel.ps.g30.tollingsystem.view.login

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
import pt.isel.ps.g30.tollingsystem.view.base.BaseActivity
import pt.isel.ps.g30.tollingsystem.view.main.MainActivity
import javax.inject.Inject

class LoginActivity : BaseActivity<LoginPresenter,LoginView>(), LoginView {

    //TODO use @String values for the literals used on the UI


    @Inject
    override lateinit var presenter: LoginPresenter

    override fun injectDependencies() {
        app.applicationComponent
                .plus(AuthModule())
                .injectTo(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        this.link_signup.setOnClickListener {
            //startActivity<SignupActivity>() TODO create sign up activity?
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


}
