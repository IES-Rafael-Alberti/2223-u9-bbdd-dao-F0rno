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
    private fun calculateCtfsRankins() {
        val rankingsMap = mutableMapOf<Int, Int>()
        val grupos = grupoDAO.showAllGroups().obj
        val ctfs = ctfDAO.getAllCTFs().obj
        grupos.forEach { grupo ->
            rankingsMap[grupo.grupoid] = ctfs.filter { ctf ->
                ctf.grupoId == grupo.grupoid }.maxByOrNull { ctf -> ctf.puntuacion }?.id ?: 0
        }
        rankingsMap.forEach { (grupoID, ctfID) ->
            grupoDAO.addMejorPosCtfId(grupoID, ctfID)
        }
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

    private fun removeGroupMembership(args: List<String>): Result<String, Results> {
        val ctfid = args[0].toInt()
        val grupoId = args[1].toInt()
        i("CTFsService.removeGroupMembership", "Removing participation of a group from a CTF")
        val rs = ctfDAO.deletGroupFromCTF(ctfid, grupoId)
        return when (rs.result) {
            Results.SUCCESSFUL -> Result(
                "Procesado: Eliminada participación del grupo $grupoId en el CTF $ctfid",
                Results.SUCCESSFUL
            )
            Results.FAILURE -> Result(
                "ERROR: Al eliminar la participación del grupo $grupoId en el CTF $ctfid",
                Results.FAILURE
            )
        }
    }

    private fun listGroups() {

    }

    fun executeArgs(args: Map<String, List<String>>): Result<String, Results> {
        var resultOfExecution: Result<String, Results> = Result("", Results.FAILURE)
        i("CTFsService.executeArgs", "Executing the args")

        if (args["-a"] != null) {
            resultOfExecution = addGroupParticipationToCTF(args["-a"]?: EMPTYLIST)
            calculateCtfsRankins()
        }

        if (args["-d"] != null) {
            resultOfExecution = removeGroupMembership(args["-d"]?: EMPTYLIST)
            calculateCtfsRankins()
        }

        if (args["-l"] != null) {
            TODO("execute -l")
        }
        return resultOfExecution
    }
}