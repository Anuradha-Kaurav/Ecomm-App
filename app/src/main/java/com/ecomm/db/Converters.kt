package com.ecomm.db

import androidx.room.TypeConverter
import com.ecomm.models.Rating
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {
    private var gson = Gson()

    @TypeConverter
    fun objToString(rating: Rating): String {
        return gson.toJson(rating)
    }

    @TypeConverter
    fun stringToObj(ratingString: String): Rating {
        val objType : Type = object : TypeToken<Rating>() {}.type
        return gson.fromJson(ratingString, objType)
    }
}