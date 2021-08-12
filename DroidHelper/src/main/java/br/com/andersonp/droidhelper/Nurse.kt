package br.com.andersonp.droidhelper

import android.util.Log
import br.com.andersonp.droidhelper.Zero.round
import kotlin.math.pow
import kotlin.math.sqrt

object Nurse {

    fun runDiags(tela: Boolean = true) {
        if (tela) {
            val densidadeValor = Architect.getScreenDensityMultiplier()
            val densidadeDpi = Architect.getScreenDensityDPI()
            val densidadeNome = Architect.getScreenDensityName()
            val larguraTelaPx = Architect.getScreenWidthPX()
            val larguraTelaDp = Architect.getScreenWidthDP()
            val alturaTelaPx = Architect.getScreenHeightPX()
            val alturaTelaDp = Architect.getScreenHeightDP()
            val larguraTelaIn = (larguraTelaPx / densidadeDpi)
            val alturaTelaIn = (alturaTelaPx / densidadeDpi)
            val larguraTelaCm = (larguraTelaIn * 2.54).round()
            val alturaTelaCm = (alturaTelaIn * 2.54).round()

            val polTela =
                sqrt((larguraTelaPx / densidadeDpi).pow(2) + (alturaTelaPx / densidadeDpi).pow(2)).round()

            fun ratio(dim1: Int, dim2: Int): String {

                val menor: Int = if (dim1 <= dim2) {
                    dim1
                } else {
                    dim2
                }
                val divisor: Double = menor.toDouble() / 9
                return if (dim1 == menor) {
                    "(" + (dim2 / divisor) + ":9)"
                } else {
                    "(" + (dim1 / divisor) + ":9)"
                }
            }

            val ratio = ratio(larguraTelaPx, alturaTelaPx)
            Log.d(
                "Tela", """
            -----------------------------------------------------------------------------
            Densidade: $densidadeValor ($densidadeNome) -> ~$densidadeDpi dpi
            Largura: $larguraTelaPx px ($larguraTelaDp dp) -> $larguraTelaIn in -> $larguraTelaCm cm
            Largura: $alturaTelaPx px ($alturaTelaDp dp) -> $alturaTelaIn in -> $alturaTelaCm cm
            Tela: $polTela'' - Ratio $ratio
            -----------------------------------------------------------------------------
        """.trimMargin()
            )
        }
    }
}