package io.github.stack07142.kotlin_samples.calculator

import android.app.Fragment
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.stack07142.kotlin_samples.R

const val CALC_PREFERENCE = "CALC_PREFERENCE"
class CalculatorHistoryFragment: Fragment() {
    private lateinit var preference: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_calculator_history, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preference = activity.applicationContext.getSharedPreferences(CALC_PREFERENCE, MODE_PRIVATE)
    }
}