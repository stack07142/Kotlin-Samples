package io.github.stack07142.kotlin_samples.calculator

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.stack07142.kotlin_samples.R
import timber.log.Timber

class CalculatorHistoryFragment : Fragment() {
    companion object {
        private const val ARG_HISTORY = "history"

        fun newInstance(history: List<String>?): CalculatorHistoryFragment {
            val args = Bundle()
            args.putStringArrayList(ARG_HISTORY, ArrayList(history))
            val fragment = CalculatorHistoryFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var history: List<String>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_calculator_history, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        history = arguments.getStringArrayList(ARG_HISTORY)
        Timber.d(history.toString())
    }
}