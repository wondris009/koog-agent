package cz.sg.common

fun getGoogleApiKey(): String {
    return System.getenv("GEMINI_API_KEY")?.takeIf { it.isNotBlank() }
        ?: error(
            """
            Gemini API key is missing! Please set the GEMINI_API_KEY (or GOOGLE_API_KEY) environment variable.
            Example: export GEMINI_API_KEY="your-api-key"
            """.trimIndent()
        )
}