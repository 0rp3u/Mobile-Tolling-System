package pt.isel.ps.g30.tollingsystem.view

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.databinding.FragTextBinding
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenter
import pt.isel.ps.g30.tollingsystem.view.base.BaseView

class TextFragment: Fragment() {

    private var title: String? = null
    internal var binding: FragTextBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // get title
        title = arguments!!.getString("title")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = View.inflate(context, R.layout.frag_text, null)
        // bind view
        binding = DataBindingUtil.bind<ViewDataBinding>(view) as FragTextBinding?
        binding!!.tvTitle.setText(title)
        return view
    }
}