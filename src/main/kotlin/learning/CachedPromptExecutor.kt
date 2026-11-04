package cz.sg.learning

import ai.koog.prompt.cache.files.FilePromptCache
import ai.koog.prompt.dsl.prompt
import ai.koog.prompt.executor.cached.CachedPromptExecutor
import ai.koog.prompt.executor.clients.google.GoogleLLMClient
import ai.koog.prompt.executor.clients.google.GoogleModels
import ai.koog.prompt.executor.llms.MultiLLMPromptExecutor
import ai.koog.prompt.message.MessagePart
import cz.sg.common.getGoogleApiKey
import kotlinx.coroutines.runBlocking
import java.nio.file.Path
import kotlin.system.measureTimeMillis

fun main() = runBlocking {

    val client = GoogleLLMClient(getGoogleApiKey())
    val promptExecutor = MultiLLMPromptExecutor(client)

    val cachedExecutor = CachedPromptExecutor(
        cache = FilePromptCache(Path.of(System.getProperty("user.home"), "tmp", "AI", "cached-prompt")),
        nested = promptExecutor
    )

    val prompt = prompt("my_prompt") {
        user("Say hello for mr :)")
    }

    val firstTime = measureTimeMillis {
        val firstResponse = cachedExecutor.execute(prompt, GoogleModels.Gemini3_6Flash)
        val text = firstResponse.parts.filterIsInstance<MessagePart.Text>().joinToString("\n") { it.text }
        println("First response: $text")
    }
    println("First execution took: ${firstTime}ms")

    val secondTime = measureTimeMillis {
        val secondResponse = cachedExecutor.execute(prompt, GoogleModels.Gemini3_6Flash)
        val text = secondResponse.parts.filterIsInstance<MessagePart.Text>().joinToString("\n") { it.text }
        println("Second response: $text")
    }
    println("Second execution took: ${secondTime}ms")
}

