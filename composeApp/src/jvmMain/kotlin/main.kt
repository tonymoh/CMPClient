import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import io.ktor.client.engine.okhttp.*

import org.company.app.App
import org.company.app.createHttpClient
import java.awt.Dimension

fun main() = application {
    Window(
        title = "CMPClient",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(350, 600)
        App(client = remember { createHttpClient(OkHttp.create())})
    }
}