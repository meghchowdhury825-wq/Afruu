package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AfiaData
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.HindSiliguriFontFamily
import com.example.util.BengaliUtils

@Composable
fun QuoteSection() {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .shadow(12.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.85f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            listOf(MaterialTheme.colorScheme.primary, GoldAccent)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.FormatQuote,
                    contentDescription = "উক্তি",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Large, Elegant Typography Quote
            Text(
                text = AfiaData.FEATURED_BIG_QUOTE,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = HindSiliguriFontFamily,
                textAlign = TextAlign.Center,
                lineHeight = 34.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        BengaliUtils.copyToClipboard(context, "আফিয়া আফরিনের উক্তি", AfiaData.FEATURED_BIG_QUOTE)
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.testTag("copy_quote_button")
                ) {
                    Icon(
                        imageVector = Icons.Filled.ContentCopy,
                        contentDescription = "কপি করুন",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "কপি করুন",
                        fontSize = 13.sp,
                        fontFamily = HindSiliguriFontFamily
                    )
                }

                Button(
                    onClick = {
                        val shareIntent = android.content.Intent().apply {
                            action = android.content.Intent.ACTION_SEND
                            putExtra(android.content.Intent.EXTRA_TEXT, "“${AfiaData.FEATURED_BIG_QUOTE}”\n— ${AfiaData.PERSON_NAME}")
                            type = "text/plain"
                        }
                        context.startActivity(android.content.Intent.createChooser(shareIntent, "শেয়ার করুন"))
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldAccent,
                        contentColor = androidx.compose.ui.graphics.Color.Black
                    ),
                    modifier = Modifier.testTag("share_quote_button")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "শেয়ার করুন",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "শেয়ার করুন",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = HindSiliguriFontFamily
                    )
                }
            }
        }
    }
}

@Composable
fun FooterSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 20.dp, bottom = 36.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalDivider(
            modifier = Modifier
                .width(120.dp)
                .padding(bottom = 20.dp),
            color = GoldAccent.copy(alpha = 0.5f),
            thickness = 1.dp
        )

        Text(
            text = AfiaData.FOOTER_NAME,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = HindSiliguriFontFamily
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = AfiaData.FOOTER_SUBTITLE,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontFamily = HindSiliguriFontFamily
        )

        Spacer(modifier = Modifier.height(12.dp))

        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(
                    text = AfiaData.FOOTER_LOVE,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontFamily = HindSiliguriFontFamily
                )
            }
        }
    }
}
