package cz.sg.model

import kotlinx.serialization.Serializable


@Serializable
data class FetchFileArgs(val filePath: String)

@Serializable
data class FetchLogsArgs(val serviceName: String, val lines: Int = 5)