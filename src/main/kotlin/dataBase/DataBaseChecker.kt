package dataBase

import dataSource.DataSourceFactory
import dataSource.DataSourceType
import logs.i
import railway.Result
import railway.Results

class DataBaseChecker(private val dataSourceFactory: DataSourceFactory) {
    fun exitsTheDB(dataSourceType: DataSourceType): Results {
        var dataBaseConnResult = Results.SUCCESSFUL
        try {
            DataSourceFactory.getDS(dataSourceType)
        } catch (e: Exception) {
            i("DataBaseChecker.exitsTheDB", "$e")
            dataBaseConnResult = Results.FAILURE
        }
        return dataBaseConnResult
    }

    fun exitsThisTable(tableName: String): Result<String, Results> {
        dataSourceFactory.getDS(DataSourceType.HIKARI).connection.use {conn ->
            i("DataBaseChecker.exitsThisTable", "Getting the tables from DB, to check $tableName")
            val rs = conn.metaData.getTables(null, null, tableName, null)
            return if (rs.next()) Result(tableName, Results.SUCCESSFUL) else Result(tableName, Results.FAILURE)
        }
    }
}