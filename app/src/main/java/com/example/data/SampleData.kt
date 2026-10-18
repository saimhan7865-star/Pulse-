package com.example.data

import com.example.R
import com.example.model.*

object SampleData {
    val currentUser = User(
        id = "me",
        name = "Alex Vance",
        handle = "@alexvance",
        avatarRes = R.drawable.pulse_app_icon_1792278389025,
        avatarColor = 0xFFFF1E56,
        isVerified = true,
        bio = "Digital Creator & Tech Explorer 🚀 Crafting reels, long-form reviews & daily thoughts. Welcome to my Pulse feed!",
        followersCount = "142.8K",
        followingCount = "480",
        isFollowedByMe = true
    )

    val userMaya = User(
        id = "u1",
        name = "Maya Cyber",
        handle = "@mayadance",
        avatarRes = R.drawable.reel_cyber_dance_1792278503427,
        avatarColor = 0xFF00F2FE,
        isVerified = true,
        bio = "Street choreography & neon visuals ⚡ Tokyo / LA",
        followersCount = "2.4M",
        followingCount = "180"
    )

    val userTechLab = User(
        id = "u2",
        name = "TechNova Studio",
        handle = "@technova",
        avatarRes = R.drawable.video_tech_studio_1792278504100,
        avatarColor = 0xFFFF0033,
        isVerified = true,
        bio = "Deep dive engineering, creator setups, AI hardware & workstation tours.",
        followersCount = "890K",
        followingCount = "92"
    )

    val userElena = User(
        id = "u3",
        name = "Elena Rostova",
        handle = "@elenatravels",
        avatarRes = R.drawable.post_travel_sunset_1792278519420,
        avatarColor = 0xFFFF8A00,
        isVerified = true,
        bio = "Chasing sunrises on highest peaks 🌄 Travel photographer & author.",
        followersCount = "620K",
        followingCount = "310"
    )

    val userKai = User(
        id = "u4",
        name = "Kai Soundwave",
        handle = "@kaisounds",
        avatarColor = 0xFF8A2BE2,
        isVerified = false,
        bio = "Indie producer & audio designer. Beats for creative minds 🎧",
        followersCount = "45K",
        followingCount = "210"
    )

    val stories = listOf(
        Story(
            id = "s0",
            author = currentUser,
            mediaRes = null,
            gradientColors = listOf(0xFFFF1E56, 0xFF8A2BE2),
            caption = "Add to your story",
            timeAgo = "Now",
            isViewed = false
        ),
        Story(
            id = "s1",
            author = userMaya,
            mediaRes = R.drawable.reel_cyber_dance_1792278503427,
            gradientColors = listOf(0xFF00F2FE, 0xFF4FACFE),
            caption = "Tokyo midnight rehearsal finished! Drops tomorrow 🎬🔥",
            timeAgo = "1h",
            isViewed = false
        ),
        Story(
            id = "s2",
            author = userElena,
            mediaRes = R.drawable.post_travel_sunset_1792278519420,
            gradientColors = listOf(0xFFFF8A00, 0xFFE52E71),
            caption = "Morning above the cloud sea ☀️ Altitude: 3,200m",
            timeAgo = "3h",
            isViewed = false
        ),
        Story(
            id = "s3",
            author = userTechLab,
            mediaRes = R.drawable.video_tech_studio_1792278504100,
            gradientColors = listOf(0xFFFF0033, 0xFFFF7B00),
            caption = "Studio 3.0 reveal is now live on Watch tab! 🚀",
            timeAgo = "5h",
            isViewed = true
        ),
        Story(
            id = "s4",
            author = userKai,
            mediaRes = null,
            gradientColors = listOf(0xFF8A2BE2, 0xFF4A00E0),
            caption = "Working on new synthwave baseline for Maya's upcoming video 🎹",
            timeAgo = "7h",
            isViewed = true
        )
    )

    val sampleComments = listOf(
        Comment("c1", userMaya, "The production quality on this is insane! 🔥 Keep pushing boundaries!", "2h ago", 142, false),
        Comment("c2", userElena, "Literally breathtaking view, need the exact GPS coordinates please! 😍", "3h ago", 89, true),
        Comment("c3", userKai, "That soundtrack transition at 0:45 gave me goosebumps 🎧", "4h ago", 34, false),
        Comment("c4", userTechLab, "Clean cable management and layout. Which monitor arms are those?", "5h ago", 76, false)
    )

    val posts = listOf(
        Post(
            id = "p1",
            author = userElena,
            timeAgo = "2 hours ago",
            content = "Standing above the clouds as golden hour ignites the peaks. Sometimes you just have to wake up at 3:30 AM, brave the freezing climb, and let the horizon remind you how vast this world is. 🌄✨ Would you camp here?",
            mediaRes = R.drawable.post_travel_sunset_1792278519420,
            reactions = mapOf(
                ReactionType.LOVE to 1420,
                ReactionType.LIKE to 890,
                ReactionType.WOW to 312
            ),
            userReaction = ReactionType.LOVE,
            comments = sampleComments,
            shareCount = 284,
            privacy = "Public"
        ),
        Post(
            id = "p2",
            author = userTechLab,
            timeAgo = "4 hours ago",
            content = "After 6 months of planning, testing 14 curved panels, and custom-soldering ambient addressable LEDs... the Dream Creator Studio is officially finished! 🎙️🖥️ Check out the full 4K tour video in the Watch tab right now!",
            mediaRes = R.drawable.video_tech_studio_1792278504100,
            reactions = mapOf(
                ReactionType.LIKE to 2410,
                ReactionType.LOVE to 1830,
                ReactionType.WOW to 420
            ),
            userReaction = null,
            comments = listOf(
                Comment("tc1", currentUser, "That monitor setup is next level! What keyboard is that?", "1h ago", 45, false),
                Comment("tc2", userKai, "Studio acoustic treatment looks spotless! 👏", "3h ago", 18, false)
            ),
            shareCount = 612,
            privacy = "Public"
        ),
        Post(
            id = "p3",
            author = userMaya,
            timeAgo = "6 hours ago",
            content = "⚡ QUICK POLL: Should the next Cyberpunk dance reel be set in Neon Shinjuku or an underground warehouse in Brooklyn? Comment below with your vote! ⬇️",
            coloredBgGradient = listOf(0xFF8A2BE2, 0xFFFF1E56),
            reactions = mapOf(
                ReactionType.LIKE to 3120,
                ReactionType.HAHA to 84,
                ReactionType.CARE to 210
            ),
            userReaction = ReactionType.LIKE,
            comments = listOf(
                Comment("mc1", userElena, "Shinjuku at rainy night hits different! 🌧️", "5h ago", 112, false),
                Comment("mc2", userKai, "Neon Shinjuku with heavy synth bassline hands down! 🔥", "4h ago", 95, true)
            ),
            shareCount = 420,
            privacy = "Public"
        )
    )

    val shortReels = listOf(
        ShortReel(
            id = "r1",
            creator = userMaya,
            caption = "Cyber-street routine in Neo-Tokyo rain! 🌧️ Neon jacket synced to the rhythm of the city. What was your favorite move? ✨",
            hashtags = listOf("#Cyberpunk", "#DanceChallenge", "#NeonVibes", "#TokyoNight", "#PulseReels"),
            audioTitle = "Cyber City Pulse - Original Beat",
            audioArtist = "Maya x Kai Soundwave",
            mediaRes = R.drawable.reel_cyber_dance_1792278503427,
            likesCount = 89420,
            isLiked = false,
            comments = sampleComments,
            shareCount = 12400,
            bookmarkCount = 5310,
            isBookmarked = false
        ),
        ShortReel(
            id = "r2",
            creator = userElena,
            caption = "The sound of wind above the cloud blanket at 3,200m altitude 🏔️ Take a deep breath and pause your scroll for 10 seconds.",
            hashtags = listOf("#NatureTherapy", "#AlpineSunset", "#Wanderlust", "#Peaceful", "#TravelGram"),
            audioTitle = "Mountain Solitude Ambient Soundscape",
            audioArtist = "Elena Nature Recs",
            mediaRes = R.drawable.post_travel_sunset_1792278519420,
            likesCount = 142300,
            isLiked = true,
            comments = sampleComments,
            shareCount = 31200,
            bookmarkCount = 18400,
            isBookmarked = true
        ),
        ShortReel(
            id = "r3",
            creator = userTechLab,
            caption = "Desk setup hack: using floating magnetic rails to eliminate ALL cable visibility! Watch till the end for the snap ⚡",
            hashtags = listOf("#DeskSetup", "#TechHacks", "#Minimalism", "#CleanDesk", "#StudioGoals"),
            audioTitle = "Lofi Workstation Groove",
            audioArtist = "ChillHop Creators",
            mediaRes = R.drawable.video_tech_studio_1792278504100,
            likesCount = 67200,
            isLiked = false,
            comments = sampleComments,
            shareCount = 9800,
            bookmarkCount = 22100,
            isBookmarked = false
        )
    )

    val videos = listOf(
        Video(
            id = "v1",
            title = "BUILDING MY DREAM TECH STUDIO! Full 4K Setup Tour & Cable Management Guide",
            channel = userTechLab,
            duration = "18:42",
            durationSeconds = 1122,
            viewsCount = "1.2M views",
            uploadDate = "3 days ago",
            thumbnailRes = R.drawable.video_tech_studio_1792278504100,
            description = "Welcome to the ultimate walkthrough of our newly completed creative lab! We built custom soundproofing acoustic panels, dual 4K OLED monitor rigs, motorized sit-stand walnut desk, and integrated smart DMX ambient studio lighting.\n\nTimestamps:\n0:00 - Introduction & Vision\n2:15 - Desk & Ergonomics\n6:40 - The Dual OLED Setup\n11:20 - Audio & Mic Chain\n15:30 - Lighting Automation\n18:00 - Final Thoughts & Giveaway",
            category = "Tech & AI",
            likesCount = 78400,
            isLiked = false,
            isDisliked = false,
            isSubscribed = true,
            comments = sampleComments,
            tags = listOf("tech", "studio tour", "desk setup", "coding", "ergonomics")
        ),
        Video(
            id = "v2",
            title = "Solo Alpine Summit Expedition: 48 Hours in the Cloud Sea (Cinematic Documentary)",
            channel = userElena,
            duration = "26:15",
            durationSeconds = 1575,
            viewsCount = "840K views",
            uploadDate = "1 week ago",
            thumbnailRes = R.drawable.post_travel_sunset_1792278519420,
            description = "A solo journey through the rugged mountain ridge where the golden clouds meet the sky. Filmed entirely on handheld lightweight cinema gear.\n\nGear Used:\n- Mirrorless Camera 4K60\n- 24-70mm f/2.8 Lens\n- Ultra-lightweight carbon tripod\n- Drone with ND Filters",
            category = "Documentaries",
            likesCount = 54100,
            isLiked = true,
            isDisliked = false,
            isSubscribed = false,
            comments = sampleComments,
            tags = listOf("hiking", "mountains", "cinematic", "drone", "nature")
        ),
        Video(
            id = "v3",
            title = "NEON REVOLUTION: Behind The Scenes of Cyber Street Choreography",
            channel = userMaya,
            duration = "12:05",
            durationSeconds = 725,
            viewsCount = "2.1M views",
            uploadDate = "2 weeks ago",
            thumbnailRes = R.drawable.reel_cyber_dance_1792278503427,
            description = "How we engineered wearable addressable LED strips that react to accelerometer movements in the dancer's jacket while filming in heavy Tokyo rain.",
            category = "Music",
            likesCount = 132000,
            isLiked = false,
            isDisliked = false,
            isSubscribed = true,
            comments = sampleComments,
            tags = listOf("dance", "cyberpunk", "tokyo", "music video", "vfx")
        )
    )

    val notifications = listOf(
        NotificationItem(
            id = "n1",
            sender = userMaya,
            message = "loved your latest short video reel ❤️",
            timeAgo = "10m ago",
            type = NotificationType.LIKE,
            isRead = false,
            targetThumbnailRes = R.drawable.reel_cyber_dance_1792278503427
        ),
        NotificationItem(
            id = "n2",
            sender = userTechLab,
            message = "uploaded a new video: 'BUILDING MY DREAM TECH STUDIO!' 🎥",
            timeAgo = "1h ago",
            type = NotificationType.VIDEO_UPLOAD,
            isRead = false,
            targetThumbnailRes = R.drawable.video_tech_studio_1792278504100
        ),
        NotificationItem(
            id = "n3",
            sender = userElena,
            message = "commented: 'Literally breathtaking view, need the exact GPS coordinates!'",
            timeAgo = "3h ago",
            type = NotificationType.COMMENT,
            isRead = true,
            targetThumbnailRes = R.drawable.post_travel_sunset_1792278519420
        ),
        NotificationItem(
            id = "n4",
            sender = userKai,
            message = "started following you 👤",
            timeAgo = "5h ago",
            type = NotificationType.FOLLOW,
            isRead = true
        )
    )

    val videoCategories = listOf(
        "All", "Tech & AI", "Music", "Gaming", "Documentaries", "Live Now", "Podcasts", "Creative", "Short Films"
    )

    val trendingTags = listOf(
        "#Cyberpunk", "#TechStudio", "#AlpineSunset", "#DanceChallenge", "#AIRevolution", "#DeskGoals", "#TokyoVibes", "#IndieMusic"
    )
}
