package DAO

import Grupo
import logs.i
import railway.Result
import railway.Results
import javax.sql.DataSource

class GrupoDAOH2(private val dataSource: DataSource) : GrupoDAO  {
    override fun showAllGroups(): List<Grupo> {
        val sql = "SELECT * FROM GRUPOS;"
        dataSource.connection.use { conn ->
            i("GrupoDAOH2.showAllGroups", "Preparing statement")
            conn.prepareStatement(sql).use { stmt ->
                val grupos = mutableListOf<Grupo>()
                try {
                    i("GrupoDAOH2.showAllGroups", "Executing query")
                    val rs = stmt.executeQuery()
                    while (rs.next()) {
                        grupos.add(Grupo(
                            grupoid = rs.getInt("GRUPOID"),
                            grupoDesc = rs.getString("GRUPODESC"),
                            mejorCtfId = rs.getInt("MEJORPOSCTFID"),
                        )
                        )
                    }
                } catch (e: Exception) {
                    return grupos
                }
                return grupos
            }
        }
    }

    override fun showGroup(grupoid: Int): Result<Grupo, Results> {
        val sql = "SELECT * FROM GRUPOS WHERE GRUPOID = ?;"
        dataSource.connection.use { conn ->
            i("GrupoDAOH2.showGroup", "Preparing statement")
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, grupoid)
                var grupo = Grupo(0, "", 0)
                try {
                    i("GrupoDAOH2.showGroup", "Executing query")
                    val rs = stmt.executeQuery()
                    if (rs.next()) {
                        grupo = Grupo(
                            grupoid = rs.getInt("GRUPOID"),
                            grupoDesc = rs.getString("GRUPODESC"),
                            mejorCtfId = rs.getInt("MEJORPOSCTFID"),
                        )
                    }
                } catch (e: Exception) {
                    return Result(grupo, Results.FAILURE)
                }
                return Result(grupo, Results.SUCCESSFUL)
            }
        }
    }
}