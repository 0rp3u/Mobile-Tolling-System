package pt.isel.ps.g30.tollingsystem.extension

import androidx.core.view.get
import androidx.core.view.size
import com.google.android.material.bottomnavigation.BottomNavigationView


/**
 * get the current checked item geoLocation
 *
 * @return index of item, start from 0.
 */
fun BottomNavigationView.getCurrentItemPosition(): Int {
    (0..menu.size).forEach {
        if(menu[it].isChecked) return it
    }
    return 0
}

fun BottomNavigationView.getCurrentItem() = menu[getCurrentItemPosition()]

fun BottomNavigationView.setCurrentItem(index: Int){
    selectedItemId = menu[index].itemId
}