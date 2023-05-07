package args

import logs.i
import railway.Result
import railway.Results

/**
 * Parses the input data from the program to be processed
 */
object ArgsParser {
    /**
     * Parses the args
     */
    fun parse(args: Array<String>): Map<String, List<String>> {
        i("ArgsParser.parse", "Parsing raw args")
        return args.fold(Pair(emptyMap<String, List<String>>(), "")) { (map, lastKey), elem ->
            if (elem.startsWith("-"))  Pair(map + (elem to emptyList()), elem)
            else Pair(map + (lastKey to map.getOrDefault(lastKey, emptyList()) + elem), lastKey)
        }.first
    }
}