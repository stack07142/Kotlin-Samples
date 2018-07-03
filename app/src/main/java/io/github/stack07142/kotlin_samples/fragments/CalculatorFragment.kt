package io.github.stack07142.kotlin_samples.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import io.github.stack07142.kotlin_samples.R
import kotlinx.android.synthetic.main.fragment_calculator.*

class CalculatorFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val numInputListener = View.OnClickListener {
            val inputText = (it as Button).text
            result_view.append(inputText)
        }

        val opInputListener = View.OnClickListener {
            val inputText = (it as Button).text
            val lastIdx = result_view.text.lastIndex
            val lastChar = result_view.text.last()

            if (isOperator(lastChar)) {
                result_view.text = result_view.text.replaceRange(lastIdx..lastIdx, inputText)
            } else {
                result_view.append(inputText)

            }
        }

        val calcListener = View.OnClickListener {
        }

        btn_0.setOnClickListener(numInputListener)
        btn_1.setOnClickListener(numInputListener)
        btn_2.setOnClickListener(numInputListener)
        btn_3.setOnClickListener(numInputListener)
        btn_4.setOnClickListener(numInputListener)
        btn_5.setOnClickListener(numInputListener)
        btn_6.setOnClickListener(numInputListener)
        btn_7.setOnClickListener(numInputListener)
        btn_8.setOnClickListener(numInputListener)
        btn_9.setOnClickListener(numInputListener)
        btn_dot.setOnClickListener(numInputListener)

        btn_plus.setOnClickListener(opInputListener)
        btn_minus.setOnClickListener(opInputListener)
        btn_multiply.setOnClickListener(opInputListener)
        btn_divide.setOnClickListener(opInputListener)
        btn_power.setOnClickListener(opInputListener)
        btn_pct.setOnClickListener(opInputListener)
        btn_root.setOnClickListener(opInputListener)
    }

    private fun checkValidity(): Boolean {
        return false
    }

    private fun update() {

    }

    private fun calc() {

    }

    private fun isOperator(c: Char): Boolean {
        return c !in '0'..'9'
    }
}