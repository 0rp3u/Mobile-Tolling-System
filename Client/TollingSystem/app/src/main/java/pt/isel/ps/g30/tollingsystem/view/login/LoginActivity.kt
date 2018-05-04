package pt.isel.ps.g30.tollingsystem.view.login

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import android.widget.EditText
import pt.isel.ps.g30.tollingsystem.R

import org.jetbrains.anko.startActivity
import pt.isel.ps.g30.tollingsystem.databinding.ActivityLoginBinding
import pt.isel.ps.g30.tollingsystem.extensions.app
import pt.isel.ps.g30.tollingsystem.injection.module.AuthModule
import pt.isel.ps.g30.tollingsystem.interactor.auth.AuthInteractor
import pt.isel.ps.g30.tollingsystem.presenter.login.LoginPresenter
import pt.isel.ps.g30.tollingsystem.presenter.login.LoginPresenterImp
import pt.isel.ps.g30.tollingsystem.view.base.BaseActivity
import pt.isel.ps.g30.tollingsystem.view.main.WithViewPagerActivity
import javax.inject.Inject

class LoginActivity : BaseActivity<LoginPresenter,LoginView>(), LoginView {

    @Inject
    lateinit var interactor: AuthInteractor

    private lateinit var bind: ActivityLoginBinding

    override fun injectDependencies() {
        app.applicationComponent
                .plus(AuthModule())
                .injectTo(this)
    }

    override fun instantiatePresenter(): LoginPresenter
            = LoginPresenterImp(interactor)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setContentView(R.layout.activity_login)
    }

    fun onLogin(view: View){
        presenter.authenticate(findViewById<EditText>(R.id.login).text.toString(), findViewById<EditText>(R.id.password).text.toString())
    }

    override fun successfullLogin() {
        startActivity<WithViewPagerActivity>()
    }

    override fun failedLogin() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoadingIndicator() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoadingIndicator() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
