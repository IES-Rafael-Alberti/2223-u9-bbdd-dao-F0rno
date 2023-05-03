package railway
enum class Results {
    SUCCESSFUL,
    FAILURE
}
data class Result<T> (val result: Results, val obj: T)