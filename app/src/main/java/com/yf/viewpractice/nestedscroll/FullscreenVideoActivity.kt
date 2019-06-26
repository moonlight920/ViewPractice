package com.yf.viewpractice.nestedscroll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.yf.viewpractice.R
import kotlinx.android.synthetic.main.activity_fullscreen_video.*
import kotlinx.android.synthetic.main.fragment_nested_card.*

class FullscreenVideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fullscreen_video)


        viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
        viewPager2.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return Cards.DATA.size
            }

            override fun createFragment(position: Int): Fragment {
                return CardFragment.create(Cards.DATA[position])
            }
        }
    }

    class CardFragment : Fragment() {

        var cardID = 0

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            cardID = arguments?.getInt("id", 0)!!
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = inflater.inflate(R.layout.fragment_nested_card, container, false)
            return view
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
//            val tvId = view.findViewById<TextView>(R.id.tvId)
//            tvId.text = "cardID + $cardID"
            viewPager2Inner.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            viewPager2Inner.adapter = object : FragmentStateAdapter(this) {
                override fun getItemCount(): Int = 2

                override fun createFragment(position: Int): Fragment {
                    return if (position == 0) {
                        VideoFragment.create()
                    } else {
                        UserInfoFragment.create()
                    }
                }

            }
        }

        companion object {

            fun create(card: Card): CardFragment {
                val fragment = CardFragment()
                val bundle = Bundle().apply {
                    putInt("id", card.id)
                }
                fragment.arguments = bundle
                return fragment
            }
        }
    }
}
