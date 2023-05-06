import DAO.CtfDAOH2
import DAO.GrupoDAO
import DAO.GrupoDAOH2
import args.ArgsParser
import dataBase.DataBaseChecker
import dataBase.DataBaseMaker
import dataBase.Tables
import dataSource.DataSourceFactory
import dataSource.DataSourceType
import railway.Results
import service.CTFsService
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val parseArgs = ArgsParser.parse(args)
    val parseResult =  ArgsParser.validateArguments(parseArgs)

    if (parseResult.result == Results.FAILURE) {
        println(parseResult.obj)
        exitProcess(1)
    }

    val dataBaseChecker = DataBaseChecker(DataSourceFactory)

    if (dataBaseChecker.exitsTheDB(DataSourceType.HIKARI) == Results.FAILURE) {
        println("No se puede connectar con la base de datos")
        exitProcess(1)
    }

    val dataSourcer = DataSourceFactory.getDS(DataSourceType.HIKARI)
    val dataBaseMaker = DataBaseMaker(dataSourcer)

    Tables.values().forEach { table ->
        val rs = dataBaseChecker.exitsThisTable(table.toString())
        if (rs.result == Results.FAILURE) {
            dataBaseMaker.createTable(table)
        }
    }

    val ctfDAO = CtfDAOH2(dataSourcer)
    val grupoDAO = GrupoDAOH2(dataSourcer)
    val service = CTFsService(ctfDAO, grupoDAO)

    println(service.executeArgs(parseArgs).obj)


    /*
    service.calculateCtfsRankins().forEach { (grupoID, ctfID) ->
        grupoDAO.addMejorPosCtfId(grupoID, ctfID)
    }

     */



    /*
    val participaciones = listOf(Ctf(1, 1, 3), Ctf(1, 2, 101), Ctf(2, 2, 3), Ctf(2, 1, 50), Ctf(2, 3, 1), Ctf(3, 1, 50), Ctf(3, 3, 5))
    val mejoresCtfByGroupId = calculaMejoresResultados(participaciones)
    println(mejoresCtfByGroupId)
     */
}