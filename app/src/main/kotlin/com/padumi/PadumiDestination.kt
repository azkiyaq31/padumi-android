package com.padumi

internal enum class PadumiDestination(
    val route: String,
    val label: String,
) {
    TODAY("today", "Today"),
    WARDROBE("wardrobe", "Wardrobe"),
    OUTFITS("outfits", "Outfits"),
    PLANNER("planner", "Planner"),
    ;

    val screenTag: String get() = "${route}_screen"
}
