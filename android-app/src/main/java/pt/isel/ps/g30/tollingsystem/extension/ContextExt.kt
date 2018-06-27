 package pt.isel.ps.g30.tollingsystem.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.toast

 fun Context.bitmapDescriptorFromVector(context: Context, @DrawableRes vectorDrawableResourceId: Int): BitmapDescriptor {
    ContextCompat.getDrawable(context, vectorDrawableResourceId)?.apply {
        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        return Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888).let{
            draw(Canvas(it))
            BitmapDescriptorFactory.fromBitmap(it)
        }
    }
    throw Exception()
}

 /**
  * Display the simple Toast message with the [Toast.LENGTH_SHORT] duration.
  *
  * @param message the message text resource.
  */
 inline fun AnkoContext<*>.toast(message: Int) = ctx.toast(message)

 /**
  * Display the simple Toast message with the [Toast.LENGTH_SHORT] duration.
  *
  * @param message the message text resource.
  */
 inline fun Fragment.toast(message: Int) = context?.toast(message)

 /**
  * Display the simple Toast message with the [Toast.LENGTH_SHORT] duration.
  *
  * @param message the message text resource.
  */
 inline fun Context.toast(message: Int): Toast = Toast
         .makeText(this, message, Toast.LENGTH_SHORT)
         .apply {
             show()
         }

 /**
  * Display the simple Toast message with the [Toast.LENGTH_SHORT] duration.
  *
  * @param message the message text.
  */
 inline fun AnkoContext<*>.toast(message: CharSequence) = ctx.toast(message)

 /**
  * Display the simple Toast message with the [Toast.LENGTH_SHORT] duration.
  *
  * @param message the message text.
  */
 inline fun Fragment.toast(message: CharSequence) = context?.toast(message)

 /**
  * Display the simple Toast message with the [Toast.LENGTH_SHORT] duration.
  *
  * @param message the message text.
  */
 inline fun Context.toast(message: CharSequence): Toast = Toast
         .makeText(this, message, Toast.LENGTH_SHORT)
         .apply {
             show()
         }

 /**
  * Display the simple Toast message with the [Toast.LENGTH_LONG] duration.
  *
  * @param message the message text resource.
  */
 inline fun AnkoContext<*>.longToast(message: Int) = ctx.longToast(message)

 /**
  * Display the simple Toast message with the [Toast.LENGTH_LONG] duration.
  *
  * @param message the message text resource.
  */
 inline fun Fragment.longToast(message: Int) = requireActivity().longToast(message)

 /**
  * Display the simple Toast message with the [Toast.LENGTH_LONG] duration.
  *
  * @param message the message text resource.
  */
 inline fun Context.longToast(message: Int): Toast = Toast
         .makeText(this, message, Toast.LENGTH_LONG)
         .apply {
             show()
         }

 /**
  * Display the simple Toast message with the [Toast.LENGTH_LONG] duration.
  *
  * @param message the message text.
  */
 inline fun AnkoContext<*>.longToast(message: CharSequence) = ctx.longToast(message)

 /**
  * Display the simple Toast message with the [Toast.LENGTH_LONG] duration.
  *
  * @param message the message text.
  */
 inline fun Fragment.longToast(message: CharSequence) = requireActivity().longToast(message)

 /**
  * Display the simple Toast message with the [Toast.LENGTH_LONG] duration.
  *
  * @param message the message text.
  */
 inline fun Context.longToast(message: CharSequence): Toast = Toast
         .makeText(this, message, Toast.LENGTH_LONG)
         .apply {
             show()
         }