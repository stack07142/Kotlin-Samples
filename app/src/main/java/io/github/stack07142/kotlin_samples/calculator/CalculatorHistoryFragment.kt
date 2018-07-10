package io.github.stack07142.kotlin_samples.calculator

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.stack07142.kotlin_samples.R
import kotlinx.android.synthetic.main.fragment_calculator_history.*

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
        rv_calc_history.adapter = HistoryRVAdapter(activity, history)
    }

    class HistoryRVAdapter(val context: Context, val history: List<String>?): RecyclerView.Adapter<HistoryItemVH>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemVH {
            return HistoryItemVH(LayoutInflater.from(context).inflate(R.layout.item_history, parent, false))
        }

        override fun onBindViewHolder(holder: HistoryItemVH, position: Int) {
            holder.textView.text = history?.get(position)
        }

        override fun getItemCount(): Int {
            return history?.size ?: 0
        }
    }

    class HistoryItemVH(view: View) : RecyclerView.ViewHolder(view) {
        val textView = view as TextView
    }
}