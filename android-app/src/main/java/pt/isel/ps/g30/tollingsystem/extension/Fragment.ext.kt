package pt.isel.ps.g30.tollingsystem.extension


import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.github.florent37.runtimepermission.RuntimePermission
import com.github.florent37.runtimepermission.PermissionResult
import com.github.florent37.runtimepermission.kotlin.KotlinRuntimePermission
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.internals.AnkoInternals
import pt.isel.ps.g30.tollingsystem.TollingSystemApp

val Fragment.app: TollingSystemApp
    get() = requireActivity().application as TollingSystemApp

inline fun <reified T: Activity> AnkoContext<*>.startActivity(vararg params: Pair<String, Any>) =
        AnkoInternals.internalStartActivity(ctx, T::class.java, params)

inline fun <reified T: Activity> Fragment.startActivity(vararg params: Pair<String, Any>) =
        AnkoInternals.internalStartActivity(this.requireContext(), T::class.java, params)

fun <T: Fragment> T.withArguments(vararg params: Pair<String, Any?>): T {
    arguments = bundleOf(*params)
    return this
}

fun Fragment.askPermission(vararg permissions: String, block: (PermissionResult) -> Unit): KotlinRuntimePermission {
    return KotlinRuntimePermission(RuntimePermission.askPermission(activity)
            .request(permissions.toList())
            .onResponse(block))
}

fun FragmentActivity.askPermission(vararg permissions: String, block: (PermissionResult) -> Unit): KotlinRuntimePermission {
    return KotlinRuntimePermission(RuntimePermission.askPermission(this)
            .request(permissions.toList())
            .onResponse(block))
}