package DAO

import Ctf
import Grupo
import railway.Result
import railway.Results
import java.sql.SQLException
import javax.sql.DataSource

class CtfDAOH2(private val dataSource: DataSource) : CtfDAO {
    override fun addGroupToCTF(ctf: Ctf): Result<Ctf, Results> {
        val sql = "INSERT INTO CTFS (CTFID, GRUPOID, PUNTUACION) VALUES (?, ?, ?);"
        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, ctf.id.toString())
                stmt.setString(2, ctf.grupoId.toString())
                stmt.setString(3, ctf.puntuacion.toString())
                try {
                    stmt.executeUpdate()
                } catch (e: Exception) {
                    return Result(ctf, Results.FAILURE)
                }
                return Result(ctf, Results.SUCCESSFUL)
            }
        }
    }

    override fun deletGroupFromCTF(grupoid: Int, ctfid: Int): Result<Int, Results> {
        val sql = "DELETE FROM CTFS WHERE GRUPOID = ?;"
        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, grupoid.toString())
                val rs = stmt.executeUpdate()
                try {
                    stmt.executeUpdate()
                } catch (e: Exception) {
                    return Result(grupoid, Results.FAILURE)
                }
                return Result(grupoid, Results.SUCCESSFUL)
            }
        }
    }
}