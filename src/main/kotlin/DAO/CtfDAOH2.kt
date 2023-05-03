package DAO

import Ctf
import Grupo
import javax.sql.DataSource

class CtfDAOH2(private val dataSource: DataSource) : CtfDAO {
    override fun addGroupToCTF(ctf: Ctf): Ctf {
        val sql = "INSERT INTO CTFS (CTFID, GRUPOID, PUNTUACION) VALUES (?, ?, ?);"
        return dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, ctf.id.toString())
                stmt.setString(2, ctf.grupoId.toString())
                stmt.setString(3, ctf.puntuacion.toString())
                val rs = stmt.executeUpdate()
                ctf
            }
        }
    }

    override fun deletGroupFromCTF(grupoid: Int, ctfid: Int) {
        TODO("Not yet implemented")
    }

    override fun showAllGroup(): List<Grupo> {
        TODO("Not yet implemented")
    }

    override fun showGroup(grupoid: Int): Grupo {
        TODO("Not yet implemented")
    }
}