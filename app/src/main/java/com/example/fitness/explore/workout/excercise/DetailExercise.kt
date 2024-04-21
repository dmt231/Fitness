package com.example.fitness.explore.workout.excercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.fitness.databinding.LayoutDetailExerciceBinding
import com.example.fitness.model.Exercise

class DetailExercise : Fragment() {
    private lateinit var viewBinding: LayoutDetailExerciceBinding
    private var exercise : Exercise? = null
    private lateinit var player : ExoPlayer
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutDetailExerciceBinding.inflate(inflater, container, false)
        getExercise()
        setTextData()
        setVideoData()
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
            this.exercise = bundle["exercise"] as Exercise
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
    private fun replaceString(input: String?): String? {
        return input?.replace(Regex(";\\s"), "\n")
    }
}