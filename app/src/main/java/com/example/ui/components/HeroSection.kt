package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Verified
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.example.data.AfiaData
import com.example.ui.theme.*
import com.example.util.BengaliUtils

@Composable
fun HeroSection(
    onExploreWorldClick: () -> Unit,
    onSpecialBoxClick: () -> Unit
) {
    val context = LocalContext.current

    // State for showing image preview dialog
    var showPreviewDialog by remember { mutableStateOf(false) }

    // Infinite Animations
    val infiniteTransition = rememberInfiniteTransition(label = "hero_anim")

    // 1. Rotating gradient angle
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    // 2. Breathing floating Y offset
    val floatY by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float"
    )

    // 3. Profile Picture Outer Pulse Glow
    val profilePulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.22f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "profile_pulse_scale"
    )

    val profileGlowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.75f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "profile_glow_alpha"
    )

    // 4. Blue Verified Badge Pulse & Floating Effect
    val badgeScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.18f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "badge_scale"
    )

    val badgeFloatY by infiniteTransition.animateFloat(
        initialValue = -4f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "badge_float_y"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .shadow(12.dp, RoundedCornerShape(28.dp)),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Floating Profile Avatar Frame with Blue Verified Badge
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .offset(y = floatY.dp)
                    .padding(top = 8.dp, bottom = 12.dp)
            ) {
                // Outer Pulsing Glow Aura behind Profile Picture
                Box(
                    modifier = Modifier
                        .size(138.dp)
                        .scale(profilePulseScale)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                listOf(
                                    MaterialTheme.colorScheme.primary.copy(alpha = profileGlowAlpha * 0.4f),
                                    GoldAccent.copy(alpha = profileGlowAlpha * 0.3f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                // Animated Glowing Rotating Ring
                Box(
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.sweepGradient(
                                listOf(
                                    MaterialTheme.colorScheme.primary,
                                    GoldAccent,
                                    VerifiedBlueBadge,
                                    MaterialTheme.colorScheme.secondary,
                                    MaterialTheme.colorScheme.primary
                                )
                            )
                        )
                        .rotate(rotationAngle)
                        .padding(4.dp)
                ) {}

                // Main Profile Picture Container
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable { showPreviewDialog = true }
                        .testTag("hero_profile_pic"),
                    contentAlignment = Alignment.Center
                ) {
                    SubcomposeAsyncImage(
                        model = AfiaData.formatDirectImageUrl(AfiaData.PROFILE_IMAGE_URL),
                        contentDescription = "আফিয়া আফরিনের প্রোফাইল ছবি",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                        loading = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = MaterialTheme.colorScheme.primary,
                                    strokeWidth = 2.dp
                                )
                            }
                        },
                        error = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "আফিয়া",
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontFamily = HindSiliguriFontFamily
                                )
                                Text(
                                    text = "আফরিন",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontFamily = HindSiliguriFontFamily
                                )
                            }
                        }
                    )
                }

                // VERIFIED BLUE BADGE (🔵 Blue Verified Tick with Floating & Pulse)
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = (-2).dp, y = (-2 + badgeFloatY).dp)
                        .scale(badgeScale)
                        .testTag("verified_blue_badge")
                ) {
                    // Pulsing Halo Glow around Blue Badge
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(VerifiedBlueBadge.copy(alpha = profileGlowAlpha * 0.5f))
                    )

                    // Blue Badge Shell
                    Surface(
                        shape = CircleShape,
                        color = VerifiedBlueBadge,
                        shadowElevation = 6.dp,
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.Center)
                            .border(2.dp, Color.White, CircleShape)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Filled.Verified,
                                contentDescription = "ব্লু ভেরিফাইড ব্যাজ",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            // Name with verified inline badge
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = AfiaData.PERSON_NAME,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = HindSiliguriFontFamily,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.Filled.Verified,
                    contentDescription = "Verified Badge",
                    tint = VerifiedBlueBadge,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Subtitle Tag
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.AutoAwesome,
                        contentDescription = null,
                        tint = GoldAccent,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "স্বাগতম আমার ডিজিটাল জগতে",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontFamily = HindSiliguriFontFamily
                    )
                }
            }

            // DIRECT CLICKABLE SOCIAL LOGO BAR (Facebook, Instagram, TikTok, Chithi)
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Facebook Logo Button
                    IconButton(
                        onClick = { BengaliUtils.openWebLink(context, AfiaData.SOCIAL_LINKS[0].url) },
                        modifier = Modifier.testTag("hero_facebook_button")
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = FacebookBlue,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(text = "f", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }
                    }

                    // Instagram Logo Button
                    IconButton(
                        onClick = { BengaliUtils.openWebLink(context, AfiaData.SOCIAL_LINKS[1].url) },
                        modifier = Modifier.testTag("hero_instagram_button")
                    ) {
                        Surface(
                            shape = CircleShape,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.linearGradient(
                                            listOf(InstagramGradientStart, InstagramGradientMiddle, InstagramGradientEnd)
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "📷", fontSize = 18.sp)
                            }
                        }
                    }

                    // TikTok Logo Button
                    IconButton(
                        onClick = { BengaliUtils.openWebLink(context, AfiaData.SOCIAL_LINKS[2].url) },
                        modifier = Modifier.testTag("hero_tiktok_button")
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = TikTokBlack,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(text = "🎵", fontSize = 18.sp)
                            }
                        }
                    }

                    // Chithi Box Logo Button
                    IconButton(
                        onClick = { BengaliUtils.openWebLink(context, AfiaData.CHITHI_URL) },
                        modifier = Modifier.testTag("hero_chithi_button")
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(text = "✉️", fontSize = 18.sp)
                            }
                        }
                    }
                }
            }

            // Main Hero Quote Box
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.horizontalGradient(
                        listOf(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), GoldAccent.copy(alpha = 0.5f))
                    )
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
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
                        lineHeight = 20.sp
                    )
                    Text(
                        text = AfiaData.HERO_QUOTE,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontFamily = HindSiliguriFontFamily,
                        textAlign = TextAlign.Center,
                        lineHeight = 26.sp
                    )
                    Text(
                        text = "”",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldAccent,
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Action Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Primary Button: "আমার জগৎ ঘুরে দেখুন"
                Button(
                    onClick = onExploreWorldClick,
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("hero_explore_button")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AutoAwesome,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = AfiaData.HERO_BUTTON_PRIMARY,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = HindSiliguriFontFamily
                        )
                    }
                }

                // Secondary Button: "💌 বিশেষ বক্স"
                OutlinedButton(
                    onClick = onSpecialBoxClick,
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("hero_special_box_button")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = AfiaData.HERO_BUTTON_SECONDARY,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = HindSiliguriFontFamily
                        )
                    }
                }
            }
        }
    }

    // Profile Picture High-Res Dialog Modal
    if (showPreviewDialog) {
        Dialog(onDismissRequest = { showPreviewDialog = false }) {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "প্রোফাইল ছবি",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = HindSiliguriFontFamily
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.Filled.Verified,
                                contentDescription = "Verified",
                                tint = VerifiedBlueBadge,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        IconButton(onClick = { showPreviewDialog = false }) {
                            Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .border(4.dp, VerifiedBlueBadge, CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        SubcomposeAsyncImage(
                            model = AfiaData.formatDirectImageUrl(AfiaData.PROFILE_IMAGE_URL),
                            contentDescription = "প্রোফাইল ছবি",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            loading = {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(32.dp),
                                        color = MaterialTheme.colorScheme.primary,
                                        strokeWidth = 3.dp
                                    )
                                }
                            },
                            error = {
                                Text(
                                    text = "আফিয়া\nআফরিন",
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontFamily = HindSiliguriFontFamily,
                                    textAlign = TextAlign.Center
                                )
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedButton(
                        onClick = { showPreviewDialog = false },
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(
                            text = "বন্ধ করুন",
                            fontFamily = HindSiliguriFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

