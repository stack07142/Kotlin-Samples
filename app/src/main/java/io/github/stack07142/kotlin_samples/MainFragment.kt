package io.github.stack07142.kotlin_samples

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.stack07142.kotlin_samples.fragments.*
import io.github.stack07142.kotlin_samples.fragments.bluetooth.BTFragment
import io.github.stack07142.kotlin_samples.fragments.calculator.CalculatorFragment
import io.github.stack07142.kotlin_samples.fragments.courtcounter.CourtCounterFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_calculator.setOnClickListener { onClick(CalculatorFragment()) }
        btn_thread_comm_pipe.setOnClickListener { onClick(PipeFragment()) }
        btn_java_test.setOnClickListener { onClick(ScheduledFutureFragment()) }
        btn_ble.setOnClickListener { onClick(BTFragment()) }
        btn_variance.setOnClickListener { onClick(VarianceFragment()) }
        btn_inline.setOnClickListener { onClick(InlineFragment()) }
        btn_dagger.setOnClickListener { onClick(CoffeeMakerFragment()) }
        btn_courtcounter.setOnClickListener { onClick(CourtCounterFragment()) }
    }

    private fun onClick(fragment: Fragment) {
        activity!!.supportFragmentManager
                .beginTransaction()
                .addToBackStack(tag)
                .replace(android.R.id.content, fragment, tag)
                .commit()
    }
}