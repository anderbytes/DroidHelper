package br.com.andersonp.droidhelper

import androidx.fragment.app.Fragment

object Maestro {

    fun Fragment.restart() {
        with(this.parentFragmentManager.beginTransaction()) {
            detach(this@restart)
            commitNow()
            attach(this@restart)
            commitNow()
        }
    }



}