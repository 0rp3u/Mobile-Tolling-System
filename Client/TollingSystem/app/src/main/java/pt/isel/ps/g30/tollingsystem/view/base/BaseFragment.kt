package pt.isel.ps.g30.tollingsystem.view.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.databinding.FragBaseBinding


class BaseFragment : Fragment() {
    private var title: String? = null
    internal var binding: FragBaseBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // get title
        title = arguments!!.getString("title")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = View.inflate(context, R.layout.frag_base, null)
        // bind view
        binding = DataBindingUtil.bind<ViewDataBinding>(view) as FragBaseBinding?
        binding!!.tvTitle.setText(title)
        return view
    }
}
