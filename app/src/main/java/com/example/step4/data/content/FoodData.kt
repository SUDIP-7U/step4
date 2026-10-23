package com.example.step4.data.content

import com.example.step4.data.model.Dosha
import com.example.step4.data.model.Dosha.KAPHA
import com.example.step4.data.model.Dosha.PITTA
import com.example.step4.data.model.Dosha.VATA
import com.example.step4.data.model.FoodCategory
import com.example.step4.data.model.FoodItem

internal object FoodData {

    const val INTRO_TITLE = "Eating with awareness"
    const val INTRO_BODY =
        "In Ayurveda, food is seen as a daily way to bring the doshas into balance. " +
            "These are general, traditional suggestions. Choose a category to explore."

    val principles: List<String> = listOf(
        "Favor fresh, warm, seasonal meals made with simple ingredients.",
        "Eat at regular times, sitting down and without distractions.",
        "Include the six tastes over the day: sweet, sour, salty, pungent, bitter and astringent.",
        "Stop eating when you feel comfortably satisfied, not full.",
    )

    private val ALL = listOf(VATA, PITTA, KAPHA)

    val categories: List<FoodCategory> = listOf(
        FoodCategory(
            id = "grains",
            title = "Grains",
            emoji = "🌾",
            intro = "Grains are the steady foundation of many Ayurvedic meals. Cooked warm and well, they ground and nourish.",
            guidance = listOf(
                "Rinse or soak grains before cooking to make them lighter on digestion.",
                "Cook them until soft, with a little ghee or oil and a pinch of spice.",
                "Choose lighter grains if you often feel heavy, and heartier ones if you feel depleted.",
            ),
            items = listOf(
                FoodItem("Basmati rice", "Light, fragrant and easy to digest.", listOf(VATA, PITTA), listOf(KAPHA)),
                FoodItem("Oats", "Warm and grounding when cooked as porridge.", listOf(VATA, PITTA), listOf(KAPHA)),
                FoodItem("Barley", "Light and drying, a traditional choice for lightness.", listOf(PITTA, KAPHA), listOf(VATA)),
                FoodItem("Quinoa", "Light and protein-rich. Rinse well and serve warm.", listOf(PITTA, KAPHA), listOf(VATA)),
                FoodItem("Millet", "Warming and drying. Pairs well with ghee and spices.", listOf(KAPHA), listOf(PITTA)),
                FoodItem("Wheat", "Sweet and substantial, soothing as warm bread or porridge.", listOf(VATA, PITTA), listOf(KAPHA)),
            ),
        ),
        FoodCategory(
            id = "fruits",
            title = "Fruits",
            emoji = "🍎",
            intro = "Fruit is best enjoyed ripe, in season and on its own, away from heavy meals.",
            guidance = listOf(
                "Choose ripe, in-season fruit and eat it between meals.",
                "Stewing fruit with cinnamon or cardamom makes it gentler on the stomach.",
                "Sweet fruits generally calm Vata and Pitta. Lighter, astringent fruits suit Kapha.",
            ),
            items = listOf(
                FoodItem("Ripe mango", "Sweet and nourishing, a summer favorite.", listOf(VATA, PITTA), listOf(KAPHA)),
                FoodItem("Apples", "Lovely stewed with cinnamon. Raw apples are lighter and more astringent.", listOf(PITTA, KAPHA), listOf(VATA)),
                FoodItem("Pomegranate", "Sweet-tart and astringent. Often described as balancing for all three.", ALL, emptyList()),
                FoodItem("Dates", "Rich, sweet and grounding, a traditional energy food.", listOf(VATA, PITTA), listOf(KAPHA)),
                FoodItem("Sweet grapes", "Juicy and cooling.", listOf(VATA, PITTA), listOf(KAPHA)),
                FoodItem("Pears", "Cooling and gently astringent.", listOf(PITTA, KAPHA), listOf(VATA)),
            ),
        ),
        FoodCategory(
            id = "vegetables",
            title = "Vegetables",
            emoji = "🥦",
            intro = "Vegetables bring color, fiber and variety. Lightly cooked is the traditional way to make them easier to digest.",
            guidance = listOf(
                "Steam, sauté or roast vegetables with a little oil and spices.",
                "Raw salads are lighter and cooling, so they suit warm days and strong digestion best.",
                "Eat leafy greens and bitter vegetables regularly if you tend toward heat or heaviness.",
            ),
            items = listOf(
                FoodItem("Sweet potato", "Sweet, soft and grounding.", listOf(VATA, PITTA), listOf(KAPHA)),
                FoodItem("Leafy greens", "Bitter and astringent. Cook them lightly if you run cold.", listOf(PITTA, KAPHA), listOf(VATA)),
                FoodItem("Carrots", "Earthy and sweet, lovely cooked with ginger.", listOf(VATA, KAPHA), listOf(PITTA)),
                FoodItem("Cucumber", "Cooling and hydrating.", listOf(PITTA), listOf(VATA, KAPHA)),
                FoodItem("Squash & pumpkin", "Naturally sweet and comforting.", listOf(VATA, PITTA), listOf(KAPHA)),
                FoodItem("Broccoli", "Light and slightly bitter. Cook well and add spices.", listOf(PITTA, KAPHA), listOf(VATA)),
            ),
        ),
        FoodCategory(
            id = "spices",
            title = "Spices",
            emoji = "🌶️",
            intro = "Spices are the gentle medicine cabinet of the Ayurvedic kitchen, awakening flavor and supporting digestion.",
            guidance = listOf(
                "Use whole or freshly ground spices, warmed briefly in ghee or oil.",
                "Begin with small amounts and build up. Flavor, not heat, is the goal.",
                "Cooling spices such as fennel and coriander are good for hot weather and a hot temperament.",
            ),
            items = listOf(
                FoodItem("Ginger", "Warming and stimulating, good fresh or dried.", listOf(VATA, KAPHA), listOf(PITTA)),
                FoodItem("Cumin", "Earthy and gently warming. A staple for many cooks.", ALL, emptyList()),
                FoodItem("Coriander", "Mild and cooling.", ALL, emptyList()),
                FoodItem("Fennel", "Sweet and cooling. Often chewed after meals.", ALL, emptyList()),
                FoodItem("Turmeric", "Golden, earthy and warming. Common in everyday cooking.", listOf(VATA, KAPHA), listOf(PITTA)),
                FoodItem("Cinnamon", "Sweet and warming, lovely in porridge and tea.", listOf(VATA, KAPHA), listOf(PITTA)),
                FoodItem("Cardamom", "Fragrant and balancing.", ALL, emptyList()),
            ),
        ),
        FoodCategory(
            id = "drinks",
            title = "Drinks",
            emoji = "🍵",
            intro = "Warm, simple drinks are a quiet way to nourish and hydrate through the day.",
            guidance = listOf(
                "Sip warm or room-temperature drinks through the day rather than gulping very cold ones.",
                "Take liquids mostly between meals so digestion stays comfortable.",
                "Choose caffeine-free herbal teas in the evening.",
            ),
            items = listOf(
                FoodItem("Warm water", "Simple and soothing, especially first thing in the morning.", ALL, emptyList()),
                FoodItem("Ginger tea", "Warming and comforting on cool, damp days.", listOf(VATA, KAPHA), listOf(PITTA)),
                FoodItem("Cumin, coriander & fennel tea", "A classic, gentle after-meal infusion.", ALL, emptyList()),
                FoodItem("Coconut water", "Cooling and hydrating.", listOf(PITTA, VATA), listOf(KAPHA)),
                FoodItem("Golden milk", "Warm milk with turmeric and a pinch of spice. Perfect before bed.", listOf(VATA, PITTA), listOf(KAPHA)),
                FoodItem("Mint tea", "Fresh and cooling.", listOf(PITTA, KAPHA), listOf(VATA)),
            ),
        ),
    )

    fun categoryById(id: String): FoodCategory? = categories.firstOrNull { it.id == id }

    /** Items in [category] that favor [dosha], or all items when [dosha] is null. */
    fun itemsFor(category: FoodCategory, dosha: Dosha?): List<FoodItem> =
        if (dosha == null) category.items else category.items.filter { dosha in it.favors }
}
