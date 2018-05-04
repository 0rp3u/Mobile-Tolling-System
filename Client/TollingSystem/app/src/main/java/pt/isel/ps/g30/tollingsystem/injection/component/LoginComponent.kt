package pt.isel.ps.g30.tollingsystem.injection.component

import dagger.Subcomponent
import pt.isel.ps.g30.tollingsystem.injection.module.AuthModule
import pt.isel.ps.g30.tollingsystem.view.login.LoginActivity
import javax.inject.Singleton


@Subcomponent(modules = arrayOf(
        AuthModule::class
))
interface LoginComponent {
    fun injectTo(Activity: LoginActivity)

}
