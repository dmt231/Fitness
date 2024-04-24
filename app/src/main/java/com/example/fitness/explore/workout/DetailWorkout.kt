package com.example.fitness.explore.workout

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.fitness.R
import com.example.fitness.adapter_recyclerView.adapter_excercise.AdapterListExerciseInWorkout
import com.example.fitness.databinding.LayoutDetailWorkoutBinding
import com.example.fitness.model.Exercise
import com.example.fitness.model.Workout
import com.example.fitness.repository.ExerciseRepository


class DetailWorkout : Fragment() {
    private lateinit var viewBinding: LayoutDetailWorkoutBinding
    private lateinit var workout: Workout
    private lateinit var adapter: AdapterListExerciseInWorkout
    private lateinit var exerciseRepository: ExerciseRepository
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutDetailWorkoutBinding.inflate(inflater, container, false)
        exerciseRepository = ExerciseRepository()
        getWorkoutData()
        bindingData()
        viewBinding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        viewBinding.linearLayoutMain.setOnClickListener {

        }
        setUpRecyclerView()
        getExerciseDataInWorkout()
        return viewBinding.root
    }

    @SuppressLint("SetTextI18n")
    private fun bindingData() {
        loadImage(workout.imgCovered!!)
        viewBinding.nameWorkout.text = workout.name
        viewBinding.timeTxt.text = workout.time.toString() + " Phút"
        when (workout.type) {
            "BodyWeight" -> {
                viewBinding.type.text = "Trọng Lượng Cơ Thể"
            }
            "Cardio" -> {
                viewBinding.type.text = "Cardio"
            }
            "Resistance" -> {
                viewBinding.type.text = "Kháng Lực"
            }
            "Stretching" -> {
                viewBinding.type.text = "Giãn Cơ"
            }
        }
        when (workout.difficulty) {
            "Beginner" -> {
                viewBinding.level.text = "Dễ"
            }
            "Advanced" -> {
                viewBinding.level.text = "Trung Bình"
            }
            "Hard" -> {
                viewBinding.level.text = "Khó"
            }
        }
        viewBinding.txtDescription.text = workout.overview
        viewBinding.txtEquipment.text = handleEquipmentData(workout.equipment!!)
        viewBinding.btnStartWorkout.setOnClickListener {
            onChangeToFinishWorkout(workout)
        }
        handleRepeat()
    }

    @SuppressLint("SetTextI18n")
    private fun handleRepeat() {
        if (workout.repeat != 0) {
            viewBinding.numberRepeat.text = "Lặp Lại ${workout.repeat.toString()} lần"
        } else {
            viewBinding.layoutRepeat.visibility = View.GONE
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getExerciseDataInWorkout() {
        for (exerciseInWorkout in workout.listExercise!!) {
            Log.d("Exercise In Workout", exerciseInWorkout.getIdExercise().toString())
            exerciseRepository.getExerciseByDocument(
                exerciseInWorkout.getIdExercise()!!,
                object : ExerciseRepository.OnCompleteListener {
                    override fun onCompleteListener(exercise: Exercise) {
                        Log.d("Name : ", exercise.getName().toString())
                        exerciseInWorkout.setImage(exercise.getImage()!!)
                        exerciseInWorkout.setName(exercise.getName()!!)
                        adapter.notifyDataSetChanged()
                    }
                })
        }
        viewBinding.recyclerViewListExercise.visibility = View.VISIBLE
    }

    private fun getWorkoutData() {
        val bundle = arguments
        if (bundle != null) {
            workout = bundle["workout"] as Workout
        }
    }

    private fun loadImage(image: String) {
        Glide.with(requireContext())
            .load(image)
            .into(viewBinding.image)
    }

    private fun handleEquipmentData(equipment: String): String {
        val translateString = equipment.split(",")
        var result = ""
        for (sequence in translateString) {
            if (sequence.trim() == "Dumbbell") {
                result += "Tạ Đơn, "
            } else if (sequence.trim() == "Barbell") {
                result += "Tạ Đòn, "
            } else if (sequence.trim() == "Machine & Cable") {
                result += "Máy Và Dây Cáp, "
            } else if (sequence.trim() == "Resistance Band") {
                result += "Dây Kháng Lực, "
            } else if (sequence.trim() == "None") {
                result += "Không Dụng Cụ, "
            } else if (sequence.trim() == "Jump Rope") {
                result += "Dây Nhảy, "
            } else if (sequence.trim() == "Pull Up Bar") {
                result += "Xà Đơn, "
            } else if (sequence.trim() == "Dip Bar") {
                result += "Xà Kép, "
            }
        }
        return result.dropLast(2)
    }

    private fun setUpRecyclerView() {
        viewBinding.recyclerViewListExercise.setHasFixedSize(false)
        val layout = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        viewBinding.recyclerViewListExercise.layoutManager = layout
        adapter = AdapterListExerciseInWorkout(
            workout.listExercise!!,
            object : AdapterListExerciseInWorkout.OnClickListenerExerciseInWorkout {
                override fun onClickListener(exercise: Exercise) {

                }
            })
        viewBinding.recyclerViewListExercise.adapter = adapter
    }

    private fun onChangeToFinishWorkout(workout: Workout) {
        val startWorkout = StartWorkout()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putSerializable("workout", workout)
        startWorkout.arguments = bundle
        fragmentTrans.add(R.id.layout_main_activity, startWorkout)
        fragmentTrans.addToBackStack(null)
        fragmentTrans.commit()
    }
}