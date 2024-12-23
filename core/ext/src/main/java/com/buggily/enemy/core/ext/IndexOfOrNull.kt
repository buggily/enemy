package com.buggily.enemy.core.ext

fun <Element> List<Element>.indexOfOrNull(element: Element): Int? {
    return indexOf(element).takeIf { it.isNonNegative }
}
