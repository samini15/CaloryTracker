package com.example.core.domain.model

sealed class Gender(val name: String) {
    object Male: Gender("Male")
    object Female: Gender("Female")

    companion object {
        fun fromString(name: String): Gender {
            return when(name) {
                "Male" -> Male
                "Female" -> Female
                else -> Female
            }
        }
    }
}
