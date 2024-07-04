package com.upstox.updatedHoldingsAssignment.persentation.main_screen.utilities

import com.upstox.updatedHoldingsAssignment.data.local.HoldingEntity
import com.upstox.updatedHoldingsAssignment.data.mapper.toHoldingInfo
import com.upstox.updatedHoldingsAssignment.utilities.calculateCurrentValue
import com.upstox.updatedHoldingsAssignment.utilities.calculatePercentage
import com.upstox.updatedHoldingsAssignment.utilities.calculateProfitAndLoss
import com.upstox.updatedHoldingsAssignment.utilities.calculateTodayProfitAndLoss
import com.upstox.updatedHoldingsAssignment.utilities.calculateTotalInvestment
import com.upstox.updatedHoldingsAssignment.utilities.commaSeparatedValue
import com.upstox.updatedHoldingsAssignment.utilities.displayPrice
import com.upstox.updatedHoldingsAssignment.utilities.round
import org.junit.Assert.assertEquals
import org.junit.Test

class UtilitiesTest {
    @Test
    fun `Profit loss calculation test 1`() {
        val entity = HoldingEntity(
            ltp = 100.00,
            avgPrice = 45.05,
            quantity = 10
        )

        assertEquals(549.5, calculateProfitAndLoss(entity))
    }

    @Test
    fun `Profit loss calculation test 2`() {
        val entity = HoldingEntity(
            ltp = 100.75,
            avgPrice = 143.25,
            quantity = 17
        )

        assertEquals(-722.5, calculateProfitAndLoss(entity))
    }

    @Test
    fun `Profit loss calculation test 3`() {
        val entity = HoldingEntity(
            ltp = 100.7,
            avgPrice = 143.23,
            quantity = 17
        )

        assertEquals(-723.01, calculateProfitAndLoss(entity)!!, 0.001)
    }

    @Test
    fun `Profit loss calculation test 4`() {
        val entity = HoldingEntity(
            avgPrice = 8.05,
            ltp = 5.03,
            quantity = 990
        )

        val result = calculateProfitAndLoss(entity) ?: 0.0

        assertEquals(-2989.8, result, 0.001)
    }

    @Test
    fun `Profit loss calculation test 5`() {
        val entity = HoldingEntity(
            avgPrice = 120.05,
            ltp = 531.03,
            quantity = 99
        )

        val result = calculateProfitAndLoss(entity) ?: 0.0

        assertEquals(40687.02, result, 0.001)
    }

    @Test
    fun `Current value calculation test`() {
        val list = listOf(
            HoldingEntity(ltp = 38.05, quantity = 990).toHoldingInfo(),
            HoldingEntity(ltp = 118.25, quantity = 110).toHoldingInfo(),
            HoldingEntity(ltp = 550.05, quantity = 150).toHoldingInfo()
        )

        assertEquals(133184.5, calculateCurrentValue(list), 0.001)
    }

    @Test
    fun `Total investment calculation test`() {
        val list = listOf(
            HoldingEntity(avgPrice = 8.05, quantity = 990).toHoldingInfo(),
            HoldingEntity(avgPrice = 108.25, quantity = 110).toHoldingInfo(),
            HoldingEntity(avgPrice = 550.05, quantity = 150).toHoldingInfo()
        )

        assertEquals(102384.5, calculateTotalInvestment(list), 0.001)
    }

    @Test
    fun `Today profit loss calculation test 1`() {
        val list = listOf(
            HoldingEntity(close = 8.05, ltp = 8.00 , quantity = 990).toHoldingInfo(),
            HoldingEntity(close = 108.25, ltp = 108.00 , quantity = 110).toHoldingInfo(),
            HoldingEntity(close = 550.05, ltp = 489.00 , quantity = 150).toHoldingInfo()
        )

        assertEquals(9234.5, calculateTodayProfitAndLoss(list), 0.001)
    }

    @Test
    fun `Percentage calculation test`() {
        assertEquals(10.02, calculatePercentage(10.02, 100.00), 0.001)
    }

    @Test
    fun `Rounding double value test`() {
        assertEquals("10.03", 10.03422.round())
    }

    @Test
    fun `Comma separating value test 1`() {
        assertEquals("10.03", "10.03".commaSeparatedValue())
    }

    @Test
    fun `Comma separating value test 2`() {
        assertEquals("1,000.03", "1000.03".commaSeparatedValue())
    }

    @Test
    fun `Comma separating value test 3`() {
        assertEquals("1,01,45,300.031423", "10145300.031423".commaSeparatedValue())
    }

    @Test
    fun `Comma separating value test 4`() {
        assertEquals("10,14,530.031423", "1014530.031423".commaSeparatedValue())
    }

    @Test
    fun `Display price test 1`() {
        assertEquals("₹ 1,22,432.43", 122432.43.displayPrice())
    }

    @Test
    fun `Display price test 2`() {
        assertEquals("-₹ 1,22,432.43", (-122432.43).displayPrice())
    }
}