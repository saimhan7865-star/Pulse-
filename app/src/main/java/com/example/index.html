package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.SampleData
import com.example.model.MainTab
import com.example.ui.components.*
import com.example.ui.feed.SocialFeedScreen
import com.example.ui.notifications.NotificationsScreen
import com.example.ui.profile.ProfileScreen
import com.example.ui.reels.ReelsScreen
import com.example.ui.search.SearchScreen
import com.example.ui.theme.*
import com.example.ui.watch.VideoDetailView
import com.example.ui.watch.WatchScreen
import com.example.viewmodel.ActiveCommentTarget
import com.example.viewmodel.SocialVideoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                PulseApp()
            }
        }
    }
}

@Composable
fun PulseApp(
    viewModel: SocialVideoViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val unreadNotifs = uiState.notifications.count { !it.isRead }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                // Hide topbar in Reels (like TikTok) or when Story / Video detail is open
                if (uiState.currentTab != MainTab.REELS &&
                    uiState.activeStoryIndex == null &&
                    uiState.activeVideoDetail == null &&
                    !uiState.isSearchOpen
                ) {
                    PulseTopBar(
                        currentTab = uiState.currentTab,
                        unreadNotifCount = unreadNotifs,
                        onSearchClick = { viewModel.toggleSearch(true) },
                        onCreateClick = { viewModel.openCreateModal("POST") },
                        onNotifsClick = { viewModel.selectTab(MainTab.NOTIFS) },
                        onMessagesClick = {
                            Toast.makeText(context, "Direct Messages inbox • 3 new chats", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            },
            bottomBar = {
                // Show bottom bar unless story or fullscreen video detail or search is open
                if (uiState.activeStoryIndex == null &&
                    uiState.activeVideoDetail == null &&
                    !uiState.isSearchOpen
                ) {
                    NavigationBar(
                        containerColor = if (uiState.currentTab == MainTab.REELS) Color.Black.copy(alpha = 0.95f) else MaterialTheme.colorScheme.surface,
                        tonalElevation = 8.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                            .testTag("main_bottom_nav_bar")
                    ) {
                        // 1. Feed (Facebook)
                        NavigationBarItem(
                            selected = uiState.currentTab == MainTab.FEED,
                            onClick = { viewModel.selectTab(MainTab.FEED) },
                            icon = {
                                Icon(
                                    imageVector = if (uiState.currentTab == MainTab.FEED) Icons.Filled.DynamicFeed else Icons.Outlined.DynamicFeed,
                                    contentDescription = "Feed"
                                )
                            },
                            label = { Text("Feed", fontSize = 11.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = PulseFacebookBlue,
                                selectedTextColor = PulseFacebookBlue,
                                indicatorColor = PulseFacebookBlue.copy(alpha = 0.15f)
                            ),
                            modifier = Modifier.testTag("nav_feed_tab")
                        )

                        // 2. Reels (TikTok)
                        NavigationBarItem(
                            selected = uiState.currentTab == MainTab.REELS,
                            onClick = { viewModel.selectTab(MainTab.REELS) },
                            icon = {
                                Icon(
                                    imageVector = if (uiState.currentTab == MainTab.REELS) Icons.Filled.ElectricBolt else Icons.Outlined.ElectricBolt,
                                    contentDescription = "Reels"
                                )
                            },
                            label = { Text("Reels", fontSize = 11.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = PulseNeonPink,
                                selectedTextColor = PulseNeonPink,
                                indicatorColor = PulseNeonPink.copy(alpha = 0.15f),
                                unselectedIconColor = if (uiState.currentTab == MainTab.REELS) Color.White.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = if (uiState.currentTab == MainTab.REELS) Color.White.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            modifier = Modifier.testTag("nav_reels_tab")
                        )

                        // 3. Watch (YouTube)
                        NavigationBarItem(
                            selected = uiState.currentTab == MainTab.WATCH,
                            onClick = { viewModel.selectTab(MainTab.WATCH) },
                            icon = {
                                Icon(
                                    imageVector = if (uiState.currentTab == MainTab.WATCH) Icons.Filled.VideoLibrary else Icons.Outlined.VideoLibrary,
                                    contentDescription = "Watch"
                                )
                            },
                            label = { Text("Watch", fontSize = 11.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = PulseYouTubeRed,
                                selectedTextColor = PulseYouTubeRed,
                                indicatorColor = PulseYouTubeRed.copy(alpha = 0.15f)
                            ),
                            modifier = Modifier.testTag("nav_watch_tab")
                        )

                        // 4. Notifications / Activity
                        NavigationBarItem(
                            selected = uiState.currentTab == MainTab.NOTIFS,
                            onClick = { viewModel.selectTab(MainTab.NOTIFS) },
                            icon = {
                                BadgedBox(
                                    badge = {
                                        if (unreadNotifs > 0) {
                                            Badge(containerColor = PulseNeonPink) {
                                                Text("$unreadNotifs")
                                            }
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (uiState.currentTab == MainTab.NOTIFS) Icons.Filled.Notifications else Icons.Outlined.Notifications,
                                        contentDescription = "Activity"
                                    )
                                }
                            },
                            label = { Text("Activity", fontSize = 11.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = PulseNeonPink,
                                selectedTextColor = PulseNeonPink,
                                indicatorColor = PulseNeonPink.copy(alpha = 0.15f)
                            ),
                            modifier = Modifier.testTag("nav_notifs_tab")
                        )

                        // 5. Profile
                        NavigationBarItem(
                            selected = uiState.currentTab == MainTab.PROFILE,
                            onClick = { viewModel.selectTab(MainTab.PROFILE) },
                            icon = {
                                Icon(
                                    imageVector = if (uiState.currentTab == MainTab.PROFILE) Icons.Filled.Person else Icons.Outlined.Person,
                                    contentDescription = "Profile"
                                )
                            },
                            label = { Text("Profile", fontSize = 11.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = PulseCyan,
                                selectedTextColor = PulseCyan,
                                indicatorColor = PulseCyan.copy(alpha = 0.15f)
                            ),
                            modifier = Modifier.testTag("nav_profile_tab")
                        )
                    }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = if (uiState.currentTab == MainTab.REELS) 0.dp else paddingValues.calculateTopPadding(),
                        bottom = 0.dp
                    )
            ) {
                when (uiState.currentTab) {
                    MainTab.FEED -> {
                        SocialFeedScreen(
                            stories = uiState.stories,
                            posts = uiState.posts,
                            currentUser = uiState.currentUser,
                            currentFilter = uiState.feedFilter,
                            onFilterChange = { viewModel.setFeedFilter(it) },
                            onStoryClick = { viewModel.openStory(it) },
                            onPostReaction = { id, rx -> viewModel.reactToPost(id, rx) },
                            onOpenComments = { viewModel.openCommentsFor(ActiveCommentTarget.PostTarget(it)) },
                            onOpenShare = { viewModel.openShare(it.content.take(30) + "...") },
                            onOpenCreate = { viewModel.openCreateModal("POST") }
                        )
                    }

                    MainTab.REELS -> {
                        ReelsScreen(
                            reels = uiState.shortReels,
                            followedCreators = uiState.followedCreators,
                            onToggleLike = { viewModel.toggleLikeReel(it) },
                            onToggleBookmark = { viewModel.toggleBookmarkReel(it) },
                            onToggleFollow = { viewModel.toggleFollowCreator(it) },
                            onOpenComments = { viewModel.openCommentsFor(ActiveCommentTarget.ReelTarget(it)) },
                            onOpenShare = { viewModel.openShare(it.caption.take(30) + "...") }
                        )
                    }

                    MainTab.WATCH -> {
                        WatchScreen(
                            videos = uiState.videos,
                            categories = SampleData.videoCategories,
                            selectedCategory = uiState.selectedCategory,
                            onCategorySelected = { viewModel.selectCategory(it) },
                            onVideoClick = { viewModel.openVideoDetail(it) },
                            onOpenShare = { viewModel.openShare(it.title) }
                        )
                    }

                    MainTab.NOTIFS -> {
                        NotificationsScreen(notifications = uiState.notifications)
                    }

                    MainTab.PROFILE -> {
                        ProfileScreen(
                            user = uiState.currentUser,
                            userPosts = uiState.posts.filter { it.author.id == uiState.currentUser.id }.ifEmpty { uiState.posts.take(2) },
                            userVideos = uiState.videos,
                            userReels = uiState.shortReels,
                            onVideoClick = { viewModel.openVideoDetail(it) },
                            onOpenCreate = { viewModel.openCreateModal("POST") }
                        )
                    }
                }
            }
        }

        // Fullscreen Story Viewer Overlay
        if (uiState.activeStoryIndex != null) {
            StoryViewer(
                stories = uiState.stories,
                currentIndex = uiState.activeStoryIndex!!,
                onClose = { viewModel.closeStory() },
                onNext = { viewModel.nextStory() },
                onPrev = { viewModel.prevStory() }
            )
        }

        // Fullscreen / Dedicated Video Detail View (YouTube Player)
        if (uiState.activeVideoDetail != null) {
            VideoDetailView(
                video = uiState.activeVideoDetail!!,
                relatedVideos = uiState.videos,
                isPlaying = uiState.isVideoPlaying,
                progress = uiState.videoPlayProgress,
                onClose = { viewModel.closeVideoDetail() },
                onTogglePlay = { viewModel.toggleVideoPlay() },
                onSeek = { viewModel.setVideoProgress(it) },
                onToggleSubscribe = { viewModel.toggleSubscribeChannel(it) },
                onToggleLike = { viewModel.toggleLikeVideo(it) },
                onToggleDislike = { viewModel.toggleDislikeVideo(it) },
                onOpenComments = { viewModel.openCommentsFor(ActiveCommentTarget.VideoTarget(uiState.activeVideoDetail!!)) },
                onOpenShare = { viewModel.openShare(uiState.activeVideoDetail!!.title) },
                onSelectRelatedVideo = { viewModel.openVideoDetail(it) }
            )
        }

        // Interactive Comments Bottom Sheet
        if (uiState.activeCommentTarget != null) {
            CommentsBottomSheet(
                target = uiState.activeCommentTarget!!,
                onDismiss = { viewModel.closeComments() },
                onSendComment = { viewModel.addComment(it) }
            )
        }

        // Share Bottom Sheet
        if (uiState.shareDialogItemName != null) {
            ShareBottomSheet(
                itemName = uiState.shareDialogItemName!!,
                onDismiss = { viewModel.closeShare() }
            )
        }

        // Create Content Sheet (Post / Reel / Video)
        if (uiState.showCreateModal) {
            CreatePostDialog(
                currentUser = uiState.currentUser,
                initialMode = uiState.createMode,
                onDismiss = { viewModel.closeCreateModal() },
                onCreatePost = { text, grad -> viewModel.createPost(text, grad) },
                onCreateReel = { cap, tag, audio -> viewModel.createReel(cap, tag, audio) }
            )
        }

        // Search Screen Overlay
        AnimatedVisibility(
            visible = uiState.isSearchOpen,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
        ) {
            SearchScreen(
                query = uiState.searchQuery,
                onQueryChange = { viewModel.updateSearchQuery(it) },
                onClose = { viewModel.toggleSearch(false) },
                trendingTags = SampleData.trendingTags,
                videos = uiState.videos,
                reels = uiState.shortReels,
                posts = uiState.posts,
                onVideoClick = {
                    viewModel.toggleSearch(false)
                    viewModel.openVideoDetail(it)
                }
            )
        }
    }
}
