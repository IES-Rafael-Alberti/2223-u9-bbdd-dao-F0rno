package dataBase

import dataSource.DataSourceFactory
import logs.i
import railway.Result
import railway.Results
import javax.sql.DataSource

class DataBaseMaker(private val dataSource: DataSource) {
    private val tablesDDL = mapOf(
        Tables.CTFS to """
            CREATE TABLE CTFS (
                CTFid INT NOT NULL,
                grupoid INT NOT NULL,
                puntuacion INT NOT NULL,
                PRIMARY KEY (CTFid,grupoid)
            );
        """.trimIndent() ,
        Tables.GRUPOS to """
            CREATE TABLE GRUPOS (
                grupoid INT NOT NULL AUTO_INCREMENT,
                grupodesc VARCHAR(100) NOT NULL,
                mejorposCTFid INT,
                PRIMARY KEY (grupoid)
            );
            insert into grupos(grupoid, grupodesc, MEJORPOSCTFID) values(1, '1DAM-G1', null);
            insert into grupos(grupoid, grupodesc, MEJORPOSCTFID) values(2, '1DAM-G2', null);
            insert into grupos(grupoid, grupodesc, MEJORPOSCTFID) values(3, '1DAM-G3', null);
            insert into grupos(grupoid, grupodesc, MEJORPOSCTFID) values(4, '1DAW-G1', null);
            insert into grupos(grupoid, grupodesc, MEJORPOSCTFID) values(5, '1DAW-G2', null);
            insert into grupos(grupoid, grupodesc, MEJORPOSCTFID) values(6, '1DAW-G3', null);
        """.trimIndent(),
    )

    fun createTable(table: Tables): Result<String, Results> {
        i("DataBaseMaker.createTable", "Creating table $table")
        val sql = tablesDDL[table]?: ""
        dataSource.connection.use {conn ->
            conn.prepareStatement(sql).use {stmt ->
                try {
                    i("DataBaseMaker.createTable", "Executing sql for $table")
                    stmt.executeUpdate()
                } catch (e: Exception) {
                    i("DataBaseMaker.createTable", "$e")
                    return Result(sql, Results.FAILURE)
                }
                return Result(sql, Results.SUCCESSFUL)
            }
        }
    }

    fun createNecessaryTables(): Result<String, Results> {
        Tables.values().forEach { table ->
            val existTheTable = DataBaseChecker(DataSourceFactory).exitsThisTable(table.toString())
            if (existTheTable.result == Results.FAILURE) {
                if (createTable(table).result == Results.FAILURE) {
                    return Result("Error creating the table: table", Results.FAILURE)
                }
            }
        }
        return Result("All tables done", Results.SUCCESSFUL)
    }
}