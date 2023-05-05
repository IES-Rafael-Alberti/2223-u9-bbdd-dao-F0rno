import DAO.GrupoDAOH2
import args.ArgsParser
import dataBase.DataBaseChecker
import dataBase.DataBaseMaker
import dataBase.Tables
import dataSource.DataSourceFactory
import dataSource.DataSourceType
import railway.Results

fun main(args: Array<String>) {

    val parseArgs = ArgsParser.parse(args)
    println(parseArgs)

    /*
    val myDBChecker = DataBaseChecker(DataSourceFactory)
    if (myDBChecker.exitsTheDB(DataSourceType.HIKARI) == Results.SUCCESSFUL) {
        val myDataSource = DataSourceFactory.getDS(DataSourceType.HIKARI)
        val myDataBaseMaker = DataBaseMaker(myDataSource)
        val myGrupoDAO = GrupoDAOH2(myDataSource)
        Tables.values().forEach {
            if (myDBChecker.exitsThisTable(it.toString()).result == Results.SUCCESSFUL) {
                if (it == Tables.GRUPOS) {
                    val rs = myGrupoDAO.addMejorPosCtfId(1, 0)
                    if (rs == Results.SUCCESSFUL) println("Done")
                }
            } else {
                myDataBaseMaker.createTable(it)
            }
        }
    } else {
        println("Cant connecto to the data base")
    }
    
     */

    /*
    if (myDBChecker.exitsTheDB().result == Results.SUCCESSFUL) {
        Tables.values().forEach { table ->
            val rs = myDBChecker.exitsThisTable(table.toString())
            when (rs.result) {
                Results.SUCCESSFUL -> println("La tabla ${table.toString()} existe")
                Results.FAILURE -> myDataBaseMaker.createTable(table)
            }
        }
    } else {
        println(2)
    }
     */
    /*
    if (argsMap.keys.size != 1) {
        TODO("1 parameter")
    }

    if (argsMap["-a"] != null) {
        when (argsMap["-a"]?.size) {
            3 -> TODO("Funcionality for -a")
            else -> TODO("Error empty args -a")
        }
    }

    if (argsMap["-d"] != null) {
        when (argsMap["-d"]?.size) {
            2 -> TODO("Funcionality for -d")
            else -> TODO("Error empty args -d")
        }
    }

    if (argsMap["-l"] != null) {
        when (argsMap["-l"]?.size) {
            0 -> TODO("Funcionality for -l empty (for all groups)")
            1 -> TODO("Funcionality for -l one group")
            else -> TODO("Error empty args -l")
        }
    }
    
    i("main", "funcionan los logs")


     */
    /*
    val participaciones = listOf(Ctf(1, 1, 3), Ctf(1, 2, 101), Ctf(2, 2, 3), Ctf(2, 1, 50), Ctf(2, 3, 1), Ctf(3, 1, 50), Ctf(3, 3, 5))
    val mejoresCtfByGroupId = calculaMejoresResultados(participaciones)
    println(mejoresCtfByGroupId)
     */
}

/**
 * TODO
 *
 * @param participaciones
 * @return devuelve un mutableMapOf<Int, Pair<Int, Ctf>> donde
 *      Key: el grupoId del grupo
 *      Pair:
 *          first: Mejor posición
 *          second: Objeto CTF el que mejor ha quedado
 */
private fun calculaMejoresResultados(participaciones: List<Ctf>): MutableMap<Int, Pair<Int, Ctf>> {
    val participacionesByCTFId = participaciones.groupBy { it.id }
    val participacionesByGrupoId = participaciones.groupBy { it.grupoId }
    val mejoresCtfByGroupId = mutableMapOf<Int, Pair<Int, Ctf>>()
    participacionesByCTFId.values.forEach { ctfs ->
        val ctfsOrderByPuntuacion = ctfs.sortedBy { it.puntuacion }.reversed()
        participacionesByGrupoId.keys.forEach { grupoId ->
            val posicionNueva = ctfsOrderByPuntuacion.indexOfFirst { it.grupoId == grupoId }
            if (posicionNueva >= 0) {
                val posicionMejor = mejoresCtfByGroupId.getOrDefault(grupoId, null)
                if (posicionMejor != null) {
                    if (posicionNueva < posicionMejor.first)
                        mejoresCtfByGroupId.set(grupoId, Pair(posicionNueva, ctfsOrderByPuntuacion.get(posicionNueva)))
                } else
                    mejoresCtfByGroupId.set(grupoId, Pair(posicionNueva, ctfsOrderByPuntuacion.get(posicionNueva)))

            }
        }
    }
    return mejoresCtfByGroupId
}