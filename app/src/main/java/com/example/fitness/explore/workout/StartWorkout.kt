package com.example.fitness.explore.workout

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness.R
import com.example.fitness.adapter_recyclerView.adapter_excercise.AdapterExerciseInStartWorkout
import com.example.fitness.databinding.LayoutCountDownBinding
import com.example.fitness.databinding.LayoutSetUpRestTimeBinding
import com.example.fitness.databinding.LayoutStartWorkoutBinding
import com.example.fitness.model.ExerciseInWorkout
import com.example.fitness.model.Workout
import android.media.MediaPlayer
import android.os.Looper
import com.example.fitness.create.model.PersonalWorkout
import com.example.fitness.databinding.LayoutDialogEndWorkoutBinding
import com.example.fitness.model.History
import com.example.fitness.repository.UserRepository
import com.example.fitness.storage.Preferences
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class StartWorkout : Fragment() {
    private lateinit var viewBinding: LayoutStartWorkoutBinding
    private var adapter: AdapterExerciseInStartWorkout? = null
    private var listExercise: ArrayList<ExerciseInWorkout> = ArrayList()
    private var userRepository: UserRepository? = null
    private  var workout: Workout? = null
    private  var personalWorkout: PersonalWorkout?= null
    private lateinit var handler: Handler
    private var seconds = 0
    private var timeStart = 60000
    private var minutes = 0
    private var secondCountdown = 0
    private var timeCountDown: CountDownTimer? = null
    private var timeLeft = timeStart
    private var timeProgress = 0
    private var mediaPlayer: MediaPlayer? = null
    private var isRunning = true
    private lateinit var runnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutStartWorkoutBinding.inflate(inflater, container, false)
        viewBinding.linearLayoutMain.setOnClickListener {

        }
        viewBinding.btnFinishWorkout.setOnClickListener {
            onFinishWorkout()
        }
        viewBinding.btnCancel.setOnClickListener {
            openDialogConfirm()
        }
        viewBinding.btnSetRestTime.setOnClickListener {
            setUpCountDownTime()
        }
        viewBinding.btnPauseResume?.setOnClickListener {
            if(isRunning){
                isRunning = false
                pauseTimer()
                viewBinding.btnPauseResume!!.setBackgroundResource(R.drawable.baseline_play_circle_filled_24)
            }else{
                isRunning = true
                resumeTimer()
                viewBinding.btnPauseResume!!.setBackgroundResource(R.drawable.baseline_pause_circle_24)
            }
        }
        handler = Handler(Looper.getMainLooper())
        userRepository = UserRepository()
        getDataWorkout()
        setUpRecyclerView()
        startCheckTimeWorkout()
        setUpMinuteSecondRest()
        return viewBinding.root
    }

    private fun openDialogConfirm() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding = LayoutDialogEndWorkoutBinding.inflate(dialog.layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.btnNo.setOnClickListener {
            dialog.cancel()
        }
        binding.btnYes.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
            dialog.cancel()
        }
        dialog.show()

    }


    private fun setUpCountDownTime() {
        val dialog = Dialog(requireContext())
        val binding = LayoutSetUpRestTimeBinding.inflate(dialog.layoutInflater)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(R.drawable.custom_background_dialog)
        dialog.window?.attributes?.gravity = Gravity.BOTTOM
        dialog.setCancelable(false)
        val minutePicker = binding.minutesPicker
        val secondPicker = binding.secondPicker
        minutePicker.minValue = 0
        minutePicker.maxValue = 60
        secondPicker.minValue = 0
        secondPicker.maxValue = 60
        binding.minutesPicker.value = minutes
        binding.secondPicker.value = secondCountdown
        binding.Cancel.setOnClickListener {
            dialog.dismiss()
        }
        binding.Agree.setOnClickListener {
            val minuteValue = minutePicker.value
            val secondValue = secondPicker.value
            timeStart = minuteValue * 60 * 1000 + secondValue * 1000
            timeLeft = timeStart
            timeProgress = 0
            setUpMinuteSecondRest()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun onFinishWorkout() {
        val percentage: Float
        var numberFinish = 0F
        for (exercise in listExercise) {
            if (exercise.getChecked()) {
                numberFinish++
            }
        }
        percentage = (numberFinish / listExercise.size.toFloat()) * 100F
        val percentageString = String.format(Locale.getDefault(), "%.2f", percentage)
        val calendarToday = Calendar.getInstance()
        val year = calendarToday.get(Calendar.YEAR)
        val month = calendarToday.get(Calendar.MONTH)
        val day = calendarToday.get(Calendar.DAY_OF_MONTH)

        handler.removeCallbacksAndMessages(null)

        val selectedDate = Calendar.getInstance()
        selectedDate.set(year, month, day)
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val dateResult = sdf.format(selectedDate.time)

        val userId = Preferences(requireContext()).getUserIdValues()

        val workoutOutputName: String
        if(workout != null){
            workoutOutputName = workout!!.name!!
        }else{
            workoutOutputName = personalWorkout!!.nameWorkout
        }

        userRepository?.createHistoryForUser(
            userId,
            workoutOutputName,
            dateResult,
            percentageString,
            viewBinding.Time.text.toString()
        )

        changeToFinishWorkout(
            History(
                null,
                userId!!,
                workoutOutputName,
                dateResult,
                percentageString,
                viewBinding.Time.text.toString()
            )
        )
    }

    private fun changeToFinishWorkout(history: History) {
        val finishWorkoutFragment = FinishWorkout()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putSerializable("History", history)
        finishWorkoutFragment.arguments = bundle
        fragmentTrans.replace(R.id.layout_main_activity, finishWorkoutFragment)
        fragmentTrans.addToBackStack(finishWorkoutFragment.tag)
        fragmentTrans.commit()
    }

    private fun startCheckTimeWorkout() {
        runnable = object : Runnable {
            override fun run() {
                if (isRunning) {
                    val hours = seconds / 3600
                    val minutes = (seconds % 3600) / 60
                    val secs = seconds % 60
                    val timeString = if (hours != 0) {
                        String.format("%02d:%02d:%02d", hours, minutes, secs)
                    } else {
                        String.format("%02d:%02d", minutes, secs)
                    }
                    viewBinding.Time.text = timeString

                    seconds++
                    handler.postDelayed(this, 1000) // Cập nhật sau mỗi giây
                }
            }
        }
        handler.post(runnable)
    }
    private fun pauseTimer() {
        isRunning = false
        handler.removeCallbacks(runnable)
    }

    private fun resumeTimer() {
        isRunning = true
        handler.post(runnable)
    }

    private fun getDataWorkout() {
        val bundle = arguments
        if (bundle != null) {
            if(bundle["workout"] != null){
                workout = bundle["workout"] as Workout
                viewBinding.nameWorkout.text = workout!!.name
                handleRepeatListExerciseFromWorkout()
            }
            if(bundle["workoutPersonal"] != null){
                personalWorkout = bundle["workoutPersonal"] as PersonalWorkout
                viewBinding.nameWorkout.text = personalWorkout!!.nameWorkout
                handleRepeatListExerciseFromPersonalWorkout()
            }

        }

    }

    private fun handleRepeatListExerciseFromPersonalWorkout() {
        val listResult = personalWorkout!!.listExercise
        handleExercise(listResult)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleRepeatListExerciseFromWorkout() {
        val listResult = workout!!.listExercise
        val repeat = workout!!.repeat  //For get the repeat in each workout
        if (listResult != null) {
            if (repeat != null) {
                if (repeat != 0) {  //If we have repeat, we can handle that how many times does the workout will be looped
                    for (index in 0 until repeat.toInt()) {
                        handleExercise(listResult)
                        adapter?.notifyDataSetChanged()
                    }
                } else {  //If repeat is 0, we don't need to repeat the handleExercise
                    handleExercise(listResult)
                    adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    private fun handleExercise(data: ArrayList<ExerciseInWorkout>) {
        for (exercise in data) {
            val setAndRep = exercise.getSetAndRep()
            if (setAndRep != null) {
                if (!setAndRep.contains("Minutes") && !setAndRep.contains("Second") && !setAndRep.contains(
                        "Hour"
                    )
                ) {
                    val splitSetRep = setAndRep.split(" ")
                    val set = splitSetRep[0].toInt()
                    val rep = splitSetRep[3].toInt()
                    for (i in 0 until set) {
                        val newExercise =
                            ExerciseInWorkout(
                                exercise.getIdExercise(),
                                setAndRep
                            )
                        newExercise.setImage(exercise.getImage().toString())
                        newExercise.setUpRep(rep.toString())
                        newExercise.setName(exercise.getName().toString())
                        listExercise.add(newExercise)
                    }
                } else {
                    val newExercise = ExerciseInWorkout(
                        exercise.getIdExercise(),
                        setAndRep
                    )
                    newExercise.setImage(exercise.getImage().toString())
                    newExercise.setName(exercise.getName().toString())
                    listExercise.add(newExercise)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacksAndMessages(null)
    }

    private fun setUpRecyclerView() {
        viewBinding.recyclerViewExerciseInWorkout.setHasFixedSize(false)
        val layout = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        viewBinding.recyclerViewExerciseInWorkout.layoutManager = layout
        adapter = AdapterExerciseInStartWorkout(
            listExercise,
            object : AdapterExerciseInStartWorkout.OpenCountDownTimer {
                override fun openCountdownTimer() {
                    startCountDownTimer()
                }

            })
        viewBinding.recyclerViewExerciseInWorkout.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    private fun startCountDownTimer() {
        val dialog = Dialog(requireContext())
        val binding = LayoutCountDownBinding.inflate(dialog.layoutInflater)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(R.drawable.custom_background_dialog)
        dialog.window?.attributes?.gravity = Gravity.BOTTOM
        dialog.setCancelable(false)
        //setUp
        val getMinute = String.format(Locale.getDefault(), "%02d", minutes)
        val getSecond = String.format(Locale.getDefault(), "%02d", secondCountdown)
        binding.timeLeft.text = "$getMinute:$getSecond"
        binding.progressCountDown.max = timeStart
        binding.progressCountDown.progress = timeProgress
        timeCountDown = object : CountDownTimer(timeLeft.toLong(), 1000) {
            override fun onTick(p0: Long) {
                timeProgress += 1000
                binding.progressCountDown.progress = timeStart - timeProgress
                timeLeft = p0.toInt()
                updateTime(binding.timeLeft)
            }

            override fun onFinish() {
                mediaPlayer = MediaPlayer.create(context, R.raw.ting_ting)
                mediaPlayer?.start()
                dialog.cancel()
            }

        }.start()

        binding.btnSkip.setOnClickListener {
            timeCountDown?.cancel()
            timeLeft = timeStart
            timeProgress = 0
            dialog.dismiss()

        }
        dialog.show()
    }

    private fun setUpMinuteSecondRest() {
        minutes = (timeStart / 1000) / 60
        secondCountdown = (timeStart / 1000) % 60

    }

    @SuppressLint("SetTextI18n")
    private fun updateTime(txtTimeLeft: TextView) {
        val minutes: Int = (timeLeft / 1000) / 60
        val second: Int = (timeLeft / 1000) % 60

        val getMinute = String.format(Locale.getDefault(), "%02d", minutes)
        val getSecond = String.format(Locale.getDefault(), "%02d", second)

        txtTimeLeft.text = "$getMinute : $getSecond"

    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (mediaPlayer != null) {
            mediaPlayer?.release()
        }
    }
}