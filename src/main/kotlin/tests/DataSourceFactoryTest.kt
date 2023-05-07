package tests

import dataSource.DataSourceFactory
import dataSource.DataSourceType
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import javax.sql.DataSource
import kotlin.test.assertTrue

class DataSourceFactoryTest {
    @Test
    fun get_HIKARI_data_source() {
        val dataSource = DataSourceFactory.getDS(DataSourceType.HIKARI)
        assertTrue(dataSource is DataSource)
    }
}