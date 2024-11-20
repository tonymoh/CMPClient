package org.company.app

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import cmpclient.composeapp.generated.resources.IndieFlower_Regular
import cmpclient.composeapp.generated.resources.Res
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.company.app.theme.AppTheme
import org.jetbrains.compose.resources.Font

@Composable
internal fun App(client: HttpClient) = AppTheme {
    // Remember coroutine scope for asynchronous operations
    val coroutineScope = rememberCoroutineScope()

    // Mutable state to store wisdom fact
    var wisdom by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Cat Wisdom",
            fontFamily = FontFamily(Font(Res.font.IndieFlower_Regular)),
            style = MaterialTheme.typography.displayLarge
        )
        // Button to fetch new wisdom
        Button(onClick = {
            coroutineScope.launch {
                // Fetch wisdom from API when button is clicked
                wisdom = clientTest(client)
            }
        }
        ) {
            Text("Get Wisdom")
        }

        Text(
            text = wisdom,
            modifier = Modifier.padding(top = 16.dp),
            fontFamily = FontFamily(Font(Res.font.IndieFlower_Regular)),
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

/**
 * Creates an HttpClient instance with logging and JSON content negotiation
 */
fun createHttpClient(engine: HttpClientEngine): HttpClient {
    return HttpClient(engine) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                }
            )
        }

    }
}

/**
 * Fetches a random cat fact from the API
 */
suspend fun clientTest(client: HttpClient): String {
    val resp = client.get(urlString = "https://catfact.ninja/fact")
    val dto = resp.body<CatWisdom>()
    return dto.fact
}

/**
 * Data class representing a cat wisdom fact
 */
@Serializable
data class CatWisdom(
    @Serializable
    val fact: String
)