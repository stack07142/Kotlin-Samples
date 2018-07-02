package io.github.stack07142.kotlin_samples.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.stack07142.kotlin_samples.R
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_calculator.setOnClickListener { onClick(CalculatorFragment()) }
    }

    private fun onClick(fragment: Fragment) {
        activity.fragmentManager
                .beginTransaction()
                .addToBackStack(tag)
                .replace(android.R.id.content, fragment, tag)
                .commit()
    }
}