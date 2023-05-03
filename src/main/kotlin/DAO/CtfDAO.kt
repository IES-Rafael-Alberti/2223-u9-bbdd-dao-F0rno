package DAO

import Ctf
import Grupo

interface CtfDAO {
    fun addGroupToCTF(ctf: Ctf): Boolean
    fun deletGroupFromCTF(grupoid: Int, ctfid: Int): Boolean
    fun showGroup(grupoid: Int): Grupo
    fun showAllGroup(): List<Grupo>
}