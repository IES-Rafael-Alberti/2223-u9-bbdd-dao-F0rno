import DAO.CtfDAOH2
import DAO.GrupoDAOH2
import args.ArgsParser
import dataBase.DataBaseChecker
import dataBase.DataBaseMaker
import dataSource.DataSourceFactory
import dataSource.DataSourceType
import railway.Results
import service.CTFsService
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val parseArgs = ArgsParser.parse(args)
    val parseResult = ArgsParser.validateArguments(parseArgs)

    if (parseResult.result == Results.FAILURE) {
        println(parseResult.obj)
        exitProcess(1)
    }

    val dataBaseChecker = DataBaseChecker(DataSourceFactory)

    val dataBaseExists = dataBaseChecker.exitsTheDB(DataSourceType.HIKARI)
    if (dataBaseExists.result == Results.FAILURE) {
        println(dataBaseExists.obj)
        exitProcess(1)
    }

    val dataSource = DataSourceFactory.getDS(DataSourceType.HIKARI)
    val dataBaseMaker = DataBaseMaker(dataSource)

    val creatingTables = dataBaseMaker.createNecessaryTables()
    if (creatingTables.result == Results.FAILURE) {
        println(creatingTables.obj)
        exitProcess(1)
    }

    val ctfDAO = CtfDAOH2(dataSource)
    val grupoDAO = GrupoDAOH2(dataSource)
    val service = CTFsService(ctfDAO, grupoDAO)

    println(service.executeArgs(parseArgs).obj)
}