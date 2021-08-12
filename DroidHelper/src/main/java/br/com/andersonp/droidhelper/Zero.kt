package br.com.andersonp.droidhelper

import java.math.BigDecimal

object Zero {

    fun Float.round(digits: Int = 2): BigDecimal {
        return this.toBigDecimal().setScale(digits, BigDecimal.ROUND_HALF_EVEN)
    }

    fun Double.round(digits: Int = 2): BigDecimal {
        return this.toBigDecimal().setScale(digits, BigDecimal.ROUND_HALF_EVEN)
    }

}