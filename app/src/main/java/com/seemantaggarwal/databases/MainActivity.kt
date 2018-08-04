package com.seemantaggarwal.databases

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.seemantaggarwal.databases.adapters.TaskRecyclerAdapter
import com.seemantaggarwal.databases.adapters.models.Task
import com.seemantaggarwal.databases.db.TaskTable
import com.seemantaggarwal.databases.db.TododbHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val tasks = ArrayList<Task>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db=TododbHelper(this).writableDatabase
        rvTasks.layoutManager = LinearLayoutManager(this)
        lateinit var  taskAdapter: TaskRecyclerAdapter

        fun refreshTodos () {
            tasks.clear()
            tasks.addAll(TaskTable.getAllTasks(db))
            taskAdapter.notifyDataSetChanged()
        }
        val onTaskDelete= {
            task: Task -> TaskTable.deleteTask(db,task)
            refreshTodos()

        }


        val taskAdapter = TaskRecyclerAdapter(tasks, {
            task: Task -> TaskTable.updateTask(db,task)
            refreshTodos()
        })
        rvTasks.adapter = taskAdapter


        refreshTodos()


        btnAddTask.setOnClickListener {
            val newTask = Task(null,
                    etNewTask.text.toString(),
                    false
            )
            val id=TaskTable.addTask(db, newTask)
            refreshTodos()
            taskAdapter.notifyDataSetChanged()
        }
        btnClearTask.setOnClickListener{
            TaskTable.deleteTask(db,true)
            refreshTodos()
        }
}
}
