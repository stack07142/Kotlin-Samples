package io.github.stack07142.kotlin_samples.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.stack07142.kotlin_samples.R
import kotlinx.android.synthetic.main.fragment_pipe.*
import timber.log.Timber
import java.io.IOException
import java.io.PipedReader
import java.io.PipedWriter

class PipeFragment : Fragment() {
    private lateinit var pipedReader: PipedReader
    private lateinit var pipedWriter: PipedWriter

    private lateinit var workerThread: Thread

    // 1. setupPipe
    // 2. data transfer
    // 3. disconnection
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pipe, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupPipe()

        btn_flush.setOnClickListener {
            val str = editText.text
            Timber.tag("PipeFragment").d("flush= $str")

            pipedWriter.write(str.toString())
            pipedWriter.flush()
        }

        workerThread = Thread(TextHandlerTask())
        workerThread.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disconnectPipe()
    }

    private fun setupPipe() {
        pipedReader = PipedReader()
        pipedWriter = PipedWriter()

        try {
            pipedWriter.connect(pipedReader)
            pipedWriter.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun disconnectPipe() {
        workerThread.interrupt()
        try {
            pipedReader.close()
            pipedWriter.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    inner class TextHandlerTask : Runnable {
        override fun run() {
            while (!Thread.currentThread().isInterrupted) {
                try {
                    var i: Int = -1
                    while ({ i = pipedReader.read(); i }() != -1) {
                        Timber.tag("PipeFragment").d("TextHandlerTask: ${i.toChar()}")
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}