package com.yf.douyincopy.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.yf.douyincopy.R
import com.yf.douyincopy.UserInfoFragment
import kotlinx.android.synthetic.main.fragment_douyin_copy.*


/**
 * 这个fragment共分 左，中，右 三部分
 * 左：相机
 * 中：核心
 * 右：详情
 */
class DouyinCopyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_douyin_copy, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager.adapter = object :
            FragmentPagerAdapter(fragmentManager!!, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> CameraFragment()
                    1 -> MainFragment()
                    else -> UserInfoFragment.create()
                }
            }

            override fun getCount(): Int = 3

        }
        viewPager.currentItem = 1
    }


}
