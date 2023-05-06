package dataBase

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dataSource.DataSourceFactory
import dataSource.DataSourceType
import logs.i
import railway.Result
import railway.Results

class DataBaseChecker(private val dataSourceFactory: DataSourceFactory) {
    fun exitsTheDB(dataSourceType: DataSourceType): Results {
        when (dataSourceType) {
            DataSourceType.HIKARI -> {
                i("DataBaseChecker.exitsTheDB", "Testing connexion to the DB, using HIKARI")
                val config = HikariConfig()
                config.jdbcUrl = "jdbc:h2:./default"
                config.username = "user"
                config.password = "user"
                config.driverClassName = "org.h2.Driver"
                config.maximumPoolSize = 10
                config.isAutoCommit = true
                config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
                var result = Results.SUCCESSFUL
                try {
                    HikariDataSource(config).close()
                } catch (e: Exception) {
                    i("DataBaseChecker.exitsTheDB", "$e")
                    result = Results.FAILURE
                }
                return result
            }
        }
    }

    fun exitsThisTable(tableName: String): Result<String, Results> {
        dataSourceFactory.getDS(DataSourceType.HIKARI).connection.use {conn ->
            i("DataBaseChecker.exitsThisTable", "Getting the tables from DB, to check $tableName")
            val rs = conn.metaData.getTables(null, null, tableName, null)
            return if (rs.next()) Result(tableName, Results.SUCCESSFUL) else Result(tableName, Results.FAILURE)
        }
    }
}