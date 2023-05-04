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
                    val rs = stmt.executeUpdate()
                } catch (e: SQLException) {
                    return Result(ctf, Results.FAILURE)
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
                    val rs = stmt.executeUpdate()
                } catch (e: SQLException) {
                    return Result(grupoid, Results.FAILURE)
                } catch (e: Exception) {
                    return Result(grupoid, Results.FAILURE)
                }
                return Result(grupoid, Results.SUCCESSFUL)
            }
        }
    }

    override fun showAllGroups(): List<Grupo> {
        TODO("Not yet implemented")
    }

    override fun showGroup(grupoid: Int): Result<Grupo, Results> {
        val sql = "SELECT * FROM GRUPOS WHERE GRUPOID = ?;"
        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, grupoid)
                var grupo = Grupo(0, "", 0)
                try {
                    val rs = stmt.executeQuery()
                    if (rs.next()) {
                        grupo = Grupo(
                            grupoid = rs.getInt("GRUPOID"),
                            grupoDesc = rs.getString("GRUPODESC"),
                            mejorCtfId = rs.getInt("MEJORPOSCTFID"),
                        )
                    }
                } catch (e: SQLException) {
                    println(e)
                    return Result(grupo, Results.FAILURE)
                } catch (e: Exception) {
                    println(e)
                    return Result(grupo, Results.FAILURE)
                }
                return Result(grupo, Results.SUCCESSFUL)
            }
        }
    }
}