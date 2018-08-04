package com.seemantaggarwal.databases.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.seemantaggarwal.databases.adapters.models.Task

class TaskTable {
    companion object {
        val TABLE_NAME = "tasks"
        val CMD_CREATE_TABLE = """ CREATE TABLE IF NOT EXISTS ${TABLE_NAME} (
                ${Columns.ID} INTEGER PRIMARY KEY AUTOINCREMENT ,
                ${Columns.TASK} TEXT,
                ${Columns.DONE} BOOLEAN
            );
            """.trimIndent()

        fun addTask(db: SQLiteDatabase, task: Task): Long {
            val row = ContentValues()
            row.put(Columns.TASK, task.taskName)
            row.put(Columns.DONE, task.done)
            return db.insert(TABLE_NAME, null, row)


        }
        fun deleteTask(db: SQLiteDatabase,task: Task){
            db.delete(
                    TABLE_NAME,
                    "${Columns.ID}=?",
                    arrayOf(task.id.toString())
            )
        }
        fun deleteTask(db: SQLiteDatabase, doneStatus: Boolean): Int{
            var doneVal=0
            return db.delete(
                    TABLE_NAME,
                    "${Columns.ID}=?",
                    arrayOf(doneVal.toString())
            )
        }
        fun updateTask(db: SQLiteDatabase,task: Task):ArrayList<Task>{
            if (task.id == null) {
                Log.e("TASK", "No ID found to update")
            }
            val updatedTask = ContentValues()
            updatedTask.put(Columns.ID, task.id)
            updatedTask.put(Columns.TASK, task.taskName)
            updatedTask.put(Columns.DONE, task.done)
            val updatedRows = db.update(
                    TABLE_NAME,
                    updatedTask,
                    "${Columns.ID} = ?",
                    arrayOf(task.id.toString())
            )
        }
        fun getAllTasks(db: SQLiteDatabase): ArrayList<Task> {
            val tasks = ArrayList<Task>()
            val cursor = db.query(
                    TABLE_NAME,
                    arrayOf(Columns.ID, Columns.TASK, Columns.DONE),
                    null, null,
                    null, null,
                    null
            )
            val idCol = cursor.getColumnIndex(Columns.ID)
            val taskCol = cursor.getColumnIndex(Columns.TASK)
            val donCol = cursor.getColumnIndex(Columns.DONE)
            while (cursor.moveToNext()) {
                val rowTask= Task(
                        cursor.getInt(idCol),
                        cursor.getString(taskCol) ,
                        cursor.getInt(donCol) == 1

                )
                tasks.add(rowTask)

            }
            return tasks
        }

    }
    object Columns{
        val ID="id"
        val TASK="task"
        val DONE="done"
    }
}