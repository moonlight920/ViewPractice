package cn.yfengtech.widgets.demo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cn.yfengtech.widgets.R
import cn.yfengtech.widgets.practice.PaintAttrView
import kotlinx.android.synthetic.main.fragment_paint_attr.*

/**
 * paint 属性演示
 *
 * @created yf
 * @date 2019-08-05 10:10
 */
class PaintAttrFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_paint_attr, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rgGradient.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioButton -> {
                    paintAttrView.setShader(PaintAttrView.GRADIENT_LINEAR, true)
                }
                R.id.radioButton2 -> {
                    paintAttrView.setShader(PaintAttrView.GRADIENT_RADIAL, true)
                }
                R.id.radioButton3 -> {
                    paintAttrView.setShader(PaintAttrView.GRADIENT_SWEEP, true)
                }
                R.id.radioButton4 -> {
                    paintAttrView.setShader(apply = false)
                }
            }
        }
    }

}
