package io.github.stack07142.kotlin_samples.fragments.courtcounter

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.stack07142.kotlin_samples.R
import kotlinx.android.synthetic.main.fragment_courtcounter.*

class CourtCounterFragment : Fragment() {
    private lateinit var viewModel: CourtCounterViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(CourtCounterViewModel::class.java)

        return inflater.inflate(R.layout.fragment_courtcounter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        displayScore()

        btn_three_a.setOnClickListener {
            viewModel.scoreTeamA += 3
            displayScore()
        }

        btn_three_b.setOnClickListener {
            viewModel.scoreTeamB += 3
            displayScore()
        }

        btn_two_a.setOnClickListener {
            viewModel.scoreTeamA += 2
            displayScore()
        }

        btn_two_b.setOnClickListener {
            viewModel.scoreTeamB += 2
            displayScore()
        }

        btn_free_a.setOnClickListener {
            viewModel.scoreTeamA += 1
            displayScore()
        }

        btn_free_b.setOnClickListener {
            viewModel.scoreTeamB += 1
            displayScore()
        }

        btn_reset.setOnClickListener {
            viewModel.scoreTeamA = 0
            viewModel.scoreTeamB = 0
            displayScore()
        }
    }

    fun displayScore() {
        tv_score_a.text = viewModel.scoreTeamA.toString()
        tv_score_b.text = viewModel.scoreTeamB.toString()
    }
}