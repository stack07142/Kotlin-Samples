package io.github.stack07142.kotlin_samples.calculator

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import io.github.stack07142.kotlin_samples.R
import kotlinx.android.synthetic.main.fragment_calculator.*

class CalculatorFragment : Fragment() {
    private lateinit var inputNumber: View.OnClickListener
    private lateinit var inputOperator: View.OnClickListener
    private lateinit var calc: View.OnClickListener
    private lateinit var delete: View.OnClickListener
    private lateinit var clear: View.OnLongClickListener

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initCalculator()

        btn_0.setOnClickListener(inputNumber)
        btn_1.setOnClickListener(inputNumber)
        btn_2.setOnClickListener(inputNumber)
        btn_3.setOnClickListener(inputNumber)
        btn_4.setOnClickListener(inputNumber)
        btn_5.setOnClickListener(inputNumber)
        btn_6.setOnClickListener(inputNumber)
        btn_7.setOnClickListener(inputNumber)
        btn_8.setOnClickListener(inputNumber)
        btn_9.setOnClickListener(inputNumber)
        btn_dot.setOnClickListener(inputNumber)
        btn_plus.setOnClickListener(inputOperator)
        btn_minus.setOnClickListener(inputOperator)
        btn_multiply.setOnClickListener(inputOperator)
        btn_divide.setOnClickListener(inputOperator)
        btn_calc.setOnClickListener(calc)
        btn_del.setOnClickListener(delete)
        btn_del.setOnLongClickListener(clear)
    }

    private fun initCalculator() {
        inputNumber = View.OnClickListener {
            val inputText = (it as Button).text
            result_view.append(inputText)
        }

        inputOperator = View.OnClickListener {
            val inputText = (it as Button).text
            val lastIdx = result_view.text.lastIndex
            val lastChar = result_view.text.last()

            if (isOperator(lastChar)) {
                result_view.text = result_view.text.replaceRange(lastIdx..lastIdx, inputText)
            } else {
                result_view.append(" $inputText ")
            }
        }

        delete = View.OnClickListener {
            result_view.text = result_view.text.dropLast(1)
        }

        clear = View.OnLongClickListener {
            result_view.text = ""
            true
        }

        calc = View.OnClickListener {
            val expression = result_view.text.split(" ")
            val result = Calculator.calc(expression)

            if (isInteger(result)) {
                result_view.text = result.toInt().toString()
            } else {
                result_view.text = result.toString()
            }
        }
    }

    private fun isOperator(c: Char): Boolean {
        return c !in '0'..'9'
    }

    private fun isInteger(num: Double): Boolean {
        return num % 1 == 0.0
    }
}