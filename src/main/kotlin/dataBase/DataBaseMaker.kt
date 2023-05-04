package dataBase

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
            insert into grupos(grupoid, grupodesc, MEJORPOSCTFID) values(1, '1DAM-G1', 0);
            insert into grupos(grupoid, grupodesc, MEJORPOSCTFID) values(2, '1DAM-G2', 0);
            insert into grupos(grupoid, grupodesc, MEJORPOSCTFID) values(3, '1DAM-G3', 0);
            insert into grupos(grupoid, grupodesc, MEJORPOSCTFID) values(4, '1DAW-G1', 0);
            insert into grupos(grupoid, grupodesc, MEJORPOSCTFID) values(5, '1DAW-G2', 0);
            insert into grupos(grupoid, grupodesc, MEJORPOSCTFID) values(6, '1DAW-G3', 0);
        """.trimIndent(),
    )
    fun createTable(table: Tables): Result<String, Results> {
        val sql = tablesDDL[table]?: ""
        println(sql)
        dataSource.connection.use {conn ->
            conn.prepareStatement(sql).use {stmt ->
                try {
                    stmt.executeUpdate()
                } catch (e: Exception) {
                    return Result(sql, Results.FAILURE)
                }
                return Result(sql, Results.SUCCESSFUL)
            }
        }
    }
}