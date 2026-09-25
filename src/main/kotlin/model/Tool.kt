package cz.sg.model

import ai.koog.agents.core.tools.Tool
import ai.koog.serialization.typeToken

class ReadSourceCodeTool : Tool<FetchFileArgs, String>(
    argsType = typeToken<FetchFileArgs>(),
    resultType = typeToken<String>(),
    name = "read_source_code",
    description = "Reads the content of a project file given its relative file path."
) {
    override suspend fun execute(args: FetchFileArgs): String {
        return when (args.filePath) {
            "src/UserService.kt" -> """
                class UserService(private val repo: UserRepository) {
                    fun getUser(id: String): User {
                        // BUG: Null pointer when user is not found!
                        return repo.findById(id)!! 
                    }
                }
            """.trimIndent()
            else -> "Error: File not found at path ${args.filePath}"
        }
    }
}

class ReadLogsTool : Tool<FetchLogsArgs, String>(
    argsType = typeToken<FetchLogsArgs>(),
    resultType = typeToken<String>(),
    name = "read_system_logs",
    description = "Fetches recent error logs for a target service name."
) {
    override suspend fun execute(args: FetchLogsArgs): String {
        return """
            [2026-09-25 14:10:02] ERROR UserService - NullPointerException: repo.findById(...) returned null for ID user_9921
            [2026-09-25 14:10:03] WARN  RetryMechanism - Retrying attempt 1...
        """.trimIndent()
    }
}