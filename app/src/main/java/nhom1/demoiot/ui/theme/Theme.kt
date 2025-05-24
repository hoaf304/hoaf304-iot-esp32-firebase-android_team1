package nhom1.demoiot.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF00FFFF),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF003B4A),
    onPrimaryContainer = Color(0xFFB2FFFF),

    secondary = Color(0xFFFF00FF),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF3A004F),
    onSecondaryContainer = Color(0xFFFFB3FF),

    tertiary = Color(0xFF00FFAA),
    onTertiary = Color.Black,

    background = Color(0xFF0A0F14),
    onBackground = Color(0xFFE0E0E0),

    surface = Color(0xFF121212),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF1F1F1F),
    onSurfaceVariant = Color(0xFFCCCCCC),

    error = Color(0xFFFF5252),
    onError = Color.Black,

    outline = Color(0xFF00FFFF), // Glow outlines
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF00BCD4),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE0F7FA),
    onPrimaryContainer = Color.Black,

    secondary = Color(0xFFFF4081),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFCE4EC),
    onSecondaryContainer = Color.Black,

    background = Color(0xFFF5F5F5),
    onBackground = Color.Black,

    surface = Color.White,
    onSurface = Color.Black,

    error = Color(0xFFD32F2F),
    onError = Color.White
)

@Composable
fun Theme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
