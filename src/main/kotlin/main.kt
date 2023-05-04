import DAO.CtfDAOH2
import DAO.GrupoDAO
import DAO.GrupoDAOH2
import dataSource.DataSourceFactory
import logs.i

fun main(args: Array<String>) {
    val argsMap = args.fold(Pair(emptyMap<String, List<String>>(), "")) { (map, lastKey), elem ->
        if (elem.startsWith("-"))  Pair(map + (elem to emptyList()), elem)
        else Pair(map + (lastKey to map.getOrDefault(lastKey, emptyList()) + elem), lastKey)
    }.first

    val myDataSource = DataSourceFactory.getDS(DataSourceFactory.DataSourceType.HIKARI)
    val myCtfDAO = CtfDAOH2(myDataSource)
    val myGrupoDAO = GrupoDAOH2(myDataSource)

    myDataSource.connection.use { conn ->
        val dbName = "d"
        val rs = conn.metaData.getTables(null, null, dbName, null)
        if (rs.next()) {
            println("La base de datos $dbName existe.")
        } else {
            println("La base de datos $dbName no existe.")
        }
    }

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