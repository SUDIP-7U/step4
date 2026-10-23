package com.example.step4.data.model

internal enum class Dosha(
    val displayName: String,
    val emoji: String,
    val element: String,
    val tagline: String,
    val qualities: List<String>,
    val inBalance: List<String>,
    val outOfBalance: List<String>,
    val supportTips: List<String>,
) {
    VATA(
        displayName = "Vata",
        emoji = "🍃",
        element = "Air & Space",
        tagline = "The energy of movement: breath, circulation, creativity and change.",
        qualities = listOf("Light", "Dry", "Cool", "Mobile", "Subtle"),
        inBalance = listOf(
            "Creative, lively and quick to learn",
            "Adaptable and enthusiastic",
            "Regular digestion and restful sleep",
        ),
        outOfBalance = listOf(
            "Restlessness, worry or a scattered mind",
            "Dry skin, irregular appetite or light sleep",
            "Feeling cold or easily overwhelmed",
        ),
        supportTips = listOf(
            "Favor warm, cooked, softly spiced meals",
            "Keep regular meal and bedtime hours",
            "Try a warm sesame-oil self-massage before bathing",
            "Choose gentle, steady movement such as yoga or walking",
            "Build a calming wind-down before sleep",
        ),
    ),
    PITTA(
        displayName = "Pitta",
        emoji = "🔥",
        element = "Fire & Water",
        tagline = "The energy of transformation: digestion, focus and drive.",
        qualities = listOf("Hot", "Sharp", "Oily", "Intense", "Light"),
        inBalance = listOf(
            "Focused, confident and articulate",
            "Strong appetite and digestion",
            "Warm-hearted with natural leadership",
        ),
        outOfBalance = listOf(
            "Irritability, impatience or perfectionism",
            "Feeling overheated or easily flushed",
            "Skipped meals leading to a short temper",
        ),
        supportTips = listOf(
            "Favor cooling foods such as cucumber, coconut and mint",
            "Avoid exercising in the midday heat",
            "Spend quiet time in nature, near water or greenery",
            "Make space for play, not only achievement",
            "Eat meals on time and unhurried",
        ),
    ),
    KAPHA(
        displayName = "Kapha",
        emoji = "💧",
        element = "Earth & Water",
        tagline = "The energy of structure: stability, nourishment and calm.",
        qualities = listOf("Heavy", "Slow", "Steady", "Soft", "Cool"),
        inBalance = listOf(
            "Calm, loyal and nurturing",
            "Strong stamina and a steady mood",
            "Smooth skin and restful, deep sleep",
        ),
        outOfBalance = listOf(
            "Sluggishness or heaviness in the morning",
            "Holding on to routines or things too long",
            "Oversleeping or feeling congested",
        ),
        supportTips = listOf(
            "Favor light, warm and well-spiced meals",
            "Start the day with brisk movement",
            "Rise early rather than sleeping in",
            "Add variety and new experiences to your week",
            "Try dry brushing before your shower",
        ),
    ),
}

internal data class RoutinePeriod(
    val id: String,
    val title: String,
    val emoji: String,
    val timeRange: String,
    val summary: String,
    val doshas: List<Dosha>,
    val tips: List<String>,
)

internal data class FoodItem(
    val name: String,
    val note: String,
    val favors: List<Dosha>,
    val moderate: List<Dosha>,
)

internal data class FoodCategory(
    val id: String,
    val title: String,
    val emoji: String,
    val intro: String,
    val guidance: List<String>,
    val items: List<FoodItem>,
)

internal data class PausePrompt(
    val id: String,
    val title: String,
    val guidance: String,
    val inhaleSeconds: Int,
    val holdSeconds: Int,
    val exhaleSeconds: Int,
)

internal data class ReflectionOption(
    val text: String,
    val dosha: Dosha,
)

internal data class ReflectionQuestion(
    val id: String,
    val text: String,
    val options: List<ReflectionOption>,
)
