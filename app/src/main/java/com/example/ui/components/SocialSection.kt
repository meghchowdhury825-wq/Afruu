package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AfiaData
import com.example.ui.theme.*
import com.example.util.BengaliUtils

@Composable
fun SocialSection() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Section Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = AfiaData.SOCIAL_TITLE,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = HindSiliguriFontFamily
            )
        }

        Text(
            text = AfiaData.SOCIAL_SUBTITLE,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontFamily = HindSiliguriFontFamily,
            modifier = Modifier.padding(bottom = 16.dp, start = 4.dp)
        )

        // Social Cards List
        AfiaData.SOCIAL_LINKS.forEachIndexed { index, social ->
            val brandColor = when (index) {
                0 -> FacebookBlue
                1 -> InstagramGradientMiddle
                2 -> TikTokBlack
                else -> MaterialTheme.colorScheme.primary
            }

            Card(
                onClick = { BengaliUtils.openWebLink(context, social.url) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .shadow(6.dp, RoundedCornerShape(22.dp))
                    .testTag("social_card_$index"),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = Brush.horizontalGradient(
                        listOf(brandColor.copy(alpha = 0.4f), GeoBorderLight)
                    )
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        // Brand Logo Circle Front
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(
                                    when (index) {
                                        0 -> Brush.linearGradient(listOf(FacebookBlue, Color(0xFF166FE5)))
                                        1 -> Brush.linearGradient(listOf(InstagramGradientStart, InstagramGradientMiddle, InstagramGradientEnd))
                                        2 -> Brush.linearGradient(listOf(Color(0xFF000000), Color(0xFF25F4EE), Color(0xFFFE2C55)))
                                        else -> Brush.linearGradient(listOf(MaterialTheme.colorScheme.primary, GoldAccent))
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (index == 0) {
                                Text(
                                    text = "f",
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            } else {
                                Text(text = social.iconEmoji, fontSize = 24.sp)
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = social.platformName,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontFamily = HindSiliguriFontFamily
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Filled.Verified,
                                    contentDescription = "Verified",
                                    tint = VerifiedBlueBadge,
                                    modifier = Modifier.size(16.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = social.username,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        IconButton(
                            onClick = {
                                BengaliUtils.copyToClipboard(context, social.platformName, social.url)
                            },
                            modifier = Modifier.testTag("copy_social_$index")
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ContentCopy,
                                contentDescription = "কপি করুন",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        IconButton(
                            onClick = { BengaliUtils.openWebLink(context, social.url) },
                            modifier = Modifier.testTag("open_social_$index")
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = brandColor.copy(alpha = 0.15f),
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = "লিংকে যান",
                                        tint = brandColor,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // App Link Share Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
                .shadow(8.dp, RoundedCornerShape(22.dp))
                .testTag("app_link_share_card"),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
            ),
            border = CardDefaults.outlinedCardBorder().copy(
                brush = Brush.horizontalGradient(
                    listOf(MaterialTheme.colorScheme.primary, GoldAccent)
                )
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ডিজিটাল জগৎ অ্যাপ লিংক",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontFamily = HindSiliguriFontFamily
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "অ্যাপের লিঙ্ক বন্ধুদের সাথে শেয়ার করুন অথবা কপি করে নিন।",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = HindSiliguriFontFamily
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            BengaliUtils.copyToClipboard(context, "অ্যাপ লিঙ্ক", AfiaData.APP_LINK)
                        },
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("social_copy_app_link_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ContentCopy,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "কপি",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = HindSiliguriFontFamily
                        )
                    }

                    OutlinedButton(
                        onClick = {
                            BengaliUtils.openWebLink(context, AfiaData.APP_LINK)
                        },
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("social_open_app_link_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "খুলুন",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = HindSiliguriFontFamily
                        )
                    }

                    Button(
                        onClick = {
                            BengaliUtils.shareText(
                                context = context,
                                text = AfiaData.APP_SHARE_MESSAGE,
                                title = "অ্যাপ লিংক শেয়ার করুন"
                            )
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("social_share_app_link_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "শেয়ার",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = HindSiliguriFontFamily
                        )
                    }
                }
            }
        }
    }
}

