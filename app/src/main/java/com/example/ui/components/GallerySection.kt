package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.SubcomposeAsyncImage
import com.example.data.AfiaData
import com.example.data.GalleryItem
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.HindSiliguriFontFamily
import com.example.util.BengaliUtils

@Composable
fun GallerySection() {
    var selectedImageIndex by remember { mutableStateOf<Int?>(null) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Section Title Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.PhotoLibrary,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = AfiaData.GALLERY_TITLE,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = HindSiliguriFontFamily
            )
        }

        Text(
            text = AfiaData.GALLERY_SUBTITLE,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontFamily = HindSiliguriFontFamily,
            lineHeight = 22.sp,
            modifier = Modifier.padding(bottom = 16.dp, start = 4.dp)
        )

        // Gallery Grid
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val itemsList = AfiaData.GALLERY_ITEMS
            for (i in itemsList.indices step 2) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val item1 = itemsList[i]
                    Box(modifier = Modifier.weight(1f)) {
                        GalleryCardItem(
                            item = item1,
                            onClick = { selectedImageIndex = i }
                        )
                    }

                    if (i + 1 < itemsList.size) {
                        val item2 = itemsList[i + 1]
                        Box(modifier = Modifier.weight(1f)) {
                            GalleryCardItem(
                                item = item2,
                                onClick = { selectedImageIndex = i + 1 }
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }

    // Fullscreen Gallery Lightbox Dialog
    selectedImageIndex?.let { index ->
        val currentItem = AfiaData.GALLERY_ITEMS[index]
        val totalCount = AfiaData.GALLERY_ITEMS.size
        val bengaliCounter = "${BengaliUtils.toBengaliNumerals(index + 1)} / ${BengaliUtils.toBengaliNumerals(totalCount)}"

        Dialog(
            onDismissRequest = { selectedImageIndex = null },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Black.copy(alpha = 0.95f)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Top Bar: Counter & Close Button
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Image Counter in Bengali numerals
                        Surface(
                            shape = CircleShape,
                            color = Color.White.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = bengaliCounter,
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = HindSiliguriFontFamily,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                            )
                        }

                        // "বন্ধ করুন" (Close) Button
                        Button(
                            onClick = { selectedImageIndex = null },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White.copy(alpha = 0.2f),
                                contentColor = Color.White
                            ),
                            shape = CircleShape,
                            modifier = Modifier.testTag("gallery_close_button")
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "বন্ধ করুন",
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "বন্ধ করুন",
                                    fontFamily = HindSiliguriFontFamily,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }

                    // Main Canvas Image Art
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                            .padding(horizontal = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp)
                                .shadow(16.dp, RoundedCornerShape(24.dp)),
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            GalleryMediaDisplay(
                                item = currentItem,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Title & Caption
                        Text(
                            text = currentItem.title,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontFamily = HindSiliguriFontFamily,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = currentItem.caption,
                            fontSize = 15.sp,
                            color = Color.White.copy(alpha = 0.8f),
                            fontFamily = HindSiliguriFontFamily,
                            textAlign = TextAlign.Center,
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Next / Previous Navigation Controls strictly with Bengali Labels
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // "আগের ছবি" (Previous Photo) Button
                            OutlinedButton(
                                onClick = {
                                    val prev = if (index > 0) index - 1 else totalCount - 1
                                    selectedImageIndex = prev
                                },
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = Color.White
                                ),
                                border = ButtonDefaults.outlinedButtonBorder.copy(
                                    brush = Brush.horizontalGradient(listOf(Color.White.copy(alpha = 0.4f), Color.White.copy(alpha = 0.4f)))
                                ),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.testTag("gallery_prev_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ChevronLeft,
                                    contentDescription = "আগের ছবি"
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "আগের ছবি",
                                    fontFamily = HindSiliguriFontFamily,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            // "পরের ছবি" (Next Photo) Button
                            Button(
                                onClick = {
                                    val next = (index + 1) % totalCount
                                    selectedImageIndex = next
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = GoldAccent,
                                    contentColor = Color.Black
                                ),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.testTag("gallery_next_button")
                            ) {
                                Text(
                                    text = "পরের ছবি",
                                    fontFamily = HindSiliguriFontFamily,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Filled.ChevronRight,
                                    contentDescription = "পরের ছবি"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GalleryCardItem(
    item: GalleryItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .shadow(6.dp, RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .testTag("gallery_item_${item.id}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            GalleryMediaDisplay(
                item = item,
                modifier = Modifier.fillMaxSize()
            )

            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f)),
                            startY = 100f
                        )
                    )
            )

            // Bottom Caption
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = GoldAccent.copy(alpha = 0.85f),
                    modifier = Modifier.padding(bottom = 4.dp)
                ) {
                    Text(
                        text = item.category,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontFamily = HindSiliguriFontFamily,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Text(
                    text = item.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontFamily = HindSiliguriFontFamily
                )
            }
        }
    }
}

@Composable
fun GalleryMediaDisplay(
    item: GalleryItem,
    modifier: Modifier = Modifier
) {
    if (!item.imageUrl.isNullOrBlank()) {
        SubcomposeAsyncImage(
            model = AfiaData.formatDirectImageUrl(item.imageUrl),
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            modifier = modifier,
            loading = {
                AestheticArtCanvas(item = item, modifier = Modifier.fillMaxSize())
            },
            error = {
                AestheticArtCanvas(item = item, modifier = Modifier.fillMaxSize())
            }
        )
    } else {
        AestheticArtCanvas(item = item, modifier = modifier)
    }
}

@Composable
fun AestheticArtCanvas(
    item: GalleryItem,
    modifier: Modifier = Modifier
) {
    val primaryColor = Color(item.colorHex)
    val secondaryColor = Color(item.secondaryColorHex)

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Background Gradient
        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(primaryColor, secondaryColor, primaryColor),
                start = Offset(0f, 0f),
                end = Offset(w, h)
            )
        )

        // Abstract Aesthetic Curves
        val path1 = Path().apply {
            moveTo(0f, h * 0.4f)
            cubicTo(w * 0.3f, h * 0.2f, w * 0.7f, h * 0.8f, w, h * 0.5f)
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }
        drawPath(path = path1, color = Color.White.copy(alpha = 0.08f))

        val path2 = Path().apply {
            moveTo(0f, h * 0.6f)
            cubicTo(w * 0.4f, h * 0.9f, w * 0.8f, h * 0.3f, w, h * 0.7f)
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }
        drawPath(path = path2, color = GoldAccent.copy(alpha = 0.12f))

        // Sun / Moon Glowing Circle
        drawCircle(
            color = GoldAccent.copy(alpha = 0.25f),
            radius = h * 0.22f,
            center = Offset(w * 0.75f, h * 0.3f)
        )
    }
}
