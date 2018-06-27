package pt.isel.ps.g30.tollingsystem.view.main

import android.content.res.Configuration
import android.os.Bundle
import android.util.SparseIntArray
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import kotlinx.android.synthetic.main.activity_main.*
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.view.common.CustomFragmentPagerAdapter
import pt.isel.ps.g30.tollingsystem.view.navigation.NavigationViewFragment
import pt.isel.ps.g30.tollingsystem.view.notifications.NotificationFragment
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehiclesFragment
import q.rorbin.badgeview.Badge
import q.rorbin.badgeview.QBadgeView


class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName

        const val SELECTED_ITEM_KEY = "SELECTED_ITEM_KEY"
    }

    private lateinit var adapterCustom: CustomFragmentPagerAdapter
    private lateinit var badge: Badge


    // collections
    private val items: SparseIntArray = SparseIntArray(3)// used for change ViewPager selected item

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

    }

    override fun onStart() {
        super.onStart()

        initData()
        initEvent()
    }

    override fun onStop() {
        super.onStop()
        vp.clearOnPageChangeListeners()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(SELECTED_ITEM_KEY, bnve.selectedItemId)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        bnve.selectedItemId = savedInstanceState?.getInt(SELECTED_ITEM_KEY, 0) ?: 0
    }

    private fun initView() {
        badge = addBadgeAt(2, 1)
        adapterCustom = CustomFragmentPagerAdapter(supportFragmentManager)
        vp.adapter = adapterCustom
    }

    private fun initData() {
        //items = SparseIntArray()

        adapterCustom.fragments = listOf(
                NavigationViewFragment(),
                VehiclesFragment().also{ it.arguments = Bundle().apply {putString("title", getString(R.string.vehicle)) } },
                NotificationFragment().also { it.arguments = Bundle().apply {putString("title", getString(R.string.notifications)) } }

        )
        items.apply {
            put(R.id.i_navigation, 0)
            put(R.id.i_vheicles, 1)
            put(R.id.i_notifications, 2)
        }
    }

    /**
     * set listeners
     */
    private fun initEvent() {

        bnve.setOnNavigationItemSelectedListener {
            var previousPos = -1
            val position = items.get(it.itemId)
            if (previousPos != position) {
                // only set item when item changed
                previousPos = position
                vp.currentItem = position
            }
            true
        }

        vp.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
               // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onPageScrollStateChanged(state: Int) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onPageSelected(position: Int) {
                bnve.selectedItemId = bnve.menu.getItem(position).itemId
            }


        })
    }

    fun addBadgeAt(position: Int, number: Int): Badge {

        val view = (bnve.getChildAt(0) as BottomNavigationMenuView).getChildAt(position) as BottomNavigationItemView
        return QBadgeView(this)
                .setBadgeNumber(number)
                .bindTarget(view)
                .let{
                    if(this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                        it.setGravityOffset(38f, 2f, true)
                        else
                        it.setGravityOffset(62f, 2f, true)
                }
    }
}
