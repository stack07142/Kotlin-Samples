package io.github.stack07142.kotlin_samples

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.stack07142.kotlin_samples.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(android.R.id.content, MainFragment())
                    .commit()
        }
    }
}
