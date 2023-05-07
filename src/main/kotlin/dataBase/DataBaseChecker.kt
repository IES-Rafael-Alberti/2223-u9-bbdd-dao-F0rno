package dataBase

import dataSource.DataSourceFactory
import dataSource.DataSourceType
import logs.i
import railway.Result
import railway.Results

class DataBaseChecker(private val dataSourceFactory: DataSourceFactory) {
    fun exitsTheDB(dataSourceType: DataSourceType): Result<String, Results> {
        try {
            DataSourceFactory.getDS(dataSourceType)
        } catch (e: Exception) {
            i("DataBaseChecker.exitsTheDB", "$e")
            return Result("Cant connect to the database or does not exists", Results.FAILURE)
        }
        return Result("Can connect to the database", Results.SUCCESSFUL)
    }

    fun exitsThisTable(tableName: String): Result<String, Results> {
        dataSourceFactory.getDS(DataSourceType.HIKARI).connection.use {conn ->
            i("DataBaseChecker.exitsThisTable", "Getting the tables from DB, to check $tableName")
            val rs = conn.metaData.getTables(null, null, tableName, null)
            return if (rs.next()) Result(tableName, Results.SUCCESSFUL) else Result(tableName, Results.FAILURE)
        }
    }
}