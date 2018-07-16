package io.github.stack07142.kotlin_samples.fragments.bluetooth

import android.app.Fragment
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.github.stack07142.kotlin_samples.R

class BTFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_ble, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val ap = BluetoothAdapter.getDefaultAdapter()
        if (ap == null) {
            Toast.makeText(activity, "bluetooth 를 지원하지 않는 기기입니다.", Toast.LENGTH_SHORT).show()
        }

        ap?.let {
            if (!it.isEnabled) {
                activity.startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 5)
            }
        }
    }
}