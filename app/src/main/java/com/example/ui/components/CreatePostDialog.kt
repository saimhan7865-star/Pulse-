package com.example.ui.components

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.User
import com.example.ui.theme.PulseCyan
import com.example.ui.theme.PulseFacebookBlue
import com.example.ui.theme.PulseNeonPink
import com.example.ui.theme.PulseYouTubeRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostDialog(
    currentUser: User,
    initialMode: String,
    onDismiss: () -> Unit,
    onCreatePost: (content: String, gradientIndex: Int?) -> Unit,
    onCreateReel: (caption: String, hashtag: String, audioName: String) -> Unit
) {
    var mode by remember { mutableStateOf(initialMode) } // "POST", "REEL", "VIDEO"

    // Post state
    var postContent by remember { mutableStateOf("") }
    var selectedGradientIndex by remember { mutableStateOf<Int?>(null) }

    // Reel state
    var reelCaption by remember { mutableStateOf("") }
    var reelHashtag by remember { mutableStateOf("#PulseVibes") }
    var reelAudio by remember { mutableStateOf("Original Audio - Alex Vance") }

    // Video state
    var videoTitle by remember { mutableStateOf("") }
    var videoCategory by remember { mutableStateOf("Tech & AI") }

    val gradients = listOf(
        listOf(PulseNeonPink, Color(0xFF8A2BE2)),
        listOf(PulseCyan, Color(0xFF4FACFE)),
        listOf(Color(0xFFFF8A00), Color(0xFFE52E71)),
        listOf(PulseFacebookBlue, Color(0xFF00C9FF))
    )

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Mode Selector Tabs (Post, Reel, Video)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CreationTabItem(
                    title = "Post (FB)",
                    icon = Icons.Outlined.DynamicFeed,
                    isSelected = mode == "POST",
                    selectedColor = PulseFacebookBlue,
                    onClick = { mode = "POST" }
                )
                CreationTabItem(
                    title = "Reel (TikTok)",
                    icon = Icons.Outlined.ElectricBolt,
                    isSelected = mode == "REEL",
                    selectedColor = PulseNeonPink,
                    onClick = { mode = "REEL" }
                )
                CreationTabItem(
                    title = "Video (YT)",
                    icon = Icons.Outlined.VideoLibrary,
                    isSelected = mode == "VIDEO",
                    selectedColor = PulseYouTubeRed,
                    onClick = { mode = "VIDEO" }
                )
            }

            // Author Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color(currentUser.avatarColor)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = currentUser.name.take(1),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                }

                Column {
                    Text(
                        text = currentUser.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Public,
                                contentDescription = "Public",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = "Public",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            when (mode) {
                "POST" -> {
                    // Facebook-style Composer
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 140.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .then(
                                if (selectedGradientIndex != null) {
                                    Modifier.background(
                                        Brush.linearGradient(gradients[selectedGradientIndex!!])
                                    )
                                } else {
                                    Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
                                }
                            )
                            .padding(14.dp)
                    ) {
                        TextField(
                            value = postContent,
                            onValueChange = { postContent = it },
                            placeholder = {
                                Text(
                                    "What's on your mind, ${currentUser.name.split(" ").first()}?",
                                    color = if (selectedGradientIndex != null) Color.White.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = if (selectedGradientIndex != null) 18.sp else 15.sp,
                                    fontWeight = if (selectedGradientIndex != null) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedTextColor = if (selectedGradientIndex != null) Color.White else MaterialTheme.colorScheme.onSurface,
                                unfocusedTextColor = if (selectedGradientIndex != null) Color.White else MaterialTheme.colorScheme.onSurface,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("create_post_input")
                        )
                    }

                    // Background Gradient Palette Selector
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Card Style:",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        // Plain text option
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .clickable { selectedGradientIndex = null },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Aa", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface)
                        }

                        // Gradient presets
                        gradients.forEachIndexed { idx, grad ->
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(Brush.linearGradient(grad))
                                    .clickable { selectedGradientIndex = idx }
                            )
                        }
                    }

                    Button(
                        onClick = { onCreatePost(postContent, selectedGradientIndex) },
                        enabled = postContent.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PulseFacebookBlue
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("publish_post_button")
                    ) {
                        Text("Post to Feed", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                }

                "REEL" -> {
                    // TikTok-style Short Composer
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = reelCaption,
                            onValueChange = { reelCaption = it },
                            label = { Text("Reel Caption") },
                            placeholder = { Text("Write an engaging hook for your short video...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("reel_caption_input")
                        )

                        OutlinedTextField(
                            value = reelHashtag,
                            onValueChange = { reelHashtag = it },
                            label = { Text("Trending Hashtags") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = reelAudio,
                            onValueChange = { reelAudio = it },
                            label = { Text("Audio Track") },
                            leadingIcon = {
                                Icon(Icons.Outlined.MusicNote, contentDescription = "Audio")
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = { onCreateReel(reelCaption, reelHashtag, reelAudio) },
                            enabled = reelCaption.isNotBlank(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PulseNeonPink
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("publish_reel_button")
                        ) {
                            Text("Publish Short Reel ⚡", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                    }
                }

                "VIDEO" -> {
                    // YouTube-style Video Upload
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = videoTitle,
                            onValueChange = { videoTitle = it },
                            label = { Text("Video Title") },
                            placeholder = { Text("e.g. My Ultimate Tech Setup Tour 2026") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = videoCategory,
                            onValueChange = { videoCategory = it },
                            label = { Text("Category") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                onCreatePost("Uploaded a new video: $videoTitle 🎥 Watch now on the Watch tab!", null)
                            },
                            enabled = videoTitle.isNotBlank(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PulseYouTubeRed
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            Text("Upload Video 🎥", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RowScope.CreationTabItem(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    selectedColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) selectedColor.copy(alpha = 0.2f) else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (isSelected) selectedColor else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) selectedColor else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
