package pt.isel.ps.g30.tollingsystem.view.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class CustomFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {


    companion object {
        val ARG_NAME = "ARG_NAME"
    }

    var fragments = listOf<Fragment>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getCount() = fragments.size

    override fun getPageTitle(position: Int): String =
            fragments[position].arguments?.getString(ARG_NAME) ?: "no title"

    override fun getItem(position: Int) = fragments[position]

}
