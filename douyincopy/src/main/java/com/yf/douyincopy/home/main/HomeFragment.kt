package com.yf.douyincopy.home.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.yf.douyincopy.bean.Cards
import com.yf.douyincopy.R
import kotlinx.android.synthetic.main.fragment_nested_home.*


/**
 * [com.yf.douyincopy.home.MainFragment] 的第一个标签home
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nested_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
        viewPager2.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return Cards.DATA.size
            }

            override fun createFragment(position: Int): Fragment {
                return HomeVideoFragment.create(Cards.DATA[position])
            }
        }

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Cards.changeCard(Cards.DATA[position])
            }
        })

    }


}
