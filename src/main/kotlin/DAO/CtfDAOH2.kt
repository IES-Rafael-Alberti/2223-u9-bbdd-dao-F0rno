package DAO

import Ctf
import Grupo
import javax.sql.DataSource

class CtfDAOH2(private val dataSource: DataSource) : CtfDAO {
    override fun addGroupToCTF(ctf: Ctf): Boolean {
        TODO("Not yet implemented")
    }

    override fun deletGroupFromCTF(grupoid: Int, ctfid: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun showAllGroup(): List<Grupo> {
        TODO("Not yet implemented")
    }

    override fun showGroup(grupoid: Int): Grupo {
        TODO("Not yet implemented")
    }
}