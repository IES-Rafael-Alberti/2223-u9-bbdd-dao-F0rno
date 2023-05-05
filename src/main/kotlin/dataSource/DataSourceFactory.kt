package dataSource

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import railway.Result
import railway.Results
import javax.sql.DataSource
import logs.i

object DataSourceFactory {
    fun getDS(dataSourceType: DataSourceType): Result<DataSource, Results> {
        when (dataSourceType) {
            DataSourceType.HIKARI -> {
                i("DataSourceFactory.getDS", "Generating HIKARI DataSource")
                val config = HikariConfig()
                config.jdbcUrl = "jdbc:h2:./default"
                config.username = "user"
                config.password = "user"
                config.driverClassName = "org.h2.Driver"
                config.maximumPoolSize = 10
                config.isAutoCommit = true
                config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
                try {
                    HikariDataSource(config).close()
                } catch (e: Exception) {
                    return Result(HikariDataSource(config), Results.FAILURE)
                }
                return Result(HikariDataSource(config), Results.SUCCESSFUL)
            }
        }
    }
}