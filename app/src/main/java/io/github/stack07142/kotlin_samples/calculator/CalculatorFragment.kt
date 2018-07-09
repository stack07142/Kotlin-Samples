package io.github.stack07142.kotlin_samples.calculator

import android.app.Fragment
import android.os.Bundle
import android.view.*
import android.widget.Button
import io.github.stack07142.kotlin_samples.R
import io.github.stack07142.kotlin_samples.R.id.*
import kotlinx.android.synthetic.main.fragment_calculator.*
import timber.log.Timber

class CalculatorFragment : Fragment() {
    private lateinit var inputNumber: View.OnClickListener
    private lateinit var inputOperator: View.OnClickListener
    private lateinit var calc: View.OnClickListener
    private lateinit var delete: View.OnClickListener
    private lateinit var clear: View.OnLongClickListener
    private var isEditMode: Boolean = true

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)
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

            if (isEditMode) {
                result_view.append(inputText)
            } else {
                clear()
                result_view.append(inputText)
            }
        }

        inputOperator = View.OnClickListener {
            if (!result_view.text.isEmpty()) {
                val inputText = (it as Button).text
                val lastIdx = result_view.text.lastIndex
                val lastChar = result_view.text.last()

                if (!isLastNumber(lastChar)) {
                    if (isEditMode) {
                        result_view.text = result_view.text.replaceRange(lastIdx - 2..lastIdx, " $inputText ")
                    } else {
                        clear()
                    }
                } else {
                    result_view.append(" $inputText ")
                }
                isEditMode = true
            }
        }

        delete = View.OnClickListener {
            if (isEditMode) {
                result_view.text = result_view.text.dropLast(1)
            } else {
                clear()
            }
        }

        clear = View.OnLongClickListener {
            clear()
            true
        }

        calc = View.OnClickListener {
            isEditMode = false
            val expression = trim(result_view.text).split(" ")
            Timber.d("expression= $expression")
            val result = Calculator.calc(expression)
            Timber.d("result= $result")

            when {
                result.isInfinite() -> result_view.text = getString(R.string.inf)
                result.isNaN() -> result_view.text = getString(R.string.nan)
                isInteger(result) -> result_view.text = result.toInt().toString()
                else -> result_view.text = result.toString()
            }
        }
    }

    private fun isLastNumber(c: Char): Boolean {
        return c in '0'..'9'
    }

    private fun isInteger(num: Double): Boolean {
        return num % 1 == 0.0
    }

    private fun trim(expression: CharSequence): CharSequence {
        var trimmed = expression
        while (!isLastNumber(trimmed.last())) {
            trimmed = trimmed.dropLast(1)
        }
        return trimmed
    }

    private fun clear() {
        result_view.text = ""
        isEditMode = true
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
       return when(item?.itemId) {
            R.id.menu_history -> {
                activity.fragmentManager
                        .beginTransaction()
                        .addToBackStack(tag)
                        .replace(android.R.id.content, CalculatorHistoryFragment(), tag)
                        .commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}