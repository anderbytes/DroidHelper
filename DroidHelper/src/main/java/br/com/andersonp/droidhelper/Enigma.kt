package br.com.andersonp.droidhelper

import java.math.BigInteger
import java.security.MessageDigest

/**
 * Enigma - The mysterious puzzle maker and solver
 *
 */
object Enigma {

    /**
     * Returns the MD5 hash of the given String
     *
     * @return MD5 hash value
     */
    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(this.toByteArray())).toString(16).padStart(32, '0')
    }

    /**
     * Returns the SHA256 hash of the given String
     *
     * @return SHA256 hash value
     */
    fun String.sha256(): String {
        val bytes = this.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }

}