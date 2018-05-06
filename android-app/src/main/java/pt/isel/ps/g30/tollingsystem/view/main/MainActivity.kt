package pt.isel.ps.g30.tollingsystem.view.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.SparseIntArray
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import q.rorbin.badgeview.Badge
import q.rorbin.badgeview.QBadgeView
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.view.map.MapViewFragment
import pt.isel.ps.g30.tollingsystem.view.notifications.NotificationFragment
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehicleFragment


class MainActivity : AppCompatActivity(){

    private var adapter: VpAdapter? = null
    private lateinit var badge: Badge


    // collections
    private var items: SparseIntArray? = null// used for change ViewPager selected item
    private lateinit var fragments: List<Fragment>// used for ViewPager adapter

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

    /**
     * change BottomNavigationViewEx style
     */
    private fun initView() {
        bnve.enableItemShiftingMode(true)
        bnve.enableAnimation(false)
        badge = addBadgeAt(2, 1)
    }

    /**
     * create fragments
     */
    private fun initData() {
        items = SparseIntArray(3)

        val mapFragment = MapViewFragment()
        //mapFragment.retainInstance = true


        val backupFragment = VehicleFragment()
        var bundle = Bundle()
        bundle.putString("title", getString(R.string.vehicle))
        backupFragment.arguments = bundle

        // create friends fragment and add it
        val notificationsFragment = NotificationFragment()
        bundle = Bundle()
        bundle.putString("title", getString(R.string.notifications))
        notificationsFragment.arguments = bundle

        // add to fragments for adapter
        fragments = listOf(mapFragment,backupFragment,notificationsFragment)

        // add to items for change ViewPager item
        items!!.put(R.id.i_navigation, 0)
        items!!.put(R.id.i_vheicles, 1)
        items!!.put(R.id.i_notifications, 2)

        // set adapter
        adapter = VpAdapter(supportFragmentManager, fragments)
        vp.adapter = adapter
    }

    /**
     * set listeners
     */
    private fun initEvent() {
        // set listener to change the current item of view pager when click bottom nav item
        bnve.onNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {
            private var previousPosition = -1

            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                val position = items!!.get(item.itemId)
                if (previousPosition != position) {
                    // only set item when item changed
                    previousPosition = position
                    Log.i(TAG, "-----bnve-------- previous item:" + bnve.currentItem + " current item:" + position + " ------------------")
                    vp.currentItem =position
                }
                return true
            }
        }

        // set listener to change the current checked item of bottom nav when scroll view pager
        vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                Log.i(TAG, "-----ViewPager-------- previous item:" + bnve.currentItem + " current item:" + position + " ------------------")
                bnve.currentItem = position
                if( position != 2){
                    badge.badgeNumber++
                }else badge.badgeNumber = 0
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

     fun addBadgeAt(position: Int, number: Int): Badge {
        // add badge
        return QBadgeView(this)
                .setBadgeNumber(number)
                .setGravityOffset(36f, 4f, true)
                .bindTarget(bnve.getBottomNavigationItemView(position))
                .setOnDragStateChangedListener { dragState, badge, targetView ->
//                    if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState)
//                        Toast.makeText(this R.string.tips_badge_removed, Toast.LENGTH_SHORT).show()
                }
    }

    /**
     * view pager adapter
     */
    private class VpAdapter(fm: FragmentManager, private val data: List<Fragment>) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return data.size
        }

        override fun getItem(position: Int): Fragment {
            return data[position]
        }
    }


    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

}
