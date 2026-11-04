package cz.sg.learning

import ai.koog.agents.core.agent.AIAgent
import ai.koog.prompt.executor.clients.google.GoogleModels
import ai.koog.prompt.executor.llms.all.simpleGoogleAIExecutor
import cz.sg.common.getGoogleApiKey
import kotlinx.coroutines.runBlocking
import java.util.Scanner

fun main() = runBlocking {
    val translatorAgent = AIAgent(
        promptExecutor = simpleGoogleAIExecutor(getGoogleApiKey()),
        llmModel = GoogleModels.Gemini3_6Flash,
        systemPrompt = """
            You are a precise, natural-sounding English to German translator. 
            Translate any user input directly into German. 
            Do not include conversational filler—only output the translated German text.
        """.trimIndent()
    )

    println("=== Koog German Translator (Gemini) ===")
    println("Type text to translate, or type '/exit' to quit.\n")

    val scanner = Scanner(System.`in`)

    while (true) {
        print("> Input: ")
        System.out.flush()
        if (!scanner.hasNextLine()) break
        val input = scanner.nextLine().trim()

        if (input.equals("/exit", ignoreCase = true)) {
            println("Exiting translator. Goodbye!")
            break
        }

        if (input.isEmpty()) continue

        try {
            val response = translatorAgent.run(input)
            println(" German: $response\n")
        } catch (e: Exception) {
            println("Error during translation: ${e.message}\n")
        }
    }
}
