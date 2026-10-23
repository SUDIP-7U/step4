package com.tridosha.balance.data.content

import com.tridosha.balance.data.model.Dosha
import com.tridosha.balance.data.model.ReflectionOption
import com.tridosha.balance.data.model.ReflectionQuestion

internal object ReflectionData {

    val questions: List<ReflectionQuestion> = listOf(
        ReflectionQuestion(
            id = "energy",
            text = "How would you describe your energy through a typical day?",
            options = listOf(
                ReflectionOption("It comes in bursts: high, then suddenly tired", Dosha.VATA),
                ReflectionOption("Strong and focused, as long as I eat on time", Dosha.PITTA),
                ReflectionOption("Steady and long-lasting, but slow to get going", Dosha.KAPHA),
            ),
        ),
        ReflectionQuestion(
            id = "stress",
            text = "When life gets stressful, you tend to…",
            options = listOf(
                ReflectionOption("Feel anxious and overthink", Dosha.VATA),
                ReflectionOption("Become irritable or critical", Dosha.PITTA),
                ReflectionOption("Withdraw and keep things inside", Dosha.KAPHA),
            ),
        ),
        ReflectionQuestion(
            id = "appetite",
            text = "Your appetite and digestion are usually…",
            options = listOf(
                ReflectionOption("Irregular. Sometimes I'm starving, sometimes I forget to eat", Dosha.VATA),
                ReflectionOption("Strong and sharp. I get cranky if a meal is late", Dosha.PITTA),
                ReflectionOption("Steady but slow. I can comfortably skip a meal", Dosha.KAPHA),
            ),
        ),
        ReflectionQuestion(
            id = "weather",
            text = "Which weather do you find hardest?",
            options = listOf(
                ReflectionOption("Cold, dry and windy days", Dosha.VATA),
                ReflectionOption("Hot, humid days", Dosha.PITTA),
                ReflectionOption("Cool, damp and grey days", Dosha.KAPHA),
            ),
        ),
        ReflectionQuestion(
            id = "sleep",
            text = "Your sleep tends to be…",
            options = listOf(
                ReflectionOption("Light and easily disturbed", Dosha.VATA),
                ReflectionOption("Moderate. I sleep soundly and wake up fairly alert", Dosha.PITTA),
                ReflectionOption("Deep and long. Getting up is the hard part", Dosha.KAPHA),
            ),
        ),
        ReflectionQuestion(
            id = "newThings",
            text = "How do you approach something new?",
            options = listOf(
                ReflectionOption("Quickly and with enthusiasm, and I often change course", Dosha.VATA),
                ReflectionOption("With a plan and real determination", Dosha.PITTA),
                ReflectionOption("Slowly and thoughtfully. I like familiar routines", Dosha.KAPHA),
            ),
        ),
        ReflectionQuestion(
            id = "body",
            text = "Which describes your skin and hair most closely?",
            options = listOf(
                ReflectionOption("Dry, thin or easily chilled", Dosha.VATA),
                ReflectionOption("Warm, sensitive or easily flushed", Dosha.PITTA),
                ReflectionOption("Soft, smooth and a little oily", Dosha.KAPHA),
            ),
        ),
    )

    /** Number of answers pointing to each dosha. [answers] holds the chosen option index per question. */
    fun scores(answers: List<Int>): Map<Dosha, Int> {
        val tally = Dosha.entries.associateWith { 0 }.toMutableMap()
        answers.forEachIndexed { questionIndex, optionIndex ->
            val dosha = questions.getOrNull(questionIndex)?.options?.getOrNull(optionIndex)?.dosha
            if (dosha != null) tally[dosha] = tally.getValue(dosha) + 1
        }
        return tally
    }

    /** The dosha(s) with the highest score; several when tied. Empty when nothing is answered. */
    fun dominant(scores: Map<Dosha, Int>): List<Dosha> {
        val top = scores.values.maxOrNull() ?: 0
        return if (top == 0) emptyList() else Dosha.entries.filter { scores[it] == top }
    }

    fun encode(doshas: List<Dosha>): String = doshas.joinToString(",") { it.name }

    fun decode(value: String?): List<Dosha> =
        value.orEmpty()
            .split(",")
            .mapNotNull { name -> Dosha.entries.firstOrNull { it.name == name.trim() } }

    fun describe(doshas: List<Dosha>): String = doshas.joinToString(" + ") { it.displayName }
}
