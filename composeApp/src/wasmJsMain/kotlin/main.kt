import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import org.company.app.App
import kotlinx.browser.document
import io.ktor.client.engine.js.JsClient
import org.company.app.createHttpClient

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val body = document.body ?: return
    ComposeViewport(body) {
        App(client = remember { createHttpClient(JsClient().create()) })
    }
}
