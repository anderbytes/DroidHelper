package br.com.andersonp.droidhelper

import android.util.Base64
import java.math.BigInteger
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

/**
 * Enigma - The mysterious puzzle maker and solver
 *
 */
@Suppress("unused")
object Enigma {

    /**
     * Returns the MD5 hash of the given String
     *
     * @param allCaps whether the result has to have all capitalized characters
     * @return MD5 hash value
     */
    fun String.md5(allCaps: Boolean = true): String {
        val digestInstance: MessageDigest = MessageDigest.getInstance("MD5")
        var calculatedMD5: String = BigInteger(1, digestInstance.digest(this.toByteArray())).toString(16).padStart(32, '0')
        if (allCaps) calculatedMD5 = calculatedMD5.uppercase()
        return calculatedMD5
    }

    /**
     * Returns the SHA256 hash of the given String
     *
     * @param allCaps whether the result has to have all capitalized characters
     * @return SHA256 hash value
     */
    fun String.sha256(allCaps: Boolean = true): String {
        val bytes = this.toByteArray()
        val digestInstance: MessageDigest = MessageDigest.getInstance("SHA-256")
        val finalDigest = digestInstance.digest(bytes)
        var calculatedSHA256: String = finalDigest.fold("", { str, it -> str + "%02x".format(it) })
        if (allCaps) calculatedSHA256 = calculatedSHA256.uppercase()
        return calculatedSHA256
    }

    /**
     * Encrypts a string based on a password and specified parameters
     *
     * @param strToEncrypt The original string to be encrypted and later decrypted
     * @param pass the password to be used in encryption
     * @param algorithm the used algorithm
     * @param transformationMode the used transformation mode
     * @param initVector the used initialization vector
     * @param salt the used Salt
     * @param iterationCount the # of iterations when generating the key
     * @param keyLength the length of the generated key
     * @return The encrypted string
     */
    fun encrypt(strToEncrypt: String, pass: String, algorithm: String = "PBKDF2WithHmacSHA1",transformationMode: String = "AES/CBC/PKCS5Padding", initVector: String, salt: String, iterationCount: Int = 6000, keyLength: Int = 128) : String {
        val factory = SecretKeyFactory.getInstance(algorithm)
        val keySpec = PBEKeySpec(pass.toCharArray(), Base64.decode(salt, Base64.DEFAULT), iterationCount, keyLength)
        val secretKey = SecretKeySpec(factory.generateSecret(keySpec).encoded, "AES")

        val cipher = Cipher.getInstance(transformationMode)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(Base64.decode(initVector, Base64.DEFAULT)))
        return Base64.encodeToString(cipher.doFinal(strToEncrypt.toByteArray(Charsets.UTF_8)), Base64.DEFAULT)
    }

    /**
     * Decrypts a string based on a password and specified parameters
     *
     * @param strToDecrypt The encrypted string to be decrypted
     * @param pass the password used originally in encryption
     * @param algorithm the used algorithm
     * @param transformationMode the used transformation mode
     * @param initVector the used initialization vector
     * @param salt the used Salt
     * @param iterationCount the # of iterations when generating the key
     * @param keyLength the length of the generated key
     * @return The decrypted string
     */
    fun decrypt(strToDecrypt : String, pass: String, algorithm: String = "PBKDF2WithHmacSHA1",transformationMode: String = "AES/CBC/PKCS5Padding", initVector: String, salt: String, iterationCount: Int = 6000, keyLength: Int = 128) : String {
        val factory = SecretKeyFactory.getInstance(algorithm)
        val keySpec = PBEKeySpec(pass.toCharArray(), Base64.decode(salt, Base64.DEFAULT), iterationCount, keyLength)
        val secretKey = SecretKeySpec(factory.generateSecret(keySpec).encoded, "AES")

        val cipher = Cipher.getInstance(transformationMode)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(Base64.decode(initVector, Base64.DEFAULT)))
        return String(cipher.doFinal(Base64.decode(strToDecrypt, Base64.DEFAULT)))
    }
}