package io.github.stack07142.kotlin_samples

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.github.stack07142.kotlin_samples.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(android.R.id.content, MainFragment())
                    .commit()
        }
    }
}
