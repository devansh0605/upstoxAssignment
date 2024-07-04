package com.upstox.updatedHoldingsAssignment.ui.theme.fontSizes

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class FontSize(
    val sp12: TextUnit,
    val sp16: TextUnit,
    val sp20: TextUnit
)

val font_size_mdpi = FontSize(
    sp12 = 4.sp,
    sp16 = 5.33.sp,
    sp20 = 6.67.sp
)

val font_size_hdpi = FontSize(
    sp12 = 6.sp,
    sp16 = 8.sp,
    sp20 = 10.sp
)

val font_size_xhdpi = FontSize(
    sp12 = 8.sp,
    sp16 = 10.66.sp,
    sp20 = 13.34.sp
)

val font_size_xxhdpi = FontSize(
    sp12 = 12.sp,
    sp16 = 16.sp,
    sp20 = 20.sp
)

val font_size_xxxhdpi = FontSize(
    sp12 = 16.sp,
    sp16 = 21.32.sp,
    sp20 = 26.68.sp
)