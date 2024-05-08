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
import com.example.fitness.explore.workout.excercise.DetailExercise
import com.example.fitness.model.Exercise
import com.example.fitness.model.Workout
import com.example.fitness.repository.ExerciseRepository
import com.example.fitness.repository.WorkoutRepository


class DetailWorkout : Fragment() {
    private lateinit var viewBinding: LayoutDetailWorkoutBinding
    private var workout: Workout? = null
    private lateinit var adapter: AdapterListExerciseInWorkout
    private lateinit var exerciseRepository: ExerciseRepository
    private lateinit var workoutRepository: WorkoutRepository
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutDetailWorkoutBinding.inflate(inflater, container, false)
        exerciseRepository = ExerciseRepository()
        workoutRepository = WorkoutRepository()
        getWorkoutData()
        viewBinding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        viewBinding.linearLayoutMain.setOnClickListener {

        }
        viewBinding.btnStartWorkout.setOnClickListener {
            onChangeToStartWorkout(workout!!)
        }
        return viewBinding.root
    }

    @SuppressLint("SetTextI18n")
    private fun bindingData() {
        loadImage(workout!!.imgCovered!!)
        viewBinding.nameWorkout.text = workout!!.name
        viewBinding.timeTxt.text = workout!!.time.toString() + " Phút"
        when (workout!!.type) {
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
        when (workout!!.difficulty) {
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
        viewBinding.txtDescription.text = workout!!.overview
        viewBinding.txtEquipment.text = handleEquipmentData(workout!!.equipment!!)
        handleRepeat()
    }

    @SuppressLint("SetTextI18n")
    private fun handleRepeat() {
        if (workout!!.repeat != 0) {
            viewBinding.numberRepeat.text = "Lặp Lại ${workout!!.repeat.toString()} lần"
        } else {
            viewBinding.layoutRepeat.visibility = View.GONE
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getExerciseDataInWorkout() {
        for (exerciseInWorkout in workout!!.listExercise!!) {
            exerciseRepository.getExerciseByDocument(
                exerciseInWorkout.getIdExercise()!!,
                object : ExerciseRepository.OnCompleteListener {
                    override fun onCompleteListener(exercise: Exercise) {
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
            if(bundle["workout"] != null) {
                workout = bundle["workout"] as Workout
                bindingData()
                setUpRecyclerView()
                getExerciseDataInWorkout()
            }else if(bundle["workoutFromPlan"] != null){
                val workout = bundle["workoutFromPlan"] as String
                workoutRepository.getWorkoutByDocumentId(workout, object : WorkoutRepository.OnCompleteListener{
                    override fun onCompleteListener(workout: Workout) {
                        resignedWorkout(workout)
                        bindingData()
                        setUpRecyclerView()
                        getExerciseDataInWorkout()
                    }
                })
            }
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
            workout!!.listExercise!!,
            object : AdapterListExerciseInWorkout.OnClickListenerExerciseInWorkout {
                override fun onClickListener(idExercise: String) {
                    val detailExercise = DetailExercise()
                    val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
                    val bundle = Bundle()
                    bundle.putString("idExercise", idExercise)
                    detailExercise.arguments = bundle
                    fragmentTrans.add(R.id.layout_main_activity, detailExercise)
                    fragmentTrans.addToBackStack(detailExercise.tag)
                    fragmentTrans.commit()
                }

            })
        viewBinding.recyclerViewListExercise.adapter = adapter
    }

    private fun onChangeToStartWorkout(workout: Workout) {
        val startWorkout = StartWorkout()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putSerializable("workout", workout)
        startWorkout.arguments = bundle
        fragmentTrans.replace(R.id.layout_main_activity, startWorkout)
        fragmentTrans.addToBackStack(startWorkout.tag)
        fragmentTrans.commit()
    }
    private fun resignedWorkout(workout : Workout){
        this.workout = workout
    }
}