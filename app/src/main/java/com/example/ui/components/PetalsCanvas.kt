package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import kotlin.random.Random

private data class Petal(
    var x: Float,
    var y: Float,
    val speedY: Float,
    val speedX: Float,
    val size: Float,
    val rotationSpeed: Float,
    var currentRotation: Float,
    val alpha: Float,
    val color: Color
)

@Composable
fun PetalsCanvas(
    modifier: Modifier = Modifier,
    isDark: Boolean = isSystemInDarkTheme()
) {
    val petalsCount = 18
    val petals = remember {
        List(petalsCount) {
            Petal(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                speedY = Random.nextFloat() * 0.0015f + 0.0008f,
                speedX = (Random.nextFloat() - 0.5f) * 0.001f,
                size = Random.nextFloat() * 16f + 12f,
                rotationSpeed = (Random.nextFloat() - 0.5f) * 1.5f,
                currentRotation = Random.nextFloat() * 360f,
                alpha = Random.nextFloat() * 0.4f + 0.3f,
                color = if (isDark) {
                    Color(0xFFFFB2C5)
                } else {
                    Color(0xFFC86D83)
                }
            )
        }
    }

    var timeNanos by remember { mutableLongStateOf(0L) }

    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos { time ->
                timeNanos = time
            }
        }
    }

    val backgroundGlowColor1 = if (isDark) Color(0xFF381423) else Color(0xFFFBEBF0)
    val backgroundGlowColor2 = if (isDark) Color(0xFF1B0E14) else Color(0xFFFAF6F0)

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // Background Radial Aura
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(backgroundGlowColor1, backgroundGlowColor2),
                center = Offset(width * 0.5f, height * 0.25f),
                radius = width * 0.9f
            )
        )

        // Draw Petals
        petals.forEach { petal ->
            // Update position
            petal.y += petal.speedY
            petal.x += petal.speedX + (kotlin.math.sin((timeNanos / 1_000_000_000f) + petal.y * 5) * 0.0003f).toFloat()
            petal.currentRotation += petal.rotationSpeed

            // Reset when falling off screen
            if (petal.y > 1.05f) {
                petal.y = -0.05f
                petal.x = Random.nextFloat()
            }
            if (petal.x < -0.05f) petal.x = 1.05f
            if (petal.x > 1.05f) petal.x = -0.05f

            val px = petal.x * width
            val py = petal.y * height

            rotate(degrees = petal.currentRotation, pivot = Offset(px, py)) {
                val path = Path().apply {
                    moveTo(px, py - petal.size)
                    cubicTo(
                        px + petal.size * 0.8f, py - petal.size * 0.5f,
                        px + petal.size * 0.8f, py + petal.size * 0.5f,
                        px, py + petal.size
                    )
                    cubicTo(
                        px - petal.size * 0.8f, py + petal.size * 0.5f,
                        px - petal.size * 0.8f, py - petal.size * 0.5f,
                        px, py - petal.size
                    )
                    close()
                }
                drawPath(
                    path = path,
                    color = petal.color.copy(alpha = petal.alpha)
                )
            }
        }
    }
}
