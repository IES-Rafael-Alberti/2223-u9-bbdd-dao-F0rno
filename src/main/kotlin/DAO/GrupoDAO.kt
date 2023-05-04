package DAO

import Grupo
import railway.Result
import railway.Results

interface GrupoDAO {
    fun showGroup(grupoid: Int): Result<Grupo, Results>
    fun showAllGroups(): List<Grupo>
}