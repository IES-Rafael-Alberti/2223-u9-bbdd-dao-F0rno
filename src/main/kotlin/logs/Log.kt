package logs

import java.util.logging.Level
import java.util.logging.LogManager

internal val l = LogManager.getLogManager().getLogger("").apply { level = Level.INFO }
fun i(tag: String, msg: String) {
    l.info("[$tag] - $msg")
}