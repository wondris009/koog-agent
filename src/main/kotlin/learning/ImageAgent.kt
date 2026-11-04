package cz.sg.learning

import ai.koog.prompt.dsl.prompt
import ai.koog.prompt.executor.clients.google.GoogleLLMClient
import ai.koog.prompt.executor.clients.google.GoogleModels
import cz.sg.common.getGoogleApiKey
import kotlinx.coroutines.runBlocking
import kotlinx.io.files.Path

fun main() = runBlocking {
    val client = GoogleLLMClient(getGoogleApiKey())

    val prompt = prompt("image_prompt") {
        user {
            +"Analyze the picture and tell me what is in it."
            image(Path(System.getProperty("user.home"), "Downloads", "qrcode.png"))
        }
    }

    val response = client.execute(prompt, GoogleModels.Gemini3_6Flash)
    println(response)
}

