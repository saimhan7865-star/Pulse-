package com.example.model

enum class MainTab {
    FEED,    // Facebook style
    REELS,   // TikTok style
    WATCH,   // YouTube style
    NOTIFS,  // Activity
    PROFILE  // Unified Profile
}

enum class FeedFilter {
    FOR_YOU,
    FRIENDS,
    TRENDING
}

enum class ReactionType(val emoji: String, val label: String) {
    LIKE("👍", "Like"),
    LOVE("❤️", "Love"),
    CARE("🥰", "Care"),
    HAHA("😂", "Haha"),
    WOW("😮", "Wow"),
    SAD("😢", "Sad"),
    ANGRY("😡", "Angry")
}

data class User(
    val id: String,
    val name: String,
    val handle: String,
    val avatarRes: Int? = null,
    val avatarColor: Long = 0xFF6366F1,
    val isVerified: Boolean = false,
    val bio: String = "",
    val followersCount: String = "12.4K",
    val followingCount: String = "340",
    val isFollowedByMe: Boolean = false
)

data class Comment(
    val id: String,
    val author: User,
    val text: String,
    val timeAgo: String,
    val likesCount: Int,
    val isLiked: Boolean = false
)

data class Story(
    val id: String,
    val author: User,
    val mediaRes: Int? = null,
    val gradientColors: List<Long> = listOf(0xFFFF1E56, 0xFF8A2BE2),
    val caption: String = "",
    val timeAgo: String = "2h",
    val isViewed: Boolean = false
)

data class Post(
    val id: String,
    val author: User,
    val timeAgo: String,
    val content: String,
    val mediaRes: Int? = null,
    val coloredBgGradient: List<Long>? = null,
    val reactions: Map<ReactionType, Int> = mapOf(ReactionType.LIKE to 0),
    val userReaction: ReactionType? = null,
    val comments: List<Comment> = emptyList(),
    val shareCount: Int = 0,
    val privacy: String = "Public"
) {
    val totalReactions: Int
        get() = reactions.values.sum()
}

data class ShortReel(
    val id: String,
    val creator: User,
    val caption: String,
    val hashtags: List<String>,
    val audioTitle: String,
    val audioArtist: String,
    val mediaRes: Int,
    val likesCount: Int,
    val isLiked: Boolean = false,
    val comments: List<Comment> = emptyList(),
    val shareCount: Int = 0,
    val bookmarkCount: Int = 0,
    val isBookmarked: Boolean = false
)

data class Video(
    val id: String,
    val title: String,
    val channel: User,
    val duration: String,
    val durationSeconds: Int,
    val viewsCount: String,
    val uploadDate: String,
    val thumbnailRes: Int,
    val description: String,
    val category: String,
    val likesCount: Int,
    val isLiked: Boolean = false,
    val isDisliked: Boolean = false,
    val isSubscribed: Boolean = false,
    val comments: List<Comment> = emptyList(),
    val tags: List<String> = emptyList()
)

enum class NotificationType {
    LIKE,
    COMMENT,
    FOLLOW,
    VIDEO_UPLOAD
}

data class NotificationItem(
    val id: String,
    val sender: User,
    val message: String,
    val timeAgo: String,
    val type: NotificationType,
    val isRead: Boolean = false,
    val targetThumbnailRes: Int? = null
)
