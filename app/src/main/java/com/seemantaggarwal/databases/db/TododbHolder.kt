package com.seemantaggarwal.databases.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val DB_NAME= "todo.db"
val DB_VER= 4
class TododbHelper(context: Context?): SQLiteOpenHelper(context, DB_NAME, null, DB_VER){
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.let{
            it.execSQL(TaskTable.CMD_CREATE_TABLE)

        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }


}
