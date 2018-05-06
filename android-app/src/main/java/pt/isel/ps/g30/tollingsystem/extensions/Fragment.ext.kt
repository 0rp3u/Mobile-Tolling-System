package pt.isel.ps.g30.tollingsystem.extensions

import android.support.v4.app.Fragment
import pt.isel.ps.g30.tollingsystem.TollingSystemApp

val Fragment.app: TollingSystemApp
    get() = requireActivity().application as TollingSystemApp