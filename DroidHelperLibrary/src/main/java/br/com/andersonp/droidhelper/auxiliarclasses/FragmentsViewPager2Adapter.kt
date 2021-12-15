package br.com.andersonp.droidhelper.auxiliarclasses

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentsViewPager2Adapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, private val listaFrags: List<Fragment>) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return listaFrags.size
    }

    override fun createFragment(position: Int): Fragment {
        return listaFrags[position]
    }

    operator fun get(i: Int): Fragment {
        return listaFrags[i]
    }
}