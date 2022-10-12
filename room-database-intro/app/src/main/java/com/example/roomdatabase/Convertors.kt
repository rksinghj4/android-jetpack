package com.example.roomdatabase

import androidx.room.TypeConverter
import java.util.Date

/**
 * Each value stored in an SQLite database (or manipulated by the database engine) has one of the following storage classes:
*  NULL. The value is a NULL value.
*  INTEGER. The value is a signed integer, stored in 0, 1, 2, 3, 4, 6, or 8 bytes depending on the magnitude of the value.
*  REAL. The value is a floating point value, stored as an 8-byte IEEE floating point number.
*  TEXT. The value is a text string, stored using the database encoding (UTF-8, UTF-16BE or UTF-16LE).
*  BLOB. The value is a blob of data, stored exactly as it was input.
 * BLOB: Binary large object - PHOTOS, MP4, MP3 etc. It could be of 4gb size.
 */

class Convertors {

    /**
     * SQLite can't store Date. We have to use @TypeConverter
     */
    @TypeConverter
    fun fromDateToLong(value: Date): Long = value.time

    @TypeConverter
    fun fromLongToDate(value: Long): Date = Date(value)
}