package com.example.assignment1

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.text.TextWatcher
import androidx.databinding.DataBindingUtil
import com.example.assignment1.databinding.OtpVerificationBinding

class OtpVerification : Fragment(), View.OnClickListener {
    private lateinit var binding: OtpVerificationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.otp_verification,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
        init()
        moveCursor()
    }


    private fun init() {
        val number = arguments?.getString(getString(R.string.number)).toString()
        //Adding Entered Mobile Number to UI Using %s PlaceHolder
        binding.otpWasSent.text = "${getString(R.string.otpWasSentTo)} ${number}"
        binding.hiWeHave.text = getString(R.string.hiiWeHave,number)

    }
    private fun setClickListener() {
        binding.continueButtonLogin.setOnClickListener(this)
        binding.backOtp.setOnClickListener(this)
    }
    private fun moveCursor() {
        val pinFields = listOf(
            binding.pin1,
            binding.pin2,
            binding.pin3,
            binding.pin4,
            binding.pin5,
            binding.pin6
        )
        for (i in pinFields.indices) {
            pinFields[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                    //no opp
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 0 && i > 0) {
                        pinFields[i - 1].requestFocus()
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1 && i < pinFields.size - 1) {
                        pinFields[i + 1].requestFocus()

                    } else {
                        otpValidation()
                    }
                }
            })
        }
    }
    private fun otpValidation() {
        val inputPin: String = binding.pin1.text.toString() +
                binding.pin2.text.toString() +
                binding.pin3.text.toString() +
                binding.pin4.text.toString() +
                binding.pin5.text.toString() +
                binding.pin6.text.toString()
        if (inputPin.trim().length == 6) {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container1, PinVerification()).commit()
        } else binding.errorMsg.text = getString(R.string.please_enter_otp)

    }
    override fun onClick(v: View?) {
        when (v?.id) {
            binding.continueButtonLogin.id -> {
                otpValidation()
            }

            binding.backOtp.id -> {
                requireActivity().supportFragmentManager.popBackStackImmediate()
            }
        }
    }

}