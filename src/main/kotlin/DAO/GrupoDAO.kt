package DAO

import Grupo
import railway.Result
import railway.Results

/**
 * Interface to interact with the GRUPO table
 */
interface GrupoDAO {
    /**
     * Gets a Grupo object
     */
    fun getGroup(grupoId: Int): Result<Grupo, Results>

    /**
     * Gets all Grupos
     */
    fun getAllGroups(): Result<List<Grupo>, Results>

    /**
     * Change the MEJORPOSCTFID column from a grupo
     */
    fun addMejorPosCtfId(grupoId: Int, ctfId: Int): Results
}