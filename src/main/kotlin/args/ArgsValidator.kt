package args

import logs.i
import railway.Result
import railway.Results

private val EMPTYLIST = emptyList<String>()

private const val ARGS_3 = 3
private const val ARGS_2 = 2
private const val ARGS_1 = 1
private const val ARGS_0 = 0

/**
 * Checks if parse arguments are valid
 */
object ArgsValidator {
    /**
     * Checks if the args are numbers
     */
    private fun argsAreValidsInt(args: List<String>): Results {
        return try {
            args.forEach { arg -> arg.toInt() }
            Results.SUCCESSFUL
        } catch (e: Exception) { Results.FAILURE }
    }

    /**
     * Checks if the args meet the requirements
     */
    fun validateArguments(args: Map<String, List<String>>): Result<String, Results> {
        var argsValidation: Result<String, Results> = Result("", Results.SUCCESSFUL)

        if (args.keys.size != ARGS_1) {
            return Result("Only 1 argument", Results.FAILURE)
        }

        i("ArgsParser.validateArguments", "Validating arguments")
        if (args["-a"] != null) {
            val aParameterArgs = args["-a"]?: EMPTYLIST
            argsValidation = when (aParameterArgs.size) {
                ARGS_3 -> when(argsAreValidsInt(aParameterArgs)) {
                    Results.SUCCESSFUL -> Result("-a is valid", Results.SUCCESSFUL)
                    Results.FAILURE -> Result("-a args need to be numbers", Results.FAILURE)
                }
                else -> Result("-a need 3 args", Results.FAILURE)
            }
        }

        if (args["-d"] != null) {
            val dParameterArgs = args["-d"]?: EMPTYLIST
            argsValidation = when (dParameterArgs.size) {
                ARGS_2 -> when(argsAreValidsInt(dParameterArgs)) {
                    Results.SUCCESSFUL -> Result("-d is valid", Results.SUCCESSFUL)
                    Results.FAILURE -> Result("-d args need to be numbers", Results.FAILURE)
                }
                else -> Result("-d need 2 args", Results.FAILURE)
            }
        }

        if (args["-l"] != null) {
            val lParameterArgs = args["-l"]?: EMPTYLIST
            argsValidation = when (lParameterArgs.size) {
                ARGS_0 -> Result("-l all groups", Results.SUCCESSFUL)
                ARGS_1 -> when(argsAreValidsInt(lParameterArgs)) {
                    Results.SUCCESSFUL -> Result("-l is valid", Results.SUCCESSFUL)
                    Results.FAILURE -> Result("-l arg need to be a number", Results.FAILURE)
                }
                else -> Result("-l is not valid", Results.FAILURE)
            }
        }
        return argsValidation
    }
}