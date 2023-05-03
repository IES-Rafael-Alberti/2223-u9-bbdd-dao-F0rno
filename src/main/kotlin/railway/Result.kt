package railway
enum class Results {
    SUCCESSFUL,
    FAILURE
}
data class Result<T, U> (val obj: T, val result: Results)