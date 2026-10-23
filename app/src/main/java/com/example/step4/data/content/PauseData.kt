package com.tridosha.balance.data.content

import com.tridosha.balance.data.model.PausePrompt
import java.time.LocalDate

internal object PauseData {

    val prompts: List<PausePrompt> = listOf(
        PausePrompt(
            id = "settle",
            title = "Settle in",
            guidance = "Rest your attention on the breath at your nostrils. Let each exhale be a little longer than the inhale.",
            inhaleSeconds = 4,
            holdSeconds = 2,
            exhaleSeconds = 6,
        ),
        PausePrompt(
            id = "even",
            title = "Even breath",
            guidance = "Make the inhale and exhale the same length, like a slow, steady wave.",
            inhaleSeconds = 5,
            holdSeconds = 0,
            exhaleSeconds = 5,
        ),
        PausePrompt(
            id = "shoulders",
            title = "Soften the shoulders",
            guidance = "Notice your shoulders as you breathe in. As you breathe out, let them melt away from your ears.",
            inhaleSeconds = 4,
            holdSeconds = 3,
            exhaleSeconds = 6,
        ),
        PausePrompt(
            id = "cooling",
            title = "Cooling breath",
            guidance = "Imagine breathing in a cool breeze and breathing out any heat or hurry.",
            inhaleSeconds = 4,
            holdSeconds = 0,
            exhaleSeconds = 6,
        ),
        PausePrompt(
            id = "ground",
            title = "Ground yourself",
            guidance = "Feel your feet on the floor. Imagine the breath traveling down through them, then back up.",
            inhaleSeconds = 5,
            holdSeconds = 2,
            exhaleSeconds = 7,
        ),
    )

    /** Index of the prompt to show first today; rotates daily. */
    fun indexForToday(): Int = LocalDate.now().dayOfYear % prompts.size
}
