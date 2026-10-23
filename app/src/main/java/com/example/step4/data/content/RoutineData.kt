package com.tridosha.balance.data.content

import com.tridosha.balance.data.model.Dosha
import com.tridosha.balance.data.model.RoutinePeriod

internal object RoutineData {

    val periods: List<RoutinePeriod> = listOf(
        RoutinePeriod(
            id = "morning",
            title = "Morning",
            emoji = "🌅",
            timeRange = "5:00 – 10:00",
            summary = "Wake gently and set a steady tone for the day.",
            doshas = listOf(Dosha.VATA, Dosha.KAPHA),
            tips = listOf(
                "Traditionally, rising around sunrise is considered easiest on the body and mind.",
                "Sip a glass of warm water to welcome your digestion.",
                "Rinse your mouth and gently clean your tongue.",
                "Move for 10–20 minutes: stretching, sun salutations or a brisk walk.",
                "Sit for a few minutes of quiet breathing or meditation.",
                "Enjoy a warm, light breakfast without screens.",
            ),
        ),
        RoutinePeriod(
            id = "daytime",
            title = "Daytime",
            emoji = "☀️",
            timeRange = "10:00 – 17:00",
            summary = "Your most active hours. Nourish yourself and keep a calm pace.",
            doshas = listOf(Dosha.PITTA, Dosha.VATA),
            tips = listOf(
                "Make lunch your main meal. Ayurveda holds that digestion is strongest around midday.",
                "Sit down to eat, chew well and put devices away.",
                "Take a short, easy walk after your meal.",
                "Drink water at room temperature through the day.",
                "Pause every hour: stretch, soften your gaze and take three slow breaths.",
                "Step outside for fresh air and natural light when you can.",
            ),
        ),
        RoutinePeriod(
            id = "evening",
            title = "Evening",
            emoji = "🌇",
            timeRange = "17:00 – 21:00",
            summary = "Slow down and let the day settle.",
            doshas = listOf(Dosha.KAPHA),
            tips = listOf(
                "Eat a lighter, warm dinner, ideally 2–3 hours before bed.",
                "Take a gentle stroll after dinner.",
                "Dim the lights and reduce screen time as the evening unfolds.",
                "Spend time on something you enjoy: music, a hobby or good company.",
                "Write down three things you are grateful for.",
            ),
        ),
        RoutinePeriod(
            id = "night",
            title = "Night",
            emoji = "🌙",
            timeRange = "21:00 – 5:00",
            summary = "Rest is when the body restores itself.",
            doshas = listOf(Dosha.PITTA, Dosha.VATA),
            tips = listOf(
                "Aim to be in bed by about 10 pm, and wake at a consistent time.",
                "Try a warm, caffeine-free drink such as chamomile or fennel tea.",
                "Keep your bedroom cool, dark and quiet.",
                "Massage your feet with a little warm oil, then wipe off any excess.",
                "Finish with a slow body scan or long, quiet exhales.",
                "Leave your phone outside the bed.",
            ),
        ),
    )

    fun periodForHour(hour: Int): RoutinePeriod = when (hour) {
        in 5..9 -> periods[0]
        in 10..16 -> periods[1]
        in 17..20 -> periods[2]
        else -> periods[3]
    }
}
