package org.company.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import io.ktor.client.engine.okhttp.*

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // https://googlesamples.github.io/android-custom-lint-rules/checks/RememberReturnType.md.html
        // @Suppress("RememberReturnType")
        enableEdgeToEdge()
        setContent {
            App(
                client = remember
                {
                    createHttpClient(OkHttp.create())
                }
            )
        }
    }
}

