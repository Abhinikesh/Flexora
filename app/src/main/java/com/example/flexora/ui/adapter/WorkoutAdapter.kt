package com.example.flexora.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flexora.R
import com.example.flexora.domain.model.Workout
import java.text.SimpleDateFormat
import java.util.*

class WorkoutAdapter : ListAdapter<Workout, WorkoutAdapter.WorkoutViewHolder>(WorkoutDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_workout, parent, false)
        return WorkoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvWorkoutName: TextView = itemView.findViewById(R.id.tvWorkoutName)
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        private val tvSetsReps: TextView = itemView.findViewById(R.id.tvSetsReps)
        private val tvDuration: TextView = itemView.findViewById(R.id.tvDuration)
        private val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        fun bind(workout: Workout) {
            tvWorkoutName.text = workout.exerciseName
            tvDate.text = sdf.format(Date(workout.date))
            tvSetsReps.text = "Sets: ${workout.sets} | Reps: ${workout.reps}"
            tvDuration.text = "${workout.durationMinutes} min"
        }
    }

    class WorkoutDiffCallback : DiffUtil.ItemCallback<Workout>() {
        override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
            return oldItem == newItem
        }
    }
}
