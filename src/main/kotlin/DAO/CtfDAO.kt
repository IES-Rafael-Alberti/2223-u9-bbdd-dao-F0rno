package DAO

import Ctf
import Grupo
import railway.Result
import railway.Results

interface CtfDAO {
    fun addGroupToCTF(ctf: Ctf): Result<Ctf, Results>
    fun deletGroupFromCTF(grupoid: Int, ctfid: Int)
    fun showGroup(grupoid: Int): Grupo
    fun showAllGroup(): List<Grupo>
}