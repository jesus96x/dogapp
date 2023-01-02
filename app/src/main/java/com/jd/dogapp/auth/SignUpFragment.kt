package com.jd.dogapp.auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jd.dogapp.databinding.FragmentSignUpBinding
import com.jd.dogapp.isValidEmail

class SignUpFragment : Fragment() {

    interface SignUpFragmentActions
    {
        fun onSignUpFieldsValidated(email: String, password: String, confirmPassword: String)
    }

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var signUpFragmentActions: SignUpFragmentActions

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signUpFragmentActions = try {
            context as SignUpFragmentActions
        }
        catch (e: ClassCastException)
        {
            throw  ClassCastException("$context debe implementar loginfragmentactions")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater)
        setupSignUpButton()
        return binding.root
    }

    private fun setupSignUpButton() {
        binding.signUpButton.setOnClickListener {
            validateFields()
        }
    }

    private fun validateFields() {
        binding.emailInput.error = ""
        binding.passwordInput.error = ""
        binding.confirmPasswordInput.error = ""

        val email = binding.emailEdit.text.toString()
        val password = binding.passwordEdit.text.toString()
        val confirmPassword = binding.confirmPasswordEdit.text.toString()

        if(!isValidEmail(email))
        {
            binding.emailInput.error = "El email no es valido"
        }
        if(password.isEmpty())
        {
            binding.passwordInput.error = "Contraseña invalida"
        }
        if(confirmPassword.isEmpty())
        {
            binding.confirmPasswordInput.error = "Contraseña no debe estar vacia"
            return
        }
        else if(password != confirmPassword)
        {
            binding.passwordInput.error = "Contraseñas no coinciden"
        }
        else
        {
            signUpFragmentActions.onSignUpFieldsValidated(email, password, confirmPassword)
        }
    }
}