package com.samuelgenio.commonservice.infrastructure.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

val gson = Gson()

fun <T> T?.toJson(): String? = gson.toJson(this)

inline fun <reified T> String?.fromJson(): T = gson.fromJson(this, typeToken<T>())

inline fun <reified T> typeToken(): Type = object : TypeToken<T>() {}.type