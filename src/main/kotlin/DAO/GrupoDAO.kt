package DAO

import Grupo
import railway.Result
import railway.Results

interface GrupoDAO {
    fun getGroup(grupoId: Int): Result<Grupo, Results>
    fun getAllGroups(): Result<List<Grupo>, Results>
    fun addMejorPosCtfId(grupoId: Int, ctfId: Int): Results
}