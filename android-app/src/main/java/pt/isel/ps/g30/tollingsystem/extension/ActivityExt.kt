package pt.isel.ps.g30.tollingsystem.extension

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.template_toolbar.*
import org.jetbrains.anko.internals.AnkoInternals

fun AppCompatActivity.initToolbar(title: String? = null, hasParent: Boolean = false) {
    setSupportActionBar(toolbar)

    // set title (if needed)
    title?.let {
        supportActionBar?.title = it
    }

    // set back arrow (if needed)
    if (hasParent) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}

inline fun <reified T: Activity> Context.startActivity(vararg params: Pair<String, Any>) =
        AnkoInternals.internalStartActivity(this, T::class.java, params)
