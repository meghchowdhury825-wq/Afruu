package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.MarkEmailUnread
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Mail
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AfiaData
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.HindSiliguriFontFamily
import com.example.util.BengaliUtils

@Composable
fun SpecialBoxSection(
    onOpenChithiMeLink: () -> Unit,
    onOpenComposerDialog: () -> Unit
) {
    val context = LocalContext.current

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
            // Header Icon Badge
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.surfaceVariant)
                        )
                    )
                    .border(2.dp, GoldAccent, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "💌", fontSize = 32.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Section Title
            Text(
                text = AfiaData.SPECIAL_BOX_TITLE,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = HindSiliguriFontFamily,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Main Text
            Text(
                text = AfiaData.SPECIAL_BOX_MAIN_TEXT,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = HindSiliguriFontFamily,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Supporting Text
            Text(
                text = AfiaData.SPECIAL_BOX_SUB_TEXT,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontFamily = HindSiliguriFontFamily,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Main Action Button: "✉️ আফিয়াকে একটি চিঠি লিখুন"
            Button(
                onClick = onOpenChithiMeLink,
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("special_box_primary_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = AfiaData.SPECIAL_BOX_BUTTON,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = HindSiliguriFontFamily
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Filled.OpenInNew,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // In-App Letter Writer Option
            OutlinedButton(
                onClick = onOpenComposerDialog,
                shape = RoundedCornerShape(18.dp),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.horizontalGradient(listOf(GoldAccent, MaterialTheme.colorScheme.primary))
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("open_in_app_letter_composer")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Mail,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "অ্যাপ থেকে চিঠি ড্রাফট করুন",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = HindSiliguriFontFamily
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Small Label: "নাম প্রকাশ না করেও চিঠি পাঠানো যাবে"
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = GoldAccent.copy(alpha = 0.15f)
            ) {
                Text(
                    text = "🔒 " + AfiaData.CHITHI_LABEL,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = HindSiliguriFontFamily,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}
