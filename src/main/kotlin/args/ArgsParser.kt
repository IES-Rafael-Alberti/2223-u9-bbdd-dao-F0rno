package args

import railway.Result
import railway.Results

val EMPTYLIST = emptyList<String>()

object ArgsParser {
    private fun <T> areAllItemsOfType(list: List<T>): Boolean {
        if (list.isEmpty()) return false
        val firstType = list.first()!!::class
        return list.all { it!!::class == firstType }
    }

    fun parse(args: Array<String>): Map<String, List<String>> {
        return args.fold(Pair(emptyMap<String, List<String>>(), "")) { (map, lastKey), elem ->
            if (elem.startsWith("-"))  Pair(map + (elem to emptyList()), elem)
            else Pair(map + (lastKey to map.getOrDefault(lastKey, emptyList()) + elem), lastKey)
        }.first
    }

    fun validateArguments(args: Map<String, List<String>>): Result<String, Results> {
        var argsValidation: Result<String, Results> = Result("", Results.SUCCESSFUL)

        if (args.keys.size != 1) {
            return Result("Only 1 argument", Results.FAILURE)
        }

        if (args["-a"] != null) {
            argsValidation = when (args["-a"]?.size) {
                3 -> Result("-a is valid", Results.SUCCESSFUL)
                else -> Result("-a need 3 args", Results.FAILURE)
            }
        }

        if (args["-d"] != null) {
            argsValidation = when (args["-d"]?.size) {
                2 -> Result("-d is valid", Results.SUCCESSFUL)
                else -> Result("-d need 2 args", Results.FAILURE)
            }
        }

        if (args["-l"] != null) {
            argsValidation = when (args["-l"]?.size) {
                0 -> Result("-l all groups", Results.SUCCESSFUL)
                1 -> Result("-l one group", Results.SUCCESSFUL)
                else -> Result("-l is not valid", Results.FAILURE)
            }
        }
        return argsValidation
    }
}