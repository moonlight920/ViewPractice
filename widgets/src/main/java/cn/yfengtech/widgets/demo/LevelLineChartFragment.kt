package cn.yfengtech.widgets.demo


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.yfengtech.widgets.R
import cn.yfengtech.widgets.chart.LevelLineChartView

import kotlinx.android.synthetic.main.fragment_cup.*
import kotlinx.android.synthetic.main.fragment_level_chart.*

internal class LevelLineChartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_level_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lcView.setCurrentLevel(LevelLineChartView.Level.C, 0.3f)
        lcView.setCurrentLevel(LevelLineChartView.Level.FINISH, 0.5f, true)

        btnAnim.setOnClickListener {
            lcView.setCurrentLevel(LevelLineChartView.Level.ZERO, 0.5f, true)
        }

    }

}
