package DAO

import Ctf
import Grupo

interface CtfDAO {
    fun addGroupToCTF(ctf: Ctf): Ctf
    fun deletGroupFromCTF(grupoid: Int, ctfid: Int)
    fun showGroup(grupoid: Int): Grupo
    fun showAllGroup(): List<Grupo>
}