package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.data.AfiaData
import com.example.ui.components.*
import com.example.ui.theme.MyApplicationTheme
import com.example.util.AmbientSoundPlayer
import com.example.util.BengaliUtils
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val soundPlayer = AmbientSoundPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var darkThemeOverride by remember { mutableStateOf<Boolean?>(null) }
            val systemInDark = isSystemInDarkTheme()
            val isDarkTheme = darkThemeOverride ?: systemInDark

            var isSoundPlaying by remember { mutableStateOf(false) }
            val coroutineScope = rememberCoroutineScope()
            val context = LocalContext.current

            var selectedSection by remember { mutableStateOf(AfiaSection.HOME) }
            var showLetterComposer by remember { mutableStateOf(false) }

            val scrollState = rememberScrollState()

            MyApplicationTheme(darkTheme = isDarkTheme) {
                Scaffold(
                    topBar = {
                        TopBarNav(
                            selectedSection = selectedSection,
                            onSectionSelected = { section ->
                                selectedSection = section
                                coroutineScope.launch {
                                    // Scroll position mapping based on section
                                    val targetScroll = when (section) {
                                        AfiaSection.HOME -> 0
                                        AfiaSection.ABOUT -> 450
                                        AfiaSection.WORLD -> 1100
                                        AfiaSection.LOVE_CALCULATOR -> 1900
                                        AfiaSection.SPECIAL_BOX -> 2700
                                        AfiaSection.CONTACT -> 3400
                                    }
                                    scrollState.animateScrollTo(targetScroll)
                                }
                            },
                            isDarkTheme = isDarkTheme,
                            onToggleTheme = { darkThemeOverride = !isDarkTheme },
                            isSoundPlaying = isSoundPlaying,
                            onToggleSound = {
                                if (isSoundPlaying) {
                                    soundPlayer.stop()
                                    isSoundPlaying = false
                                } else {
                                    soundPlayer.start(coroutineScope)
                                    isSoundPlaying = true
                                }
                            },
                            onOpenLetterDialog = { showLetterComposer = true }
                        )
                    },
                    bottomBar = {
                        BottomNavRail(
                            selectedSection = selectedSection,
                            onSectionSelected = { section ->
                                selectedSection = section
                                coroutineScope.launch {
                                    val targetScroll = when (section) {
                                        AfiaSection.HOME -> 0
                                        AfiaSection.ABOUT -> 450
                                        AfiaSection.WORLD -> 1100
                                        AfiaSection.LOVE_CALCULATOR -> 1900
                                        AfiaSection.SPECIAL_BOX -> 2700
                                        AfiaSection.CONTACT -> 3400
                                    }
                                    scrollState.animateScrollTo(targetScroll)
                                }
                            }
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        // Ambient Animated Falling Petals Canvas
                        PetalsCanvas(
                            modifier = Modifier.fillMaxSize(),
                            isDark = isDarkTheme
                        )

                        // Main Scrollable Website Page
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(scrollState)
                                .padding(vertical = 12.dp)
                        ) {
                            // Section 1: Hero
                            HeroSection(
                                onExploreWorldClick = {
                                    selectedSection = AfiaSection.WORLD
                                    coroutineScope.launch { scrollState.animateScrollTo(1100) }
                                },
                                onSpecialBoxClick = {
                                    selectedSection = AfiaSection.SPECIAL_BOX
                                    coroutineScope.launch { scrollState.animateScrollTo(2700) }
                                }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Section 2: About Me
                            AboutSection()

                            Spacer(modifier = Modifier.height(16.dp))

                            // Section 3: Her World
                            HerWorldSection()

                            Spacer(modifier = Modifier.height(16.dp))

                            // Section 4: Moments / Gallery
                            GallerySection()

                            Spacer(modifier = Modifier.height(16.dp))

                            // Section 5: Love Calculator
                            LoveCalculatorSection()

                            Spacer(modifier = Modifier.height(16.dp))

                            // Section 5: Special Box
                            SpecialBoxSection(
                                onOpenChithiMeLink = {
                                    BengaliUtils.openWebLink(context, AfiaData.CHITHI_URL)
                                },
                                onOpenComposerDialog = {
                                    showLetterComposer = true
                                }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Section 6: Social / Contact
                            SocialSection()

                            Spacer(modifier = Modifier.height(16.dp))

                            // Section 7: Typography Quote
                            QuoteSection()

                            Spacer(modifier = Modifier.height(12.dp))

                            // Section 8: Footer
                            FooterSection()
                        }
                    }
                }

                // In-App Letter Composer Modal
                if (showLetterComposer) {
                    LetterComposerDialog(
                        onDismiss = { showLetterComposer = false },
                        onOpenChithiMe = {
                            BengaliUtils.openWebLink(context, AfiaData.CHITHI_URL)
                        }
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPlayer.stop()
    }
}
