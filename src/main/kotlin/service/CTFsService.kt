package service

import DAO.CtfDAO
import DAO.GrupoDAO

class CTFsService(
    private val ctfDAO: CtfDAO,
    private val grupoDAO: GrupoDAO
) {
    fun calculateCtfsRankins(): Map<Int, Int> {
        val rankingsMap = mutableMapOf<Int, Int>()
        val grupos = grupoDAO.showAllGroups().obj
        val ctfs = ctfDAO.getAllCTFs().obj
        grupos.forEach { grupo ->
            rankingsMap[grupo.grupoid] = ctfs.filter { ctf ->
                ctf.grupoId == grupo.grupoid }.maxByOrNull { ctf -> ctf.puntuacion }?.id ?: 0
        }
        return rankingsMap
    }

    fun executeArgs(args: Map<String, List<String>>) {
        if (args["-a"] != null) {
            TODO("execute -a")
        }

        if (args["-d"] != null) {
            TODO("execute -d")
        }

        if (args["-l"] != null) {
            TODO("execute -l")
        }
    }
}