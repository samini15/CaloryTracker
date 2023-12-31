package com.example.core.domain.use_case

class FilterOutDigits {
    operator fun invoke(text: String): String = text.filter { it.isDigit() }
}