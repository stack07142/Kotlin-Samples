package io.github.stack07142.kotlin_samples

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(android.R.id.content, MainFragment())
                    .commit()
        }

        /*
        val links: MutableMap<String, String> = mutableMapOf()
        links["a"] = "b"
        links["b"] = "c"
        links["c"] = "d"

        val answer = links.getOrDefault("d", "deffff")
        Timber.d("answer= $answer")
        */
    }
}
