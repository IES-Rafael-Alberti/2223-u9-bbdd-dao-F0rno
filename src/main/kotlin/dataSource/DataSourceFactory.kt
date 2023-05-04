package dataSource

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import railway.Result
import railway.Results
import javax.sql.DataSource

object DataSourceFactory {
    fun getDS(dataSourceType: DataSourceType): Result<DataSource, Results> {
        return when (dataSourceType) {
            DataSourceType.HIKARI -> {
                val config = HikariConfig()
                config.jdbcUrl = "jdbc:h2:./default"
                config.username = "user"
                config.password = "user"
                config.driverClassName = "org.h2.Driver"
                config.maximumPoolSize = 10
                config.isAutoCommit = true
                config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
                lateinit var dataSource: DataSource
                try {
                    dataSource = HikariDataSource(config)
                } catch (e: Exception) {
                    return Result(dataSource, Results.FAILURE)
                }
                return Result(dataSource, Results.SUCCESSFUL)
            }
        }
    }
}