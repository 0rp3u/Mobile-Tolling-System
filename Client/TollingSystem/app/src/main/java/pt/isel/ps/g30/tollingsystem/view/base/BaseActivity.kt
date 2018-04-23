package pt.isel.ps.g30.tollingsystem.view.base

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenter


/**
 * @param P the type of the presenter the Activity is based on
 */
abstract class BaseActivity<P: BasePresenter<V>, in V> : BaseView, AppCompatActivity() {

    abstract fun injectDependencies()

    protected lateinit var presenter: P

    /**
     * Instantiates the presenter the Activity is based on.
     */
    protected abstract fun instantiatePresenter(): P


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        injectDependencies()
        presenter = instantiatePresenter()
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
}
