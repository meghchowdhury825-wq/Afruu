package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.AfiaData
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.HindSiliguriFontFamily
import com.example.util.BengaliUtils

@Composable
fun LetterComposerDialog(
    onDismiss: () -> Unit,
    onOpenChithiMe: () -> Unit
) {
    var senderName by remember { mutableStateOf("") }
    var letterBody by remember { mutableStateOf("") }
    var isAnonymous by remember { mutableStateOf(true) }
    var showSentSuccess by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .wrapContentHeight()
                .padding(16.dp)
                .shadow(16.dp, RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "✉️", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "আফিয়াকে চিঠি লিখুন",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontFamily = HindSiliguriFontFamily
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_letter_dialog")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "বন্ধ করুন",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                if (showSentSuccess) {
                    // Success View
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "💌", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "চিঠিটি তৈরি হয়েছে!",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = HindSiliguriFontFamily
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "চিঠিটি ক্লিপবোর্ডে কপি করা হয়েছে। এখন chithi.me পেজে গিয়ে পেস্ট করে পাঠিয়ে দিন।",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontFamily = HindSiliguriFontFamily,
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = {
                                onOpenChithiMe()
                                onDismiss()
                            },
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("go_to_chithi_me_button")
                        ) {
                            Text(
                                text = "chithi.me এ পেস্ট করে পাঠান 🚀",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = HindSiliguriFontFamily
                            )
                        }
                    }
                } else {
                    // Compose Form
                    // Anonymous Switch
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "গোপনে (বেনামে) চিঠি পাঠাবেন?",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontFamily = HindSiliguriFontFamily
                        )
                        Switch(
                            checked = isAnonymous,
                            onCheckedChange = { isAnonymous = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                                checkedTrackColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.testTag("anonymous_switch")
                        )
                    }

                    if (!isAnonymous) {
                        OutlinedTextField(
                            value = senderName,
                            onValueChange = { senderName = it },
                            label = {
                                Text(
                                    text = "আপনার নাম",
                                    fontFamily = HindSiliguriFontFamily
                                )
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                                .testTag("sender_name_input")
                        )
                    }

                    // Letter Content Box
                    OutlinedTextField(
                        value = letterBody,
                        onValueChange = { letterBody = it },
                        label = {
                            Text(
                                text = "আপনার মনের কথাগুলো এখানে লিখুন...",
                                fontFamily = HindSiliguriFontFamily
                            )
                        },
                        minLines = 5,
                        maxLines = 8,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .testTag("letter_body_input")
                    )

                    // Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                val textToCopy = if (isAnonymous) letterBody else "প্রেরক: $senderName\n\n$letterBody"
                                BengaliUtils.copyToClipboard(context, "আফিয়ার জন্য চিঠি", textToCopy)
                            },
                            enabled = letterBody.isNotBlank(),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("copy_letter_button")
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ContentCopy,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "কপি করুন",
                                fontSize = 14.sp,
                                fontFamily = HindSiliguriFontFamily
                            )
                        }

                        Button(
                            onClick = {
                                val textToCopy = if (isAnonymous) letterBody else "প্রেরক: $senderName\n\n$letterBody"
                                BengaliUtils.copyToClipboard(context, "আফিয়ার জন্য চিঠি", textToCopy)
                                showSentSuccess = true
                            },
                            enabled = letterBody.isNotBlank(),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("submit_letter_button")
                        ) {
                            Text(
                                text = "প্রস্তুত করুন",
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
