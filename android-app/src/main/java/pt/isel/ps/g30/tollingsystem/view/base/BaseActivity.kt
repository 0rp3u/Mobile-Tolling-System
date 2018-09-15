package pt.isel.ps.g30.tollingsystem.view.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenter
import javax.inject.Inject


/**
 * @param P the type of the detailsPresenter the Activity is based on
 */
abstract class BaseActivity<P: BasePresenter<V>, in V> : BaseView, AppCompatActivity() {

    abstract fun injectDependencies() //<- injects the P detailsPresenter

    open lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
    }

    override fun onStart() {
        super.onStart()
        @Suppress("UNCHECKED_CAST")
        presenter.onViewAttached(this as V)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_base, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.logout-> {
                presenter.logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStop() {
        presenter.onViewDetached()
        super.onStop()
    }
}
