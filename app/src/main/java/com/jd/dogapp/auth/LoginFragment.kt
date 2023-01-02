package com.jd.dogapp.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jd.dogapp.databinding.FragmentLoginBinding
import com.jd.dogapp.isValidEmail

class LoginFragment : Fragment() {

    interface LoginFragmentActions
    {
        fun onRegisterButtonClick()
        fun onLoginFieldsValidated(email: String, password: String)
    }

    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginFragmentActions: LoginFragmentActions

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginFragmentActions = try {
            context as LoginFragmentActions
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
        binding = FragmentLoginBinding.inflate(inflater)

        binding.loginRegisterButton.setOnClickListener {
            loginFragmentActions.onRegisterButtonClick()
        }

        binding.loginButton.setOnClickListener {
            validateFields()
        }

        return binding.root
    }

    private fun validateFields()
    {
        binding.emailInput.error = ""
        binding.passwordInput.error = ""

        val email = binding.emailEdit.text.toString()
        val password = binding.passwordEdit.text.toString()

        if(!isValidEmail(email))
        {
            binding.emailInput.error = "El email no es valido"
        }
        if(password.isEmpty())
        {
            binding.passwordInput.error = "Contraseña invalida"
        }
        else
        {
            loginFragmentActions.onLoginFieldsValidated(email, password)
        }
    }
}