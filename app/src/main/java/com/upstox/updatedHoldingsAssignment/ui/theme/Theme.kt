package com.upstox.updatedHoldingsAssignment.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.core.view.WindowCompat
import com.upstox.updatedHoldingsAssignment.R
import com.upstox.updatedHoldingsAssignment.ui.theme.dimensions.Dimensions
import com.upstox.updatedHoldingsAssignment.ui.theme.dimensions.dimen_hdpi
import com.upstox.updatedHoldingsAssignment.ui.theme.dimensions.dimen_mdpi
import com.upstox.updatedHoldingsAssignment.ui.theme.dimensions.dimen_xhdpi
import com.upstox.updatedHoldingsAssignment.ui.theme.dimensions.dimen_xxhdpi
import com.upstox.updatedHoldingsAssignment.ui.theme.dimensions.dimen_xxxhdpi
import com.upstox.updatedHoldingsAssignment.ui.theme.fontSizes.FontSize
import com.upstox.updatedHoldingsAssignment.ui.theme.fontSizes.font_size_hdpi
import com.upstox.updatedHoldingsAssignment.ui.theme.fontSizes.font_size_mdpi
import com.upstox.updatedHoldingsAssignment.ui.theme.fontSizes.font_size_xhdpi
import com.upstox.updatedHoldingsAssignment.ui.theme.fontSizes.font_size_xxhdpi
import com.upstox.updatedHoldingsAssignment.ui.theme.fontSizes.font_size_xxxhdpi

@Composable
fun ProvideDimens(
    dimensions: Dimensions,
    fontSizes: FontSize,
    content: @Composable () -> Unit
) {
    val dimensionSet = remember { dimensions }
    val fontSizeSet = remember { fontSizes }

    CompositionLocalProvider(
        LocalAppDimens provides dimensionSet,
        LocalAppFontSizes provides fontSizeSet,
        content = content
    )
}

private val LocalAppDimens = staticCompositionLocalOf {
    dimen_mdpi
}

private val LocalAppFontSizes = staticCompositionLocalOf {
    font_size_mdpi
}

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun UpdatedHoldingsAssignmentTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat
                .getInsetsController(window, view)
                .isAppearanceLightStatusBars = darkTheme
        }
    }

    val configuration = LocalConfiguration.current
    var dimensions = dimen_mdpi
    var fontSize = font_size_mdpi

    if (configuration.screenWidthDp <= 160) {
        dimensions = dimen_mdpi
        fontSize = font_size_mdpi
    } else if (configuration.screenWidthDp in (161..240)) {
        dimensions = dimen_hdpi
        fontSize = font_size_hdpi
    } else if (configuration.screenWidthDp in (241..320)) {
        dimensions = dimen_xhdpi
        fontSize = font_size_xhdpi
    } else if (configuration.screenWidthDp in (321..480)) {
        dimensions = dimen_xxhdpi
        fontSize = font_size_xxhdpi
    } else if (configuration.screenWidthDp > 480) {
        dimensions = dimen_xxxhdpi
        fontSize = font_size_xxxhdpi
    }

    ProvideDimens(
        dimensions = dimensions,
        fontSizes = fontSize
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

object UpdatedHoldingsAssignmentTheme {

    val dimens: Dimensions
        @Composable
        get() = LocalAppDimens.current

    val fontSizes: FontSize
        @Composable
        get() = LocalAppFontSizes.current
}

val Poppins = FontFamily(
    Font(R.font.poppins_light, FontWeight.Light),
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold)
)

val Roboto = FontFamily(
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium)
)