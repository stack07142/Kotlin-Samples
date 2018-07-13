package io.github.stack07142.kotlin_samples

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.stack07142.kotlin_samples.R.id.btn_calculator
import io.github.stack07142.kotlin_samples.R.id.btn_thread_comm_pipe
import io.github.stack07142.kotlin_samples.fragments.calculator.CalculatorFragment
import io.github.stack07142.kotlin_samples.fragments.PipeFragment
import io.github.stack07142.kotlin_samples.fragments.ScheduledFutureFragment

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_calculator.setOnClickListener { onClick(CalculatorFragment()) }
        btn_thread_comm_pipe.setOnClickListener { onClick(PipeFragment()) }
        btn_java_test.setOnClickListener { startActivity(Intent(activity, ScheduledFutureFragment::class.java)) }
    }

    private fun onClick(fragment: Fragment) {
        activity.fragmentManager
                .beginTransaction()
                .addToBackStack(tag)
                .replace(android.R.id.content, fragment, tag)
                .commit()
    }
}