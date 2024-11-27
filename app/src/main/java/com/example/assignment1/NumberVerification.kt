package com.example.assignment1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.example.assignment1.databinding.NumberVerificationBinding


class NumberVerification : Fragment() ,View.OnClickListener{
    private lateinit var binding: NumberVerificationBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.number_verification,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moveCursor()
        setClickListener()
    }

    private fun setClickListener() {
        binding.continueButtonLogin.setOnClickListener(this)
        binding.backButtonNumVerification.setOnClickListener(this)
    }
    private fun checkPin(): Boolean {
        val newPin =
            binding.PinNumVerfication.pin1.text.toString() +
                    binding.PinNumVerfication.pin2.text.toString() +
                    binding.PinNumVerfication.pin3.text.toString() +
                    binding.PinNumVerfication.pin4.text.toString()
        val reEnteredPin = binding.PinNumVerfication2.pin1.text.toString() +
                binding.PinNumVerfication2.pin2.text.toString() +
                binding.PinNumVerfication2.pin3.text.toString() +
                binding.PinNumVerfication2.pin4.text.toString()
        if (newPin == reEnteredPin && newPin.length == 4) {

            binding.PinNumVerfication2.pinError.visibility = View.GONE
            return true
        } else
            binding.PinNumVerfication2.pinError.text = getString(R.string.enter_pin)
        return false
    }
    private fun moveCursor() {
        val pinFields = listOf(

            binding.PinNumVerfication.pin1,
            binding.PinNumVerfication.pin2,
            binding.PinNumVerfication.pin3,
            binding.PinNumVerfication.pin4,
            binding.PinNumVerfication2.pin1,
            binding.PinNumVerfication2.pin2,
            binding.PinNumVerfication2.pin3,
            binding.PinNumVerfication2.pin4
        )

        for (i in pinFields.indices) {
            pinFields[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // No-op
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 0 && i > 0) {
                        pinFields[i - 1].requestFocus()
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1 && i < pinFields.size - 1) {
                        pinFields[i + 1].requestFocus()
                    } else
                        callDashboard()

                }
            })
        }
    }
    private fun callDashboard() {
        if (checkPin()) {
            requireActivity().supportFragmentManager.popBackStack(tag,FragmentManager.POP_BACK_STACK_INCLUSIVE)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container1, Dashboard()).commit()
          //  requireActivity().supportFragmentManager.beginTransaction().
        } else binding.PinNumVerfication2.pinError.text = getString(R.string.pinDoesNotMatch)
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            binding.continueButtonLogin.id -> {
                callDashboard()
            }

            binding.backButtonNumVerification.id -> {
                requireActivity().supportFragmentManager.popBackStackImmediate()
            }
        }

    }

}