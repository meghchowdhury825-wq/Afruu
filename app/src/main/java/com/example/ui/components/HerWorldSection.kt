package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AfiaData
import com.example.data.WorldCategory
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.HindSiliguriFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HerWorldSection() {
    var selectedCategory by remember { mutableStateOf<WorldCategory?>(null) }

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
                imageVector = Icons.Filled.Public,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = AfiaData.HER_WORLD_TITLE,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = HindSiliguriFontFamily
            )
        }

        Text(
            text = AfiaData.HER_WORLD_SUBTITLE,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontFamily = HindSiliguriFontFamily,
            modifier = Modifier.padding(bottom = 16.dp, start = 4.dp)
        )

        // Cards List
        AfiaData.WORLD_CATEGORIES.forEach { category ->
            Card(
                onClick = { selectedCategory = category },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .shadow(6.dp, RoundedCornerShape(20.dp))
                    .testTag("world_category_${category.id}"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = category.iconEmoji,
                                fontSize = 24.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = category.title,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontFamily = HindSiliguriFontFamily
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = category.subtitle,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontFamily = HindSiliguriFontFamily
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.Filled.ChevronRight,
                        contentDescription = "বিস্তারিত দেখুন",
                        tint = GoldAccent,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }

    // Modal Bottom Sheet when category is selected
    selectedCategory?.let { category ->
        ModalBottomSheet(
            onDismissRequest = { selectedCategory = null },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 32.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = category.iconEmoji, fontSize = 28.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = category.title,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontFamily = HindSiliguriFontFamily
                            )
                            Text(
                                text = category.subtitle,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontFamily = HindSiliguriFontFamily
                            )
                        }
                    }

                    IconButton(
                        onClick = { selectedCategory = null },
                        modifier = Modifier.testTag("close_category_sheet")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "বন্ধ করুন",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(category.items) { item ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = item.title,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontFamily = HindSiliguriFontFamily
                                    )

                                    if (item.tag.isNotEmpty()) {
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = GoldAccent.copy(alpha = 0.2f)
                                        ) {
                                            Text(
                                                text = item.tag,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = GoldAccent,
                                                fontFamily = HindSiliguriFontFamily,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                }

                                Text(
                                    text = item.subtitle,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontFamily = HindSiliguriFontFamily,
                                    modifier = Modifier.padding(top = 2.dp)
                                )

                                Text(
                                    text = item.description,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontFamily = HindSiliguriFontFamily,
                                    lineHeight = 20.sp,
                                    modifier = Modifier.padding(top = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
