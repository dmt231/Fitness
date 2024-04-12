package com.example.fitness.authentication.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitness.R
import com.example.fitness.adapter.viewPagerAdapter.ViewPagerAdapter
import com.example.fitness.authentication.LoginFragment
import com.example.fitness.authentication.SignUpFragment
import com.example.fitness.databinding.WelcomeLayoutBinding


class WelcomeFragment : Fragment() {
    private lateinit var viewBinding: WelcomeLayoutBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = WelcomeLayoutBinding.inflate(inflater, container, false)
        setUpViewPager()
        setAnimation()
        viewBinding.btnLogin.setOnClickListener {
            onChangedToLoginPage()
        }
        viewBinding.btnSignUp.setOnClickListener {
            onChangedToSignUpPage()
        }
        return viewBinding.root
    }

    private fun setUpViewPager() {
        viewPagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager, lifecycle)
        viewBinding.viewPager.adapter = viewPagerAdapter
        viewBinding.indicator.setViewPager(viewBinding.viewPager)
    }

    private fun setAnimation() {
        viewBinding.relativeLayout.translationY = 600F
        viewBinding.relativeLayout.alpha = 0F
        viewBinding.relativeLayout.animate().translationY(0F).alpha(1F).setDuration(800).start()

        viewBinding.viewPager.translationX = -600F
        viewBinding.viewPager.alpha = 0F
        viewBinding.viewPager.animate().translationX(0F).alpha(1F).setDuration(800).start()


        viewBinding.txtFitness.alpha = 0F
        viewBinding.txtFitness.animate().translationY(0F).alpha(1F).setDuration(800).start()

    }
    private fun onChangedToLoginPage(){
        val loginFragment = LoginFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTrans.add(R.id.layoutLoginSignUp, loginFragment)
        fragmentTrans.addToBackStack(loginFragment.tag)
        fragmentTrans.commit()
    }
    private fun onChangedToSignUpPage(){
        val signUpFragment = SignUpFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTrans.add(R.id.layoutLoginSignUp, signUpFragment)
        fragmentTrans.addToBackStack(signUpFragment.tag)
        fragmentTrans.commit()
    }
}