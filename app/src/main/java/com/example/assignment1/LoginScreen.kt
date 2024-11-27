package com.example.assignment1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.assignment1.databinding.LoginScreenBinding

class LoginScreen : Fragment(),View.OnClickListener {
    private lateinit var binding: LoginScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.login_screen,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      setClickListner()

    }
    private fun setClickListner(){
        binding.continueButtonLogin.setOnClickListener(this)
        binding.skip.setOnClickListener(this)
    }
    /*Function for Number Validation
    only 10 digit number and Number With prefix [6,7,8,9]
    Returns true i
    Regex is used for pattern Making and Matching

    * */
    fun numValidation(): Boolean {
        val reg = Regex("^[6-9]\\d{9}\$")
        val number = binding.numberInput.editTextLogin.text.toString()
        if (!reg.matches(number) || number.length < 10) {
            binding.numberInput.warning.visibility = View.VISIBLE
            binding.numberInput.warning.text = getString(R.string.invalidMobileNumber)
            return false
        }
        else if (number.toSet().size == 1) {
                binding.numberInput.warning.text = getString(R.string.sameCharacters)
                return false
            }
        else {
            binding.numberInput.warning.visibility = View.GONE
            return true
        }

    }

    override fun onClick(v: View?) {
        when(v?.id){
            binding.continueButtonLogin.id ->{
                //Code For Continue Button to Load Otp Verification Fragment
                if (numValidation()) {
                    if (binding.checkbox.isChecked) {
                        val bundle = Bundle()
                        bundle.putString(getString(R.string.number), binding.numberInput.editTextLogin.text.toString())
                        val fragment = OtpVerification()
                        fragment.arguments = bundle
                        parentFragmentManager.beginTransaction().replace(R.id.container1, fragment)
                            .addToBackStack(null).commit()
                    } else {
                        binding.numberInput.warning.visibility = View.VISIBLE
                        binding.numberInput.warning.text = getString(R.string.agreeToConditions)
                    } } }
            //Code for Continue Button To load Dashboard Fragment
            binding.skip.id->{
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container1, Dashboard()).commit()

            }
        }
    }
}