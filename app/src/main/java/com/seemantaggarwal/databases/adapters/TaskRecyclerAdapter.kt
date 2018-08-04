package com.seemantaggarwal.databases.adapters


import android.app.AlertDialog
import android.content.Context
import android.support.v7.widget.AlertDialogLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seemantaggarwal.databases.R
import com.seemantaggarwal.databases.adapters.models.Task
import kotlinx.android.synthetic.main.list_item_task.view.*

class TaskRecyclerAdapter (
        val tasks: ArrayList<Task>
        val onTaskUpdate: (Task) -> Unit,
        val onTaskDelete: (Task) -> Unit
): RecyclerView.Adapter<TaskRecyclerAdapter.TaskViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = li.inflate(R.layout.list_item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.itemView.checkBox.isChecked = tasks[position].done
        holder.itemView.tvTaskName.text = tasks[position].taskName
        holder.itemView.checkBox.setOnCheckedChangeListener {
            _, isChecked -> tasks[position].done = isChecked
            tasks[position].done=isChecked
            onTaskUpdate(tasks[position])
        }
        holder.itemView.setOnLongClickListener{
            AlertDialog.Builder(holder.itemView.context)
                    .setTitle("DELETE TASK").setMessage("DO YOU WANT TO DELETE?")
                    .setPositiveButton("YES", { ,  -> onTaskDelete(tasks[position])}  )
                    .setNegativeButton("NO", { ,  -> Unit })
                    .show()
            true


        }
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }



}