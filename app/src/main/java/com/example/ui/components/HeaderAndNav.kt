package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.AfiaData
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.HindSiliguriFontFamily
import com.example.util.BengaliUtils

enum class AfiaSection(val title: String, val iconSelected: @Composable () -> Unit, val iconUnselected: @Composable () -> Unit) {
    HOME("হোম", { Icon(Icons.Filled.Home, contentDescription = "হোম") }, { Icon(Icons.Outlined.Home, contentDescription = "হোম") }),
    ABOUT("সম্পর্কে", { Icon(Icons.Filled.Person, contentDescription = "সম্পর্কে") }, { Icon(Icons.Outlined.Person, contentDescription = "সম্পর্কে") }),
    WORLD("জগৎ", { Icon(Icons.Filled.AutoAwesome, contentDescription = "জগৎ") }, { Icon(Icons.Outlined.AutoAwesome, contentDescription = "জগৎ") }),
    LOVE_CALCULATOR("লাভ ক্যালকুলেটর", { Icon(Icons.Filled.Favorite, contentDescription = "লাভ ক্যালকুলেটর") }, { Icon(Icons.Outlined.FavoriteBorder, contentDescription = "লাভ ক্যালকুলেটর") }),
    SPECIAL_BOX("বিশেষ বক্স", { Icon(Icons.Filled.MarkEmailUnread, contentDescription = "বিশেষ বক্স") }, { Icon(Icons.Outlined.MarkEmailUnread, contentDescription = "বিশেষ বক্স") }),
    CONTACT("যোগাযোগ", { Icon(Icons.Filled.Share, contentDescription = "যোগাযোগ") }, { Icon(Icons.Outlined.Share, contentDescription = "যোগাযোগ") })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarNav(
    selectedSection: AfiaSection,
    onSectionSelected: (AfiaSection) -> Unit,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    isSoundPlaying: Boolean,
    onToggleSound: () -> Unit,
    onOpenLetterDialog: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    var showShareDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (showShareDialog) {
        AppShareDialog(onDismiss = { showShareDialog = false })
    }

    Surface(
        color = MaterialTheme.colorScheme.background.copy(alpha = 0.92f),
        tonalElevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Brand Logo & Title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onSectionSelected(AfiaSection.HOME) }
                    .padding(vertical = 4.dp, horizontal = 6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "আ",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = HindSiliguriFontFamily
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "আফিয়া আফরিন",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontFamily = HindSiliguriFontFamily
                    )
                    Text(
                        text = "ডিজিটাল জগৎ",
                        fontSize = 11.sp,
                        color = GoldAccent,
                        fontFamily = HindSiliguriFontFamily
                    )
                }
            }

            // Right Action Controls
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Share App Link Action Button
                IconButton(
                    onClick = { showShareDialog = true },
                    modifier = Modifier.testTag("top_bar_share_app_button")
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription = "অ্যাপ লিংক শেয়ার করুন",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                // Sound Toggle Button
                IconButton(
                    onClick = onToggleSound,
                    modifier = Modifier.testTag("sound_toggle_button")
                ) {
                    Icon(
                        imageVector = if (isSoundPlaying) Icons.Filled.VolumeUp else Icons.Outlined.VolumeOff,
                        contentDescription = "শব্দ চালু/বন্ধ",
                        tint = if (isSoundPlaying) GoldAccent else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Quick Write Letter Floating Action
                IconButton(
                    onClick = onOpenLetterDialog,
                    modifier = Modifier.testTag("write_letter_icon_button")
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Mail,
                        contentDescription = "চিঠি লিখুন",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                // Theme Toggle Button
                IconButton(
                    onClick = onToggleTheme,
                    modifier = Modifier.testTag("theme_toggle_button")
                ) {
                    Icon(
                        imageVector = if (isDarkTheme) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
                        contentDescription = "থিম পরিবর্তন",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Section Selector Dropdown
                Box {
                    IconButton(
                        onClick = { showMenu = !showMenu },
                        modifier = Modifier.testTag("sections_menu_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "নেভিগেশন মেনু",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false },
                        modifier = Modifier
                            .width(220.dp)
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        AfiaSection.entries.forEach { section ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = section.title,
                                        fontFamily = HindSiliguriFontFamily,
                                        fontWeight = if (selectedSection == section) FontWeight.Bold else FontWeight.Normal,
                                        color = if (selectedSection == section) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                    )
                                },
                                leadingIcon = {
                                    if (selectedSection == section) section.iconSelected() else section.iconUnselected()
                                },
                                onClick = {
                                    onSectionSelected(section)
                                    showMenu = false
                                }
                            )
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "অ্যাপ লিংক কপি ও শেয়ার",
                                    fontFamily = HindSiliguriFontFamily,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Share,
                                    contentDescription = "শেয়ার",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            onClick = {
                                showMenu = false
                                showShareDialog = true
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavRail(
    selectedSection: AfiaSection,
    onSectionSelected: (AfiaSection) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        tonalElevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(12.dp)
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            tonalElevation = 0.dp,
            modifier = Modifier.navigationBarsPadding()
        ) {
            AfiaSection.entries.forEach { section ->
                val isSelected = selectedSection == section
                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onSectionSelected(section) },
                    icon = { if (isSelected) section.iconSelected() else section.iconUnselected() },
                    label = {
                        Text(
                            text = section.title,
                            fontFamily = HindSiliguriFontFamily,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.testTag("nav_item_${section.name.lowercase()}")
                )
            }
        }
    }
}

@Composable
fun AppShareDialog(onDismiss: () -> Unit) {
    val context = LocalContext.current

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 10.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("app_share_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header Icon
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "শেয়ার অ্যাপ",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Title & Description
                Text(
                    text = "অ্যাপ লিংক শেয়ার করুন",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = HindSiliguriFontFamily,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "আফিয়া আফরিনের ডিজিটাল জগৎ অ্যাপটির লিংক আপনার বন্ধুদের সাথে শেয়ার করুন। (নোট: এটি AI Studio প্রিভিউ লিংক, এটি খুলতে AI Studio ক্লাউড রানটাইম বা ডাউনলোডকৃত APK প্রয়োজন হতে পারে)।",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = HindSiliguriFontFamily,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                // App Link Field with Copy & Open Icon
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            BengaliUtils.openWebLink(context, AfiaData.APP_LINK)
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = AfiaData.APP_LINK,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1f),
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = "লিংক খুলুন",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Action Buttons: Copy Link, Open, & Share App
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                BengaliUtils.copyToClipboard(context, "অ্যাপ লিঙ্ক", AfiaData.APP_LINK)
                            },
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("dialog_copy_link_button")
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ContentCopy,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "লিংক কপি",
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
                                .testTag("dialog_open_link_button")
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Share,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "খুলুন",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = HindSiliguriFontFamily
                            )
                        }
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
                            .fillMaxWidth()
                            .testTag("dialog_share_app_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "বন্ধুদের শেয়ার করুন",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = HindSiliguriFontFamily
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.testTag("dialog_close_button")
                ) {
                    Text(
                        text = "বন্ধ করুন",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontFamily = HindSiliguriFontFamily
                    )
                }
            }
        }
    }
}
