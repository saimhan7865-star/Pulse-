package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.SampleData
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed class ActiveCommentTarget {
    data class PostTarget(val post: Post) : ActiveCommentTarget()
    data class ReelTarget(val reel: ShortReel) : ActiveCommentTarget()
    data class VideoTarget(val video: Video) : ActiveCommentTarget()
}

data class SocialUiState(
    val currentTab: MainTab = MainTab.FEED,
    val feedFilter: FeedFilter = FeedFilter.FOR_YOU,
    val currentUser: User = SampleData.currentUser,
    val stories: List<Story> = SampleData.stories,
    val posts: List<Post> = SampleData.posts,
    val shortReels: List<ShortReel> = SampleData.shortReels,
    val videos: List<Video> = SampleData.videos,
    val notifications: List<NotificationItem> = SampleData.notifications,
    val selectedCategory: String = "All",
    // Active Modals & Overlays
    val activeStoryIndex: Int? = null,
    val activeVideoDetail: Video? = null,
    val isVideoPlaying: Boolean = true,
    val videoPlayProgress: Float = 0.25f,
    val activeCommentTarget: ActiveCommentTarget? = null,
    val shareDialogItemName: String? = null,
    val showCreateModal: Boolean = false,
    val createMode: String = "POST", // "POST", "REEL", "VIDEO"
    val isSearchOpen: Boolean = false,
    val searchQuery: String = "",
    val followedCreators: Set<String> = setOf("u2")
)

class SocialVideoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SocialUiState())
    val uiState: StateFlow<SocialUiState> = _uiState.asStateFlow()

    fun selectTab(tab: MainTab) {
        _uiState.update { it.copy(currentTab = tab) }
    }

    fun setFeedFilter(filter: FeedFilter) {
        _uiState.update { it.copy(feedFilter = filter) }
    }

    fun selectCategory(category: String) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    // Story Viewer
    fun openStory(index: Int) {
        _uiState.update {
            val updatedStories = it.stories.mapIndexed { idx, s ->
                if (idx == index) s.copy(isViewed = true) else s
            }
            it.copy(activeStoryIndex = index, stories = updatedStories)
        }
    }

    fun closeStory() {
        _uiState.update { it.copy(activeStoryIndex = null) }
    }

    fun nextStory() {
        val current = _uiState.value.activeStoryIndex ?: return
        if (current + 1 < _uiState.value.stories.size) {
            openStory(current + 1)
        } else {
            closeStory()
        }
    }

    fun prevStory() {
        val current = _uiState.value.activeStoryIndex ?: return
        if (current > 0) {
            openStory(current - 1)
        }
    }

    // Video Detail / Player
    fun openVideoDetail(video: Video) {
        _uiState.update { it.copy(activeVideoDetail = video, isVideoPlaying = true) }
    }

    fun closeVideoDetail() {
        _uiState.update { it.copy(activeVideoDetail = null) }
    }

    fun toggleVideoPlay() {
        _uiState.update { it.copy(isVideoPlaying = !it.isVideoPlaying) }
    }

    fun setVideoProgress(progress: Float) {
        _uiState.update { it.copy(videoPlayProgress = progress.coerceIn(0f, 1f)) }
    }

    // Facebook Reactions
    fun reactToPost(postId: String, reaction: ReactionType) {
        _uiState.update { state ->
            val updatedPosts = state.posts.map { post ->
                if (post.id == postId) {
                    val currentReactions = post.reactions.toMutableMap()
                    val prevReaction = post.userReaction

                    if (prevReaction == reaction) {
                        // Undo reaction
                        val count = currentReactions[reaction] ?: 1
                        if (count <= 1) currentReactions.remove(reaction) else currentReactions[reaction] = count - 1
                        post.copy(reactions = currentReactions, userReaction = null)
                    } else {
                        // If there was a previous reaction, decrement it
                        if (prevReaction != null) {
                            val count = currentReactions[prevReaction] ?: 1
                            if (count <= 1) currentReactions.remove(prevReaction) else currentReactions[prevReaction] = count - 1
                        }
                        // Increment new reaction
                        currentReactions[reaction] = (currentReactions[reaction] ?: 0) + 1
                        post.copy(reactions = currentReactions, userReaction = reaction)
                    }
                } else post
            }
            state.copy(posts = updatedPosts)
        }
    }

    // TikTok Short Reels
    fun toggleLikeReel(reelId: String) {
        _uiState.update { state ->
            val updatedReels = state.shortReels.map { reel ->
                if (reel.id == reelId) {
                    val newLiked = !reel.isLiked
                    val newCount = if (newLiked) reel.likesCount + 1 else reel.likesCount - 1
                    reel.copy(isLiked = newLiked, likesCount = newCount)
                } else reel
            }
            state.copy(shortReels = updatedReels)
        }
    }

    fun toggleBookmarkReel(reelId: String) {
        _uiState.update { state ->
            val updatedReels = state.shortReels.map { reel ->
                if (reel.id == reelId) {
                    val newBookmarked = !reel.isBookmarked
                    val newCount = if (newBookmarked) reel.bookmarkCount + 1 else reel.bookmarkCount - 1
                    reel.copy(isBookmarked = newBookmarked, bookmarkCount = newCount)
                } else reel
            }
            state.copy(shortReels = updatedReels)
        }
    }

    // Follow Creator
    fun toggleFollowCreator(creatorId: String) {
        _uiState.update { state ->
            val currentFollowed = state.followedCreators.toMutableSet()
            if (currentFollowed.contains(creatorId)) {
                currentFollowed.remove(creatorId)
            } else {
                currentFollowed.add(creatorId)
            }
            state.copy(followedCreators = currentFollowed)
        }
    }

    // YouTube Subscriptions & Video Likes
    fun toggleSubscribeChannel(channelId: String) {
        _uiState.update { state ->
            val updatedVideos = state.videos.map { v ->
                if (v.channel.id == channelId) {
                    v.copy(isSubscribed = !v.isSubscribed)
                } else v
            }
            val updatedActive = if (state.activeVideoDetail?.channel?.id == channelId) {
                state.activeVideoDetail.copy(isSubscribed = !state.activeVideoDetail.isSubscribed)
            } else state.activeVideoDetail
            state.copy(videos = updatedVideos, activeVideoDetail = updatedActive)
        }
    }

    fun toggleLikeVideo(videoId: String) {
        _uiState.update { state ->
            val updatedVideos = state.videos.map { v ->
                if (v.id == videoId) {
                    val newLiked = !v.isLiked
                    val newCount = if (newLiked) v.likesCount + 1 else v.likesCount - 1
                    v.copy(isLiked = newLiked, isDisliked = false, likesCount = newCount)
                } else v
            }
            val updatedActive = if (state.activeVideoDetail?.id == videoId) {
                val newLiked = !state.activeVideoDetail.isLiked
                val newCount = if (newLiked) state.activeVideoDetail.likesCount + 1 else state.activeVideoDetail.likesCount - 1
                state.activeVideoDetail.copy(isLiked = newLiked, isDisliked = false, likesCount = newCount)
            } else state.activeVideoDetail

            state.copy(videos = updatedVideos, activeVideoDetail = updatedActive)
        }
    }

    fun toggleDislikeVideo(videoId: String) {
        _uiState.update { state ->
            val updatedVideos = state.videos.map { v ->
                if (v.id == videoId) {
                    val wasLiked = v.isLiked
                    val newLiked = false
                    val newLikesCount = if (wasLiked) v.likesCount - 1 else v.likesCount
                    v.copy(isLiked = newLiked, isDisliked = !v.isDisliked, likesCount = newLikesCount)
                } else v
            }
            state.copy(videos = updatedVideos)
        }
    }

    // Comments Sheet
    fun openCommentsFor(target: ActiveCommentTarget) {
        _uiState.update { it.copy(activeCommentTarget = target) }
    }

    fun closeComments() {
        _uiState.update { it.copy(activeCommentTarget = null) }
    }

    fun addComment(text: String) {
        if (text.isBlank()) return
        val currentTarget = _uiState.value.activeCommentTarget ?: return
        val newComment = Comment(
            id = "c_${System.currentTimeMillis()}",
            author = _uiState.value.currentUser,
            text = text,
            timeAgo = "Just now",
            likesCount = 0
        )

        _uiState.update { state ->
            when (currentTarget) {
                is ActiveCommentTarget.PostTarget -> {
                    val updatedPosts = state.posts.map { p ->
                        if (p.id == currentTarget.post.id) {
                            p.copy(comments = listOf(newComment) + p.comments)
                        } else p
                    }
                    val updatedPost = updatedPosts.first { it.id == currentTarget.post.id }
                    state.copy(
                        posts = updatedPosts,
                        activeCommentTarget = ActiveCommentTarget.PostTarget(updatedPost)
                    )
                }
                is ActiveCommentTarget.ReelTarget -> {
                    val updatedReels = state.shortReels.map { r ->
                        if (r.id == currentTarget.reel.id) {
                            r.copy(comments = listOf(newComment) + r.comments)
                        } else r
                    }
                    val updatedReel = updatedReels.first { it.id == currentTarget.reel.id }
                    state.copy(
                        shortReels = updatedReels,
                        activeCommentTarget = ActiveCommentTarget.ReelTarget(updatedReel)
                    )
                }
                is ActiveCommentTarget.VideoTarget -> {
                    val updatedVideos = state.videos.map { v ->
                        if (v.id == currentTarget.video.id) {
                            v.copy(comments = listOf(newComment) + v.comments)
                        } else v
                    }
                    val updatedVideo = updatedVideos.first { it.id == currentTarget.video.id }
                    state.copy(
                        videos = updatedVideos,
                        activeVideoDetail = if (state.activeVideoDetail?.id == updatedVideo.id) updatedVideo else state.activeVideoDetail,
                        activeCommentTarget = ActiveCommentTarget.VideoTarget(updatedVideo)
                    )
                }
            }
        }
    }

    // Share Sheet
    fun openShare(itemName: String) {
        _uiState.update { it.copy(shareDialogItemName = itemName) }
    }

    fun closeShare() {
        _uiState.update { it.copy(shareDialogItemName = null) }
    }

    // Creation Modal
    fun openCreateModal(mode: String = "POST") {
        _uiState.update { it.copy(showCreateModal = true, createMode = mode) }
    }

    fun closeCreateModal() {
        _uiState.update { it.copy(showCreateModal = false) }
    }

    fun createPost(content: String, gradientIndex: Int?) {
        if (content.isBlank()) return
        val gradients = listOf(
            listOf(0xFFFF1E56, 0xFF8A2BE2),
            listOf(0xFF00F2FE, 0xFF4FACFE),
            listOf(0xFFFF8A00, 0xFFE52E71),
            listOf(0xFF1877F2, 0xFF00C9FF)
        )
        val selectedGradient = gradientIndex?.let { gradients.getOrNull(it) }
        val newPost = Post(
            id = "p_${System.currentTimeMillis()}",
            author = _uiState.value.currentUser,
            timeAgo = "Just now",
            content = content,
            coloredBgGradient = selectedGradient,
            reactions = mapOf(ReactionType.LIKE to 1),
            userReaction = ReactionType.LIKE,
            comments = emptyList(),
            shareCount = 0
        )
        _uiState.update { it.copy(posts = listOf(newPost) + it.posts, showCreateModal = false) }
    }

    fun createReel(caption: String, hashtag: String, audioName: String) {
        if (caption.isBlank()) return
        val newReel = ShortReel(
            id = "r_${System.currentTimeMillis()}",
            creator = _uiState.value.currentUser,
            caption = caption,
            hashtags = listOf(hashtag.ifBlank { "#Pulse" }, "#Creator", "#Viral"),
            audioTitle = audioName.ifBlank { "Original Sound - Alex Vance" },
            audioArtist = "Alex Vance",
            mediaRes = SampleData.shortReels.first().mediaRes,
            likesCount = 1,
            isLiked = true
        )
        _uiState.update { it.copy(shortReels = listOf(newReel) + it.shortReels, showCreateModal = false, currentTab = MainTab.REELS) }
    }

    // Search
    fun toggleSearch(isOpen: Boolean) {
        _uiState.update { it.copy(isSearchOpen = isOpen, searchQuery = "") }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }
}
