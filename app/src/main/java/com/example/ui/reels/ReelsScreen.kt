package com.example.ui.reels

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ShortReel
import com.example.ui.theme.PulseCyan
import com.example.ui.theme.PulseNeonPink
import kotlinx.coroutines.delay

@Composable
fun ReelsScreen(
    reels: List<ShortReel>,
    followedCreators: Set<String>,
    onToggleLike: (String) -> Unit,
    onToggleBookmark: (String) -> Unit,
    onToggleFollow: (String) -> Unit,
    onOpenComments: (ShortReel) -> Unit,
    onOpenShare: (ShortReel) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { reels.size })
    var selectedTab by remember { mutableStateOf("For You") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .testTag("reels_screen")
    ) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val reel = reels[page]
            SingleReelPage(
                reel = reel,
                isFollowed = followedCreators.contains(reel.creator.id),
                onToggleLike = { onToggleLike(reel.id) },
                onToggleBookmark = { onToggleBookmark(reel.id) },
                onToggleFollow = { onToggleFollow(reel.creator.id) },
                onOpenComments = { onOpenComments(reel) },
                onOpenShare = { onOpenShare(reel) }
            )
        }

        // Top Floating Tabs: Following | For You
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ReelsTopTabItem(
                title = "Following",
                isSelected = selectedTab == "Following",
                onClick = { selectedTab = "Following" }
            )
            Box(
                modifier = Modifier
                    .size(width = 1.dp, height = 12.dp)
                    .background(Color.White.copy(alpha = 0.4f))
            )
            ReelsTopTabItem(
                title = "For You",
                isSelected = selectedTab == "For You",
                onClick = { selectedTab = "For You" }
            )
        }
    }
}

@Composable
fun ReelsTopTabItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Text(
            text = title,
            color = if (isSelected) Color.White else Color.White.copy(alpha = 0.6f),
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            fontSize = 17.sp
        )
        if (isSelected) {
            Box(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .size(width = 24.dp, height = 2.dp)
                    .clip(RoundedCornerShape(1.dp))
                    .background(PulseNeonPink)
            )
        }
    }
}

@Composable
fun SingleReelPage(
    reel: ShortReel,
    isFollowed: Boolean,
    onToggleLike: () -> Unit,
    onToggleBookmark: () -> Unit,
    onToggleFollow: () -> Unit,
    onOpenComments: () -> Unit,
    onOpenShare: () -> Unit
) {
    var isPlaying by remember { mutableStateOf(true) }
    var showHeartBurst by remember { mutableStateOf(false) }
    var showPauseOverlay by remember { mutableStateOf(false) }

    // Vinyl record rotation animation
    val infiniteTransition = rememberInfiniteTransition(label = "vinyl")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing)
        ),
        label = "rotation"
    )

    // Reel progress indicator simulation
    var reelProgress by remember { mutableStateOf(0.1f) }
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            while (true) {
                delay(100)
                reelProgress = (reelProgress + 0.005f).let { if (it > 1f) 0f else it }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        if (!reel.isLiked) {
                            onToggleLike()
                        }
                        showHeartBurst = true
                    },
                    onTap = {
                        isPlaying = !isPlaying
                        showPauseOverlay = true
                    }
                )
            }
            .testTag("reel_item_${reel.id}")
    ) {
        // Fullscreen Reel Image / Video
        Image(
            painter = painterResource(id = reel.mediaRes),
            contentDescription = reel.caption,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Gradient Scrim Overlays for controls and text readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0.0f to Color.Black.copy(alpha = 0.45f),
                        0.3f to Color.Transparent,
                        0.6f to Color.Transparent,
                        1.0f to Color.Black.copy(alpha = 0.85f)
                    )
                )
        )

        // Heart Burst Animation on Double Tap
        LaunchedEffect(showHeartBurst) {
            if (showHeartBurst) {
                delay(700)
                showHeartBurst = false
            }
        }
        AnimatedVisibility(
            visible = showHeartBurst,
            enter = scaleIn(spring(dampingRatio = 0.4f, stiffness = 500f)) + fadeIn(),
            exit = scaleOut() + fadeOut(),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Heart Burst",
                tint = PulseNeonPink,
                modifier = Modifier.size(110.dp)
            )
        }

        // Tap Play/Pause Feedback Icon
        LaunchedEffect(showPauseOverlay) {
            if (showPauseOverlay) {
                delay(500)
                showPauseOverlay = false
            }
        }
        AnimatedVisibility(
            visible = showPauseOverlay,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut(),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Filled.PlayArrow else Icons.Filled.Pause,
                    contentDescription = "Play/Pause",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        // Right-Side Action Sidebar: Creator avatar, Like, Comments, Bookmark, Share, Vinyl Record
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 12.dp, bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Creator Avatar with Follow button
            Box(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .testTag("reel_creator_avatar")
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, Color.White, CircleShape)
                        .background(Color(reel.creator.avatarColor)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = reel.creator.name.take(1),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                // Follow "+" Badge
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = 8.dp)
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(if (isFollowed) PulseCyan else PulseNeonPink)
                        .clickable(onClick = onToggleFollow),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isFollowed) Icons.Filled.Check else Icons.Filled.Add,
                        contentDescription = "Follow",
                        tint = if (isFollowed) Color.Black else Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            // Like Button with heart bounce
            val heartScale by animateFloatAsState(
                targetValue = if (reel.isLiked) 1.25f else 1.0f,
                animationSpec = spring(dampingRatio = 0.5f, stiffness = 400f),
                label = "heart_bounce"
            )

            ReelActionIcon(
                icon = if (reel.isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                label = formatCount(reel.likesCount),
                tint = if (reel.isLiked) PulseNeonPink else Color.White,
                modifier = Modifier.scale(heartScale),
                onClick = onToggleLike,
                testTag = "reel_like_button_${reel.id}"
            )

            // Comment Button
            ReelActionIcon(
                icon = Icons.Outlined.ChatBubble,
                label = "${reel.comments.size}",
                tint = Color.White,
                onClick = onOpenComments,
                testTag = "reel_comment_button_${reel.id}"
            )

            // Bookmark Button
            ReelActionIcon(
                icon = if (reel.isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                label = formatCount(reel.bookmarkCount),
                tint = if (reel.isBookmarked) Color(0xFFFFD700) else Color.White,
                onClick = onToggleBookmark,
                testTag = "reel_bookmark_button_${reel.id}"
            )

            // Share Button
            ReelActionIcon(
                icon = Icons.Filled.Share,
                label = formatCount(reel.shareCount),
                tint = Color.White,
                onClick = onOpenShare,
                testTag = "reel_share_button_${reel.id}"
            )

            // Spinning Vinyl Record / Sound Disc
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1E1E28))
                    .border(2.dp, Color(0xFF333348), CircleShape)
                    .rotate(rotation)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(PulseCyan)
                )
            }
        }

        // Bottom Left Info Overlay: Creator Handle, Caption, Audio Ticker
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth(0.82f)
                .padding(start = 16.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Handle & Verified Badge
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = reel.creator.handle,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                if (reel.creator.isVerified) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = "Verified",
                        tint = PulseCyan,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            // Caption
            Text(
                text = reel.caption,
                color = Color.White,
                fontSize = 14.sp,
                lineHeight = 18.sp,
                maxLines = 3
            )

            // Hashtags
            Text(
                text = reel.hashtags.joinToString(" "),
                color = PulseCyan,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
            )

            // Audio Ticker with rotating note
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(top = 2.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.MusicNote,
                    contentDescription = "Audio Track",
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = "${reel.audioTitle} • ${reel.audioArtist}",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 12.sp
                )
            }
        }

        // Bottom Thin Progress Bar
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(2.dp)
                .background(Color.White.copy(alpha = 0.2f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(reelProgress)
                    .background(PulseNeonPink)
            )
        }
    }
}

@Composable
fun ReelActionIcon(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    tint: Color,
    onClick: () -> Unit,
    testTag: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .clickable(onClick = onClick)
            .testTag(testTag)
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.45f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = tint,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = label,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

private fun formatCount(count: Int): String {
    return when {
        count >= 1_000_000 -> String.format("%.1fM", count / 1_000_000.0)
        count >= 1_000 -> String.format("%.1fK", count / 1_000.0)
        else -> "$count"
    }
}
