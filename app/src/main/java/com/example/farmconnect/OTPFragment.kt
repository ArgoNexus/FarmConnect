package com.example.farmconnect

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.farmconnect.activity.UsersMainActivity
import com.example.farmconnect.databinding.FragmentOTPBinding
import com.example.farmconnect.models.users
import com.example.farmconnect.viewmodels.AuthViewModel
import kotlinx.coroutines.launch

class OTPFragment : Fragment() {
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: FragmentOTPBinding
    private lateinit var userNumber: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOTPBinding.inflate(inflater)
        getUserNumber()
        sendOTP()
        onLoginButtonClicked()
        customizingEnteringOTP()
        return binding.root
    }

    private fun sendOTP() {
        Utils.showDialog(requireContext(), "Sending OTP")
        viewModel.apply {
            sendOTP(userNumber, requireActivity())
            lifecycleScope.launch {
                viewModel.otpSent.collect {
                    if (it) {
                        Utils.hideDialog()
                        Utils.showToast(requireContext(), "OTP sent")
                    }
                }
            }
        }
    }

    private fun getUserNumber() {
        val bundle = arguments
        userNumber = bundle?.getString("number").toString()
        binding.UserNumber.text = userNumber
    }

    private fun customizingEnteringOTP() {
        val editTexts = arrayOf(
            binding.etOtp1,
            binding.etOtp2,
            binding.etOtp3,
            binding.etOtp4,
            binding.etOtp5,
            binding.etOtp6
        )

        for (i in editTexts.indices) {
            editTexts[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1) {
                        if (i < editTexts.size - 1) {
                            editTexts[i + 1].requestFocus()
                        }
                    } else if (s?.length == 0) {
                        if (i > 0) {
                            editTexts[i - 1].requestFocus()
                        }
                    }
                }
            })
        }
    }

    private fun verifyOtp(otp: String) {
        val user= users(uid=Utils.getCurrentUserId(), userPhoneNumber=userNumber, userAddress = null)
        viewModel.signInWithPhoneAuthCredential(otp, userNumber,user)
        lifecycleScope.launch {
            viewModel.isSignedInSuccessfully.collect {
                if (it) {
                    Utils.hideDialog()
                    Utils.showToast(requireContext(), "Login Successful")

                }
            }
        }
    }

    private fun onLoginButtonClicked() {
        binding.loginbtn.setOnClickListener {
            Utils.showDialog(requireContext(), "Logging In")
            val editTexts = arrayOf(
                binding.etOtp1,
                binding.etOtp2,
                binding.etOtp3,
                binding.etOtp4,
                binding.etOtp5,
                binding.etOtp6
            )
            val otp = editTexts.joinToString("") { it.text.toString() }

            if (otp.length < editTexts.size) {
                Utils.showToast(requireContext(), "Please enter the right OTP")
            } else {
                verifyOtp(otp)
            }
        }

    }
}