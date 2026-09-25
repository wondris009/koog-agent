package cz.sg

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import ai.koog.agents.core.agent.AIAgent
import ai.koog.agents.core.tools.ToolRegistry
import ai.koog.prompt.executor.clients.google.GoogleLLMClient
import ai.koog.prompt.executor.clients.google.GoogleModels
import ai.koog.prompt.executor.llms.MultiLLMPromptExecutor
import cz.sg.model.ReadLogsTool
import cz.sg.model.ReadSourceCodeTool
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val apiKey = System.getenv("GEMINI_API_KEY")
        ?: System.getenv("GOOGLE_API_KEY")
        ?: error("GEMINI_API_KEY environment variable missing")

    // Initialize the prompt executor
    val executor = MultiLLMPromptExecutor(GoogleLLMClient(apiKey))

    // Build the AI Agent
    val bugFixAgent = AIAgent(
        promptExecutor = executor,
        llmModel = GoogleModels.Gemini2_5Flash,
        systemPrompt = """
            You are a Senior Kotlin Staff Engineer debugging production issues.
            1. Always gather facts by using your available tools (logs, code inspection) before jumping to conclusions.
            2. Formulate a hypothesis and propose a clean, safe fix using idiomatic Kotlin (e.g., handling nullability properly).
        """.trimIndent(),
        toolRegistry = ToolRegistry {
            tool(ReadSourceCodeTool())
            tool(ReadLogsTool())
        }
    )

    val userQuery = "Users are receiving 500 errors on the profile page. Service name is 'UserService'. Can you figure out what's wrong and fix it?"

    println("=== Agent Execution Started ===")

    // Run agent with user prompt
    val response = bugFixAgent.run(userQuery)

    println("\n=== Final Agent Response ===")
    println(response)
}