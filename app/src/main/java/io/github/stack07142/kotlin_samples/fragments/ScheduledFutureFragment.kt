package io.github.stack07142.kotlin_samples.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import io.github.stack07142.kotlin_samples.R
import org.apache.commons.lang3.concurrent.BasicThreadFactory
import timber.log.Timber
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.ThreadFactory
import java.util.concurrent.TimeUnit

class ScheduledFutureFragment : Fragment() {

    private var future: ScheduledFuture<*>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scheduled_future, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val executor = Executors.newSingleThreadScheduledExecutor(newFactory("alarms"))
        future = executor.scheduleAtFixedRate({
            try {
                Timber.d(">>>>>>>>>>>>>>>>>thread sleep-start")
                Thread.sleep(2000)
                Timber.d("<<<<<<<<<<<<<<<<<thread sleep-stop")
                Timber.d("future.isDone()= %s", if (future == null) "null" else future!!.isDone)
                Timber.d("future.isCancelled()= %s", if (future == null) "null" else future!!.isCancelled)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, 0L, 4L, TimeUnit.SECONDS)

        val btnCancel = activity?.findViewById<Button>(R.id.btn_future_cancel)
        btnCancel?.setOnClickListener { _ -> cancel() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cancel()
    }

    private fun newFactory(pattern: String): ThreadFactory {
        return BasicThreadFactory.Builder()
                .namingPattern("$pattern-%s")
                .build()
    }

    private fun cancel() {
        if (future != null) {
            Timber.d("clickListener:: future.cancel(false)= %s", future!!.cancel(false))
            Timber.d("clickListener:: future.isDone()= %s", future!!.isDone)
            Timber.d("clickListener:: future.isCancelled()= %s", future!!.isCancelled)
            future = null
        }
    }
}
