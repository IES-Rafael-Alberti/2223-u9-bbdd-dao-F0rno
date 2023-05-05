package DAO

import Grupo
import railway.Result
import railway.Results

interface GrupoDAO {
    fun showGroup(grupoId: Int): Result<Grupo, Results>
    fun showAllGroups(): List<Grupo>
    fun addMejorPosCtfId(grupoId: Int, ctfId: Int): Results
}