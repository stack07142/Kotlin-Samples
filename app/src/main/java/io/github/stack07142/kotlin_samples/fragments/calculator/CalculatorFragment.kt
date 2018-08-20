package io.github.stack07142.kotlin_samples.fragments.calculator

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.NotificationCompat
import android.support.v4.content.LocalBroadcastManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import io.github.stack07142.kotlin_samples.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_calculator.*
import timber.log.Timber
import java.util.concurrent.TimeUnit


/**
 * Calculator
 * LocalBroadcast
 * Local Notification
 * Unit Test
 * UI Test
 *
 * Kotlin
 * - When expression
 * - lateinit modifier: lateinit allows initializing a non-null property outside of a constructor
 * - Safety calls ?.
 * - as: unsafe type cast
 * - object expression: create an object of an anonymous class that inherits from some type
 * - Range expression
 */

const val SAVE_ACTION = "SAVE_ACTION"
const val CALC_RESULT = "CALC_RESULT"
const val CHANNEL_NAME = "CHANNEL_NAME"
const val CHANNEL_ID = "CHANNEL_ID"

class CalculatorFragment : Fragment() {
    private lateinit var inputNumber: View.OnClickListener
    private lateinit var inputOperator: View.OnClickListener
    private lateinit var calc: View.OnClickListener
    private lateinit var delete: View.OnClickListener
    private lateinit var clear: View.OnLongClickListener
    private var isEditMode: Boolean = true
    private lateinit var broadcastReceiver: BroadcastReceiver
    private var disposable: Disposable? = null
    private var incrementalNotiId = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)
        initCalculator()

        btn_0.setOnClickListener(inputNumber)
        btn_1.setOnClickListener(inputNumber)
        btn_2.setOnClickListener(inputNumber)
        btn_3.setOnClickListener(inputNumber)
        btn_4.setOnClickListener(inputNumber)
        btn_5.setOnClickListener(inputNumber)
        btn_6.setOnClickListener(inputNumber)
        btn_7.setOnClickListener(inputNumber)
        btn_8.setOnClickListener(inputNumber)
        btn_9.setOnClickListener(inputNumber)
        btn_dot.setOnClickListener(inputNumber)
        btn_plus.setOnClickListener(inputOperator)
        btn_minus.setOnClickListener(inputOperator)
        btn_multiply.setOnClickListener(inputOperator)
        btn_divide.setOnClickListener(inputOperator)
        btn_calc.setOnClickListener(calc)
        btn_del.setOnClickListener(delete)
        btn_del.setOnLongClickListener(clear)

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Timber.d("onReceive:: ${intent?.getStringExtra(CALC_RESULT)}")
            }
        }
        LocalBroadcastManager.getInstance(activity!!).registerReceiver(broadcastReceiver, IntentFilter(SAVE_ACTION))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable?.dispose()
        LocalBroadcastManager.getInstance(activity!!).unregisterReceiver(broadcastReceiver)
    }

    private fun initCalculator() {
        inputNumber = View.OnClickListener {
            val inputText = (it as Button).text

            if (isEditMode) {
                result_view.append(inputText)
            } else {
                clear()
                result_view.append(inputText)
            }
        }

        inputOperator = View.OnClickListener {
            if (!result_view.text.isEmpty()) {
                val inputText = (it as Button).text
                val lastIdx = result_view.text.lastIndex
                val lastChar = result_view.text.last()

                if (!isLastNumber(lastChar)) {
                    if (isEditMode) {
                        result_view.text = result_view.text.replaceRange(lastIdx - 2..lastIdx, " $inputText ")
                    } else {
                        clear()
                    }
                } else {
                    result_view.append(" $inputText ")
                }
                isEditMode = true
            }
        }

        delete = View.OnClickListener {
            if (isEditMode) {
                result_view.text = result_view.text.dropLast(1)
            } else {
                clear()
            }
        }

        clear = View.OnLongClickListener {
            clear()
            true
        }

        calc = View.OnClickListener {
            isEditMode = false
            var expression = result_view.text.toString()
            val trimmedExpression = trim(result_view.text).split(" ")
            Timber.d("expression= $trimmedExpression")

            val result = Calculator.calc(trimmedExpression)
            Timber.d("result= $result")

            val refinedResult = when {
                result.isInfinite() -> getString(R.string.inf)
                result.isNaN() -> getString(R.string.nan)
                isInteger(result) -> result.toInt().toString()
                else -> result.toString()
            }
            result_view.text = refinedResult

            expression += " = $refinedResult"
            longOperation(expression)
        }
    }

    private fun longOperation(msg: String) {
        disposable = Observable.just(true)
                .subscribeOn(Schedulers.newThread())
                .delay(2000, TimeUnit.MILLISECONDS)
                .ignoreElements()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            sendNotification(msg)

                            val intent = Intent()
                            intent.action = SAVE_ACTION
                            intent.putExtra(CALC_RESULT, "longOperation-onComplete")
                            LocalBroadcastManager.getInstance(activity!!).sendBroadcast(intent)
                        },
                        { _ -> Timber.d("onError") }
                )
    }

    private fun sendNotification(msg: String) {
        val notificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                    CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        //val resultIntent = Intent(activity, MainActivity::class.java)
        //val stackBuilder = TaskStackBuilder.create(activity)
        //stackBuilder.addParentStack(MainActivity::class.java)
        //stackBuilder.addNextIntent(resultIntent)
        //val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(activity!!, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(msg)
        //.setContentIntent(resultPendingIntent)
        //.setAutoCancel(true)

        notificationManager.notify(incrementalNotiId++, builder.build())
    }

    private fun isLastNumber(c: Char): Boolean {
        return c in '0'..'9'
    }

    private fun isInteger(num: Double): Boolean {
        return num % 1 == 0.0
    }

    private fun trim(expression: CharSequence): CharSequence {
        var trimmed = expression
        while (!isLastNumber(trimmed.last())) {
            trimmed = trimmed.dropLast(1)
        }
        return trimmed
    }

    private fun clear() {
        result_view.text = ""
        isEditMode = true
    }
}