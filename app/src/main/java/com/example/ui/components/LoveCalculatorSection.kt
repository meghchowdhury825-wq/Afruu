package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.AfiaData
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.HindSiliguriFontFamily
import com.example.ui.theme.VerifiedBlueBadge
import com.example.util.BengaliUtils
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoveCalculatorSection() {
    val context = LocalContext.current

    var name1 by remember { mutableStateOf("আফিয়া আফরিন") }
    var name2 by remember { mutableStateOf("") }
    var calculatedScore by remember { mutableStateOf<Int?>(null) }
    var calculatedMessage by remember { mutableStateOf("") }
    var showResultDialog by remember { mutableStateOf(false) }

    // Pulsing heart animation
    val infiniteTransition = rememberInfiniteTransition(label = "love_anim")
    val heartScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "heart_pulse"
    )

    val auraRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "aura_rotation"
    )

    val floatY by infiniteTransition.animateFloat(
        initialValue = -6f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floating_y"
    )

    // Animated score count-up
    val animatedScore by animateIntAsState(
        targetValue = if (showResultDialog) (calculatedScore ?: 0) else 0,
        animationSpec = tween(durationMillis = 1400, easing = LinearOutSlowInEasing),
        label = "score_anim"
    )

    fun calculateLove() {
        if (name1.isBlank() || name2.isBlank()) return

        // Deterministic high romantic compatibility calculation
        val combined = (name1.trim().lowercase() + name2.trim().lowercase()).toCharArray()
        var charSum = 0
        combined.forEach { charSum += it.code }
        val baseScore = 78 + (abs(charSum) % 22) // Ensure score is always between 78% and 99%

        calculatedScore = baseScore
        calculatedMessage = getBengaliLoveQuote(baseScore, name1, name2)
        showResultDialog = true
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(10.dp, RoundedCornerShape(28.dp))
            .testTag("love_calculator_card"),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = Brush.horizontalGradient(
                listOf(
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    GoldAccent.copy(alpha = 0.5f)
                )
            )
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = Color(0xFFE91E63),
                    modifier = Modifier
                        .size(28.dp)
                        .scale(heartScale)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "ভালোবাসা ক্যালকুলেটর",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = HindSiliguriFontFamily
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Filled.AutoAwesome,
                    contentDescription = null,
                    tint = GoldAccent,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "দুটি মনের বন্ধন ও ভালোবাসার শতকরা অনুপাত জানুন 💖",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontFamily = HindSiliguriFontFamily,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Input Fields
            OutlinedTextField(
                value = name1,
                onValueChange = { name1 = it },
                label = { Text("প্রথম নাম (আপনার নাম)", fontFamily = HindSiliguriFontFamily) },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Person, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("love_input_name1")
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = name2,
                onValueChange = { name2 = it },
                label = { Text("দ্বিতীয় নাম (প্রিয়জনের নাম)", fontFamily = HindSiliguriFontFamily) },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Favorite, contentDescription = null, tint = Color(0xFFE91E63))
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE91E63),
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("love_input_name2")
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Action Calculate Button
            Button(
                onClick = { calculateLove() },
                enabled = name1.isNotBlank() && name2.isNotBlank(),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE91E63),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .shadow(6.dp, RoundedCornerShape(18.dp))
                    .testTag("calculate_love_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ভালোবাসার শতাংশ হিসাব করুন 💖",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = HindSiliguriFontFamily
                    )
                }
            }

            // Quick view trigger if calculated before
            if (calculatedScore != null && !showResultDialog) {
                Spacer(modifier = Modifier.height(12.dp))
                TextButton(
                    onClick = { showResultDialog = true },
                    modifier = Modifier.testTag("reopen_love_result")
                ) {
                    Text(
                        text = "সর্বশেষ ফলাফল দেখুন (${calculatedScore}%)",
                        color = Color(0xFFE91E63),
                        fontFamily = HindSiliguriFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    // ROMANTIC POP-UP RESULT MODAL DIALOG 💖
    if (showResultDialog) {
        Dialog(
            onDismissRequest = { showResultDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .wrapContentHeight()
                    .shadow(24.dp, RoundedCornerShape(32.dp))
                    .clip(RoundedCornerShape(32.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .border(
                        2.dp,
                        Brush.horizontalGradient(
                            listOf(Color(0xFFE91E63), GoldAccent, Color(0xFFFF4081))
                        ),
                        RoundedCornerShape(32.dp)
                    )
                    .testTag("love_result_dialog_modal")
            ) {
                // Background Gentle Floating Hearts & Glow Effect
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0xFFFFF0F5),
                                    MaterialTheme.colorScheme.surface,
                                    Color(0xFFFFF8FA)
                                )
                            )
                        )
                ) {
                    // Floating Heart 1 Top Left
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = null,
                        tint = Color(0xFFE91E63).copy(alpha = 0.12f),
                        modifier = Modifier
                            .size(70.dp)
                            .align(Alignment.TopStart)
                            .offset(x = (-10).dp, y = (20).dp + floatY.dp)
                    )

                    // Floating Heart 2 Bottom Right
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = null,
                        tint = GoldAccent.copy(alpha = 0.18f),
                        modifier = Modifier
                            .size(90.dp)
                            .align(Alignment.BottomEnd)
                            .offset(x = (20).dp, y = (10).dp - floatY.dp)
                    )

                    // Floating Heart 3 Center Right
                    Icon(
                        imageVector = Icons.Filled.AutoAwesome,
                        contentDescription = null,
                        tint = Color(0xFFFF4081).copy(alpha = 0.2f),
                        modifier = Modifier
                            .size(36.dp)
                            .align(Alignment.CenterEnd)
                            .offset(x = (-16).dp, y = (-40).dp + floatY.dp)
                    )
                }

                // Modal Content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Close Header Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = null,
                                tint = Color(0xFFE91E63),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "ভালোবাসার বন্ধন",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE91E63),
                                fontFamily = HindSiliguriFontFamily
                            )
                        }

                        IconButton(
                            onClick = { showResultDialog = false },
                            modifier = Modifier.testTag("close_love_dialog")
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "বন্ধ করুন",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Circular Glowing Percentage Ring Container
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(136.dp)
                    ) {
                        // Rotating Gradient Ring Aura
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(
                                    Brush.sweepGradient(
                                        listOf(
                                            Color(0xFFE91E63),
                                            GoldAccent,
                                            Color(0xFFFF4081),
                                            VerifiedBlueBadge,
                                            Color(0xFFE91E63)
                                        )
                                    )
                                )
                                .rotate(auraRotation)
                        ) {}

                        // Percentage Badge inside
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(124.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surface)
                                .border(2.dp, Color(0xFFFFF0F5), CircleShape)
                                .shadow(8.dp, CircleShape)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "$animatedScore%",
                                    fontSize = 38.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFE91E63),
                                    fontFamily = HindSiliguriFontFamily
                                )
                                Text(
                                    text = "ভালোবাসার মিল",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontFamily = HindSiliguriFontFamily
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Names Badge
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color(0xFFFFF0F5),
                        border = CardDefaults.outlinedCardBorder().copy(
                            brush = Brush.horizontalGradient(
                                listOf(Color(0xFFE91E63).copy(alpha = 0.3f), GoldAccent.copy(alpha = 0.3f))
                            )
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                        ) {
                            Text(
                                text = name1.trim(),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontFamily = HindSiliguriFontFamily
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = null,
                                tint = Color(0xFFE91E63),
                                modifier = Modifier
                                    .size(20.dp)
                                    .scale(heartScale)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = name2.trim(),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontFamily = HindSiliguriFontFamily
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Romantic Love Quote Box
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colorScheme.surface,
                        border = CardDefaults.outlinedCardBorder().copy(
                            brush = Brush.horizontalGradient(
                                listOf(GoldAccent, Color(0xFFE91E63))
                            )
                        ),
                        shadowElevation = 4.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "“",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldAccent,
                                lineHeight = 16.sp
                            )
                            Text(
                                text = calculatedMessage,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontFamily = HindSiliguriFontFamily,
                                textAlign = TextAlign.Center,
                                lineHeight = 24.sp
                            )
                            Text(
                                text = "”",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldAccent,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Action Buttons Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Share/Copy Button
                        Button(
                            onClick = {
                                val shareText = "💖 ভালোবাসা ক্যালকুলেটর ফলাফল 💖\n\n" +
                                        "${name1.trim()} 💖 ${name2.trim()}\n" +
                                        "ভালোবাসার মিল: $calculatedScore%\n\n" +
                                        "\"$calculatedMessage\"\n\n" +
                                        "আফিয়া আফরিনের ডিজিটাল অ্যাপ থেকে তৈরি ✨"
                                BengaliUtils.copyToClipboard(context, "Love Score", shareText)
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE91E63)
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("share_love_result")
                        ) {
                            Icon(imageVector = Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "কপি করুন",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = HindSiliguriFontFamily
                            )
                        }

                        // Close Button
                        OutlinedButton(
                            onClick = { showResultDialog = false },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                        ) {
                            Text(
                                text = "বন্ধ করুন",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = HindSiliguriFontFamily
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun getBengaliLoveQuote(score: Int, name1: String, name2: String): String {
    val quotes90Plus = listOf(
        "একদম রাজযোটক সম্পর্ক! হৃদয়ের গভীর থেকে ভালোবাসার অমলিন বন্ধন। $name1 ও $name2 যেন একে অপরের পরম আশ্রয়। 💖✨",
        "তোমাদের ভালোবাসায় রয়েছে স্নিগ্ধতা ও অনাবিল সৌন্দর্য। ভালোবাসার এই মায়াবী বন্ধন চিরকাল অমলিন থাকুক! 🌸💕",
        "শতকরা $score% ভালোবাসা! দুটি মনের মাঝে রয়েছে নিখাদ বিশ্বাস ও গভীর অনুভূতি। রূপকথার মতোই সুন্দর তোমাদের সম্পর্ক। 🌹✨"
    )

    val quotes80Plus = listOf(
        "খুবই সুন্দর ও মিষ্টি একটি সম্পর্ক! কিছুটা অভিমান আর অনেকটা ভালোবাসায় মোড়ানো তোমাদের প্রতিটি মুহূর্ত। 💕✨",
        "তোমাদের অনুভূতির মেলবন্ধন চমৎকার! ভালোবাসা ও ভালোলাগার সুবাস ছড়িয়ে থাকুক তোমাদের দিনগুলোতে। 🌺💖",
        "শতকরা $score% মিল! হৃদয়ের গভীরে লুকিয়ে থাকা সুরগুলো যেন একসাথে বেজে ওঠে। 🎶❤️"
    )

    val quotes75Plus = listOf(
        "ভালোবাসার মিষ্টি এক ক্যানভাস! একটু যত্ন আর বিশ্বাসের ছোঁয়ায় এই সম্পর্ক হয়ে উঠবে অতুলনীয়। 🌷✨",
        "দুটি মনের অপূর্ব মেলবন্ধন! ভালোবাসা হোক প্রতিদিন নতুন করে অনুভবের গল্প। 💖"
    )

    return when {
        score >= 90 -> quotes90Plus.random()
        score >= 80 -> quotes80Plus.random()
        else -> quotes75Plus.random()
    }
}

