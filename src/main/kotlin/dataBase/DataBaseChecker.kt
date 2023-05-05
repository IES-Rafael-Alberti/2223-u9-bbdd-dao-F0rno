package dataBase

import dataSource.DataSourceFactory
import dataSource.DataSourceType
import logs.i
import railway.Result
import railway.Results

class DataBaseChecker(private val dataSourceFactory: DataSourceFactory) {
    fun exitsTheDB(): Result<DataSourceFactory, Results> {
        val rs = dataSourceFactory.getDS(DataSourceType.HIKARI)
        if (rs.result == Results.FAILURE) return Result(dataSourceFactory, Results.FAILURE)
        rs.obj.connection.use { conn ->
            i("DataBaseChecker.exitsTheDB", "Validating the connexion to the DB")
            return when(conn.isValid(5)) {
                 true -> Result(dataSourceFactory, Results.SUCCESSFUL)
                 false -> Result(dataSourceFactory, Results.FAILURE)
             }
        }
    }

    fun exitsThisTable(tableName: String): Result<String, Results> {
        dataSourceFactory.getDS(DataSourceType.HIKARI).obj.connection.use {conn ->
            i("DataBaseChecker.exitsThisTable", "Getting the tables from DB, to check $tableName")
            val rs = conn.metaData.getTables(null, null, tableName, null)
            return if (rs.next()) Result(tableName, Results.SUCCESSFUL) else Result(tableName, Results.FAILURE)
        }
    }
}