package com.example.ui.feed

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.components.FacebookReactionPicker
import com.example.ui.theme.*

@Composable
fun SocialFeedScreen(
    stories: List<Story>,
    posts: List<Post>,
    currentUser: User,
    currentFilter: FeedFilter,
    onFilterChange: (FeedFilter) -> Unit,
    onStoryClick: (Int) -> Unit,
    onPostReaction: (postId: String, reaction: ReactionType) -> Unit,
    onOpenComments: (Post) -> Unit,
    onOpenShare: (Post) -> Unit,
    onOpenCreate: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("social_feed_screen"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Feed Filter Chips (For You, Friends, Trending)
        item {
            FeedFilterRow(
                selectedFilter = currentFilter,
                onFilterSelected = onFilterChange
            )
        }

        // Status Composer Card ("What's on your mind?")
        item {
            StatusComposerCard(
                currentUser = currentUser,
                onClick = onOpenCreate
            )
        }

        // Stories Tray (Facebook-style stories at top)
        item {
            StoriesTray(
                stories = stories,
                onStoryClick = onStoryClick,
                onCreateStoryClick = onOpenCreate
            )
        }

        // Feed Posts
        items(posts, key = { it.id }) { post ->
            SocialPostCard(
                post = post,
                onReaction = { reaction -> onPostReaction(post.id, reaction) },
                onCommentsClick = { onOpenComments(post) },
                onShareClick = { onOpenShare(post) }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun FeedFilterRow(
    selectedFilter: FeedFilter,
    onFilterSelected: (FeedFilter) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FeedFilter.values().forEach { filter ->
            val isSelected = selectedFilter == filter
            val label = when (filter) {
                FeedFilter.FOR_YOU -> "For You"
                FeedFilter.FRIENDS -> "Friends"
                FeedFilter.TRENDING -> "Trending 🔥"
            }
            FilterChip(
                selected = isSelected,
                onClick = { onFilterSelected(filter) },
                label = {
                    Text(
                        text = label,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 13.sp
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = PulseFacebookBlue.copy(alpha = 0.2f),
                    selectedLabelColor = PulseFacebookBlue,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                border = null,
                shape = RoundedCornerShape(20.dp)
            )
        }
    }
}

@Composable
fun StatusComposerCard(
    currentUser: User,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onClick)
                    .testTag("status_composer_bar")
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(currentUser.avatarColor)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = currentUser.name.take(1),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "What's on your mind, ${currentUser.name.split(" ").first()}?",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                    )
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

            // Action Pills: Photo, Reel, Feeling
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ComposerActionChip(
                    icon = Icons.Outlined.PhotoLibrary,
                    label = "Photo",
                    tint = Color(0xFF4CAF50),
                    onClick = onClick
                )
                ComposerActionChip(
                    icon = Icons.Outlined.ElectricBolt,
                    label = "Reel",
                    tint = PulseNeonPink,
                    onClick = onClick
                )
                ComposerActionChip(
                    icon = Icons.Outlined.SentimentSatisfiedAlt,
                    label = "Feeling",
                    tint = Color(0xFFFFB300),
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
private fun ComposerActionChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    tint: Color,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = tint,
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun StoriesTray(
    stories: List<Story>,
    onStoryClick: (Int) -> Unit,
    onCreateStoryClick: () -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        itemsIndexed(stories) { index, story ->
            if (index == 0) {
                // Add Story Card
                CreateStoryCard(
                    user = story.author,
                    onClick = onCreateStoryClick
                )
            } else {
                StoryCard(
                    story = story,
                    onClick = { onStoryClick(index) }
                )
            }
        }
    }
}

@Composable
fun CreateStoryCard(
    user: User,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .size(width = 105.dp, height = 160.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag("create_story_card"),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.65f)
                    .background(Color(user.avatarColor)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = user.name.take(1),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                )
            }

            // Plus icon overlay at junction
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = 18.dp)
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(PulseFacebookBlue)
                    .border(2.dp, MaterialTheme.colorScheme.surfaceVariant, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Create Story",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                text = "Add to Story",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp)
            )
        }
    }
}

@Composable
fun StoryCard(
    story: Story,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .size(width = 105.dp, height = 160.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag("story_card_${story.id}"),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (story.mediaRes != null) {
                Image(
                    painter = painterResource(id = story.mediaRes),
                    contentDescription = story.caption,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.verticalGradient(story.gradientColors.map { Color(it) }))
                )
            }

            // Gradient shade
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            0.0f to Color.Black.copy(alpha = 0.5f),
                            0.5f to Color.Transparent,
                            1.0f to Color.Black.copy(alpha = 0.75f)
                        )
                    )
            )

            // Story author avatar with glowing ring
            val ringColors = if (!story.isViewed) {
                listOf(PulseNeonPink, PulseCyan)
            } else {
                listOf(Color.Gray, Color.DarkGray)
            }

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopStart)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Brush.sweepGradient(ringColors))
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(Color(story.author.avatarColor)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = story.author.name.take(1),
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = story.author.name.split(" ").first(),
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun SocialPostCard(
    post: Post,
    onReaction: (ReactionType) -> Unit,
    onCommentsClick: () -> Unit,
    onShareClick: () -> Unit
) {
    var showReactionPicker by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("post_card_${post.id}"),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Post Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(Color(post.author.avatarColor)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = post.author.name.take(1),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                    }

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = post.author.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            if (post.author.isVerified) {
                                Icon(
                                    imageVector = Icons.Outlined.CheckCircle,
                                    contentDescription = "Verified",
                                    tint = PulseCyan,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = post.timeAgo,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "•",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Icon(
                                imageVector = Icons.Filled.Public,
                                contentDescription = "Public",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(11.dp)
                            )
                        }
                    }
                }

                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.MoreHoriz,
                        contentDescription = "Post options",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Post Content Text / Colored Gradient Canvas
            if (post.coloredBgGradient != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Brush.linearGradient(post.coloredBgGradient.map { Color(it) }))
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = post.content,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        lineHeight = 28.sp
                    )
                }
            } else {
                Text(
                    text = post.content,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 22.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )

                if (post.mediaRes != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        painter = painterResource(id = post.mediaRes),
                        contentDescription = "Post Media",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp)
                    )
                }
            }

            // Reactions & Comments Counts Summary
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Top Reactions Preview
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    val emojis = post.reactions.keys.take(3).map { it.emoji }
                    if (emojis.isNotEmpty()) {
                        Text(
                            text = emojis.joinToString(" "),
                            fontSize = 14.sp
                        )
                        Text(
                            text = "${post.totalReactions}",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Comments & Shares Count
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    if (post.comments.isNotEmpty()) {
                        Text(
                            text = "${post.comments.size} comments",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.clickable(onClick = onCommentsClick)
                        )
                    }
                    if (post.shareCount > 0) {
                        Text(
                            text = "${post.shareCount} shares",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.clickable(onClick = onShareClick)
                        )
                    }
                }
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // Bottom Action Bar: Like (with Reaction Picker), Comment, Share
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    // Like Action with Tap & Long-Press
                    Box(
                        modifier = Modifier
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onTap = {
                                        if (post.userReaction != null) {
                                            onReaction(post.userReaction)
                                        } else {
                                            onReaction(ReactionType.LIKE)
                                        }
                                    },
                                    onLongPress = {
                                        showReactionPicker = true
                                    }
                                )
                            }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .testTag("post_like_button_${post.id}")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            if (post.userReaction != null) {
                                Text(text = post.userReaction.emoji, fontSize = 18.sp)
                                Text(
                                    text = post.userReaction.label,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = when (post.userReaction) {
                                        ReactionType.LIKE -> ReactionLikeBlue
                                        ReactionType.LOVE -> ReactionLoveRed
                                        else -> ReactionCareYellow
                                    }
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Outlined.ThumbUp,
                                    contentDescription = "Like",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "Like",
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    // Comment Action
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier
                            .clickable(onClick = onCommentsClick)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .testTag("post_comment_button_${post.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ChatBubbleOutline,
                            contentDescription = "Comment",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Comment",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Share Action
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier
                            .clickable(onClick = onShareClick)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .testTag("post_share_button_${post.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = "Share",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Share",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Facebook Reaction Picker Popup
                androidx.compose.animation.AnimatedVisibility(
                    visible = showReactionPicker,
                    enter = fadeIn(),
                    exit = fadeOut(),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = 16.dp, y = (-48).dp)
                ) {
                    FacebookReactionPicker(
                        onReactionSelected = { reaction ->
                            onReaction(reaction)
                            showReactionPicker = false
                        },
                        onDismiss = { showReactionPicker = false }
                    )
                }
            }
        }
    }
}
