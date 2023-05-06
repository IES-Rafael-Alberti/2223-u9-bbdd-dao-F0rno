package service

import Ctf
import DAO.CtfDAO
import DAO.GrupoDAO
import logs.i
import railway.Result
import railway.Results

val EMPTYLIST = emptyList<String>()

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

    private fun addGroupParticipationToCTF(args: List<String>): Result<String, Results> {
        val ctfid = args[0].toInt()
        val grupoId = args[1].toInt()
        val puntuacion = args[2].toInt()
        val ctf = Ctf(ctfid, grupoId, puntuacion)
        i("CTFsService.addGroupParticipationToCTF", "Adding participation to CTF")
        val rs = ctfDAO.addGroupParticipationInCTF(ctf)
        return when (rs.result) {
            Results.SUCCESSFUL -> Result(
                "Procesado: Añadida participación del grupo $grupoId en el CTF $ctfid con una puntuación de $puntuacion puntos.",
                Results.SUCCESSFUL
            )
            Results.FAILURE -> Result(
                "ERROR: Error al añadir la participación del grupo:$grupoId, en el CTF:$ctfid",
                Results.FAILURE
            )
        }
    }

    fun executeArgs(args: Map<String, List<String>>): Result<String, Results> {
        var resultOfExecution: Result<String, Results> = Result("", Results.FAILURE)
        i("CTFsService.executeArgs", "Executing the args")
        if (args["-a"] != null) {
            resultOfExecution = addGroupParticipationToCTF(args["-a"]?: EMPTYLIST)
        }

        if (args["-d"] != null) {
            TODO("execute -d")
        }

        if (args["-l"] != null) {
            TODO("execute -l")
        }
        return resultOfExecution
    }
}