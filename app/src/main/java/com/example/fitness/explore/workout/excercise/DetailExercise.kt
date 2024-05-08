package com.example.fitness.explore.workout.excercise

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.fitness.databinding.LayoutDetailExerciceBinding
import com.example.fitness.model.Exercise
import com.example.fitness.repository.ExerciseRepository

class DetailExercise : Fragment() {
    private lateinit var viewBinding: LayoutDetailExerciceBinding
    var exercise : Exercise? = null
    private lateinit var player : ExoPlayer
    private lateinit var exerciseRepository: ExerciseRepository
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutDetailExerciceBinding.inflate(inflater, container, false)
        exerciseRepository = ExerciseRepository()
        getExercise()
        viewBinding.btnBack.setOnClickListener {
            onBack()
        }
        viewBinding.linearLayoutMain.setOnClickListener {
        }
        return viewBinding.root
    }

    private fun onBack() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun getExercise() {
        val bundle = arguments
        if(bundle != null){
            if(bundle["exercise"] != null)
            {
                this.exercise = bundle["exercise"] as Exercise
                setTextData()
                setVideoData()
            }
            else if(bundle["idExercise"] != null){
                val exerciseId = bundle["idExercise"] as String
                exerciseRepository.getExerciseByDocument(exerciseId, object : ExerciseRepository.OnCompleteListener{
                    override fun onCompleteListener(exercise: Exercise) {
                        resignedExercise(exercise)
                        setTextData()
                        setVideoData()
                    }
                })
            }
        }
    }
    private fun setTextData(){
        viewBinding.nameExercise.text = exercise?.getName()
        viewBinding.txtDescription.text = exercise?.getDescription()
        val instructionResult = exercise?.getInstruction()
        val stringArray = instructionResult?.split(";")
        var finalResult = ""
        if (stringArray != null) {
            for(partString in stringArray){
                finalResult += partString.trim() + "\n"
            }
        }
        viewBinding.Instruction.text = finalResult

    }
    private fun setVideoData() {
        player = ExoPlayer.Builder(requireContext()).build()
        viewBinding.video.player = player
        val mediaItem = MediaItem.fromUri(exercise?.getSrcVideo()!!)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }
    override fun onStop() {
        super.onStop()
        player.release()
    }
    private fun resignedExercise(exercise : Exercise){
        this.exercise = exercise
    }
}