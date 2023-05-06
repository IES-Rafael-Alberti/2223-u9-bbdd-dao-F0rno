package service

import DAO.CtfDAO
import DAO.CtfDAOH2
import DAO.GrupoDAO
import args.*
import dataBase.DataBaseChecker
import dataSource.DataSourceFactory
import railway.Result
import railway.Results

class CTFsService(
    private val ctfDAO: CtfDAO,
    private val grupoDAO: GrupoDAO
) {
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