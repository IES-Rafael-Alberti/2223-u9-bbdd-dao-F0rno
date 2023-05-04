package dataBase

import dataSource.DataSourceFactory
import railway.Result
import railway.Results

class DataBaseChecker(private val dataSourceFactory: DataSourceFactory) {
    fun exitsTheDB(): Result<DataSourceFactory, Results> {
        dataSourceFactory.getDS(DataSourceFactory.DataSourceType.HIKARI).connection.use {conn ->
             return when(conn.isValid(5)) {
                 true -> Result(dataSourceFactory, Results.SUCCESSFUL)
                 false -> Result(dataSourceFactory, Results.FAILURE)
             }
        }
    }

    fun exitsThisTable(tableName: String): Result<String, Results> {
        dataSourceFactory.getDS(DataSourceFactory.DataSourceType.HIKARI).connection.use {conn ->
            val rs = conn.metaData.getTables(null, null, tableName, null)
            return if (rs.next()) Result(tableName, Results.SUCCESSFUL) else Result(tableName, Results.FAILURE)
        }
    }
}