package DAO

import Ctf
import railway.Result
import railway.Results

/**
 * Interface to interact with the CTF table
 */
interface CtfDAO {
    /**
     * Adds a CTF object
     */
    fun addGroupParticipationInCTF(ctf: Ctf): Result<Ctf, Results>

    /**
     * Delets a CTF
     */
    fun deletGroupFromCTF(grupoid: Int, ctfid: Int): Result<Int, Results>

    /**
     * Gets all CTFs
     */
    fun getAllCTFs(): Result<List<Ctf>, Results>
}