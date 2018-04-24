package pt.isel.ps.g30.tollingsystem.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import pt.isel.ps.g30.tollingsystem.TollingSystemApp

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
