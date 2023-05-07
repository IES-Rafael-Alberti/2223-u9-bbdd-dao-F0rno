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
    private fun calculateCtfsRankings(): MutableMap<Int, Int> {
        val rankingsMap = mutableMapOf<Int, Int>()
        val groups = grupoDAO.getAllGroups().obj
        val ctfs = ctfDAO.getAllCTFs().obj
        groups.forEach { group ->
            rankingsMap[group.grupoid] = ctfs.filter { ctf ->
                ctf.grupoId == group.grupoid }.maxByOrNull { ctf -> ctf.puntuacion }?.id ?: 0
        }
        return  rankingsMap
    }

    private fun setCtfsRankings(rankings: MutableMap<Int, Int>) {
        rankings.forEach { (grupoID, ctfID) ->
            grupoDAO.addMejorPosCtfId(grupoID, ctfID)
        }
    }

    private fun addGroupParticipationToCTF(args: List<String>): Result<String, Results> {
        val ctfid = args[0].toInt()
        val grupoId = args[1].toInt()
        val puntuacion = args[2].toInt()
        val ctf = Ctf(ctfid, grupoId, puntuacion)
        i("CTFsService.addGroupParticipationToCTF", "Adding participation to CTF")
        val grupoDesc = grupoDAO.getGroup(grupoId).obj.grupoDesc
        val rs = ctfDAO.addGroupParticipationInCTF(ctf)
        return when (rs.result) {
            Results.SUCCESSFUL -> Result(
                "Procesado: Añadida participación del grupo $grupoDesc en el CTF $ctfid con una puntuación de $puntuacion puntos.",
                Results.SUCCESSFUL
            )
            Results.FAILURE -> Result(
                "ERROR: Error al añadir la participación del grupo:$grupoDesc, en el CTF:$ctfid",
                Results.FAILURE
            )
        }
    }

    private fun removeGroupMembership(args: List<String>): Result<String, Results> {
        val ctfid = args[0].toInt()
        val grupoId = args[1].toInt()
        i("CTFsService.removeGroupMembership", "Removing participation of a group from a CTF")
        val grupoDesc = grupoDAO.getGroup(grupoId).obj.grupoDesc
        val rs = ctfDAO.deletGroupFromCTF(ctfid, grupoId)
        return when (rs.result) {
            Results.SUCCESSFUL -> Result(
                "Procesado: Eliminada participación del grupo $grupoDesc en el CTF $ctfid",
                Results.SUCCESSFUL
            )
            Results.FAILURE -> Result(
                "ERROR: Al eliminar la participación del grupo $grupoDesc en el CTF $ctfid",
                Results.FAILURE
            )
        }
    }

    private fun listGroups(args: List<String>): Result<List<String>, Results> {
        val groups = mutableListOf<String>()
        i("CTFsService.listGroups", "Listing groups info")
        when(args.size) {
            0 -> {
                grupoDAO.getAllGroups().obj.forEach { group ->
                    groups.add("GRUPO: ${group.grupoid}   ${group.grupoDesc}  MEJORCTF: ${group.mejorCtfId}")
                }
            }
            else -> {
                args.forEach { groupId ->
                    val group = grupoDAO.getGroup(groupId.toInt()).obj
                    groups.add("GRUPO: ${group.grupoid}   ${group.grupoDesc}  MEJORCTF: ${group.mejorCtfId}")
                }
            }
        }
        return Result(groups, Results.SUCCESSFUL)
    }

    fun executeArgs(args: Map<String, List<String>>): Result<String, Results> {
        var resultOfExecution: Result<String, Results> = Result("", Results.FAILURE)
        i("CTFsService.executeArgs", "Executing the args")

        if (args["-a"] != null) {
            resultOfExecution = addGroupParticipationToCTF(args["-a"]?: EMPTYLIST)
            setCtfsRankings(calculateCtfsRankings())
        }

        if (args["-d"] != null) {
            resultOfExecution = removeGroupMembership(args["-d"]?: EMPTYLIST)
            setCtfsRankings(calculateCtfsRankings())
        }

        if (args["-l"] != null || args["-l"] == emptyList<String>()) {
            resultOfExecution =  Result(listGroups(args["-l"]?: EMPTYLIST).obj.joinToString("\n"), Results.SUCCESSFUL)
        }
        return resultOfExecution
    }
}