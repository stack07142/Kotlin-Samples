package io.github.stack07142.kotlin_samples.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.github.stack07142.kotlin_samples.R
import kotlinx.android.synthetic.main.fragment_looperthread.*
import timber.log.Timber

class LooperThreadFragment : Fragment() {
    private lateinit var looperThread: LooperThread

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_looperthread, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        looperThread = LooperThread()
        looperThread.start()

        btn_send_msg.setOnClickListener { _ ->
            looperThread.handler?.let {
                val msg = looperThread.handler?.obtainMessage(0)
                looperThread.handler?.sendMessage(msg)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        looperThread.handler?.looper?.quit()
    }

    private class LooperThread : Thread() {
        internal var handler: Handler? = null

        override fun run() {
            Looper.prepare()

            handler = @SuppressLint("HandlerLeak")
            object : Handler() {
                override fun handleMessage(msg: Message) {
                    if (msg.what == 0) {
                        doLongRunningOperation()
                    }
                }
            }

            Looper.loop()
        }

        private fun doLongRunningOperation() {
            Timber.tag("LooperThreadFragment").d("longRunningOperation")
        }
    }
}
