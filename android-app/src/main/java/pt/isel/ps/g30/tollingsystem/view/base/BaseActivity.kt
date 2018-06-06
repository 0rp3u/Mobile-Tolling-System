package pt.isel.ps.g30.tollingsystem.view.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenter


/**
 * @param P the type of the presenter the Activity is based on
 */
abstract class BaseActivity<P: BasePresenter<V>, in V> : BaseView, AppCompatActivity() {

    abstract fun injectDependencies() //<- injects the P presenter

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
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.settings-> {
                //TODO open settings Activity
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onStop() {
        presenter.onViewDetached()
        super.onStop()
    }
}
