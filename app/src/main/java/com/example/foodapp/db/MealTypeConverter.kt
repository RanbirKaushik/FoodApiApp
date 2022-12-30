package com.example.foodapp.db

import androidx.room.TypeConverters
import androidx.room.TypeConverter
import java.util.jar.Attributes

@TypeConverters
class MealTypeConverter {

    @TypeConverter
    fun fromAnyToString(attributes: Any?) : String{
        if (attributes==null)
            return ""
        return attributes as String
    }

    @TypeConverter
    fun fromStringToAny(attributes: String?) : Any{
        if (attributes==null)
            return ""
        return attributes
    }

}