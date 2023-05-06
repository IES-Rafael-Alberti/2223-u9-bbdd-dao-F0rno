package args

import railway.Result
import railway.Results

val EMPTYLIST = emptyList<String>()

object ArgsParser {
    private fun argsIsValidInt(args: List<String>): Results {
        return try {
            args.forEach { arg -> arg.toInt() }
            Results.SUCCESSFUL
        } catch (e: Exception) { Results.FAILURE }
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
            val aParameterArgs = args["-a"]?: EMPTYLIST
            argsValidation = when (aParameterArgs.size) {
                3 -> when(argsIsValidInt(aParameterArgs)) {
                        Results.SUCCESSFUL -> Result("-a is valid", Results.SUCCESSFUL)
                        Results.FAILURE -> Result("-a args need to be numbers", Results.FAILURE)
                    }
                else -> Result("-a need 3 args", Results.FAILURE)
            }
        }

        if (args["-d"] != null) {
            val dParameterArgs = args["-d"]?: EMPTYLIST
            argsValidation = when (dParameterArgs.size) {
                2 -> when(argsIsValidInt(dParameterArgs)) {
                    Results.SUCCESSFUL -> Result("-d is valid", Results.SUCCESSFUL)
                    Results.FAILURE -> Result("-d args need to be numbers", Results.FAILURE)
                }
                else -> Result("-d need 2 args", Results.FAILURE)
            }
        }

        if (args["-l"] != null) {
            val lParameterArgs = args["-l"]?: EMPTYLIST
            argsValidation = when (lParameterArgs.size) {
                0 -> Result("-l all groups", Results.SUCCESSFUL)
                1 -> when(argsIsValidInt(lParameterArgs)) {
                    Results.SUCCESSFUL -> Result("-l is valid", Results.SUCCESSFUL)
                    Results.FAILURE -> Result("-l arg need to be a number", Results.FAILURE)
                }
                else -> Result("-l is not valid", Results.FAILURE)
            }
        }
        return argsValidation
    }
}