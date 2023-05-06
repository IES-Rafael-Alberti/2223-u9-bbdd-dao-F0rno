package DAO

import Grupo
import railway.Result
import railway.Results

interface GrupoDAO {
    fun showGroup(grupoId: Int): Result<Grupo, Results>
    fun showAllGroups(): Result<List<Grupo>, Results>
    fun addMejorPosCtfId(grupoId: Int, ctfId: Int): Results
}