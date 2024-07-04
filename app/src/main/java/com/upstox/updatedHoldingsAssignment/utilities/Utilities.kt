package com.upstox.updatedHoldingsAssignment.utilities

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.upstox.updatedHoldingsAssignment.AppClass
import com.upstox.updatedHoldingsAssignment.data.local.HoldingEntity
import com.upstox.updatedHoldingsAssignment.domain.model.HoldingInfo
import kotlin.math.absoluteValue

fun showToast(text: String) {
    Handler(Looper.getMainLooper()).post {
        Toast.makeText(
            AppClass.getContext(),
            text,
            Toast.LENGTH_SHORT
        ).show()
    }
}

/**
 *  To calculate net profit or loss for a particular stock.
 */
fun calculateProfitAndLoss(holdingEntity: HoldingEntity): Double? {
    val priceDifference = holdingEntity.ltp?.minus(holdingEntity.avgPrice ?: return null)
    return priceDifference?.times(holdingEntity.quantity ?: return null)
}

fun calculateCurrentValue(holdings: List<HoldingInfo>): Double {
    var currentValue = 0.0

    holdings.forEach {
        currentValue += it.ltp?.times(it.quantity ?: 0) ?: 0.0
    }

    return currentValue
}

fun calculateTotalInvestment(holdings: List<HoldingInfo>): Double {
    var totalInvestment = 0.0

    holdings.forEach {
        totalInvestment += it.avgPrice?.times(it.quantity ?: 0) ?: 0.0
    }

    return totalInvestment
}

fun calculateTodayProfitAndLoss(holdings: List<HoldingInfo>): Double {
    var totalValue = 0.0

    holdings.forEach loop@{
        totalValue += (it.close?.minus(it.ltp ?: return@loop))?.times(it.quantity ?: 0) ?: 0.0
    }

    return totalValue
}

fun calculatePercentage(numerator: Double?, denominator: Double?): Double {
    return (numerator?.div((denominator ?: return 0.0)))?.times(100) ?: 0.0
}

fun splitStringInTwos(string: String): String {
    return (if (string.length - 2 > 0) splitStringInTwos(string.take(string.length - 2)) else return string) +
            "," + string.takeLast(2)
}

fun Double.displayPrice(): String {
    return (if (this < 0) "-" else "") + Constants.CURRENCY_SYMBOL + " " + this.absoluteValue.round().commaSeparatedValue()
}

fun String.commaSeparatedValue(): String {
    val amountArray = this.split(".")

    val csvBuilder = StringBuilder()

    val startIndex = amountArray[0].length - 3

    if (startIndex > 0) {
        csvBuilder.append(splitStringInTwos(amountArray[0].substring(0, startIndex)) +"," + amountArray[0].substring(startIndex))
    } else {
        csvBuilder.append(amountArray[0])
    }

    csvBuilder.append(".${amountArray[1]}")

    return csvBuilder.toString()
}

fun Double.round(decimals: Int = 2): String = "%.${decimals}f".format(this)