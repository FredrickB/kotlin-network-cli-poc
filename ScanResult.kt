import java.time.LocalDateTime

data class ScanResult(
        val ipAddress: String,
        val up: Boolean,
        val scannedAt: LocalDateTime
)
