package tests

import dataSource.DataSourceFactory
import dataSource.DataSourceType
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import javax.sql.DataSource

class DataSourceFactoryTest : DescribeSpec({
    describe("Getting a dataSource") {
        it("hikari") {
            DataSourceFactory.getDS(DataSourceType.HIKARI).shouldBeInstanceOf<DataSource>()
        }
    }
})
