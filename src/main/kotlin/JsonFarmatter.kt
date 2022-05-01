import kotlinx.serialization.json.Json

object JsonFarmatter {
    val farmatter = Json{
        // encode
        encodeDefaults = true
        // decode
        ignoreUnknownKeys = true
        explicitNulls = false
    }
}