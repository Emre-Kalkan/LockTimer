package net.emrekalkan.locktimer.presentation.util.extensions

@Suppress("UNCHECKED_CAST")
fun <T : Number> Number?.orZero(): T {
    return (this ?: 0) as T
}
