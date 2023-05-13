package com.dapm.gotour.singin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.dapm.gotour.R
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.database.model.Usuario
import com.dapm.gotour.databinding.FragmentLoginBinding

import com.dapm.gotour.home.HomeActivity


class LoginFragment : Fragment() {

    private lateinit var _binding: FragmentLoginBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val db = DataBaseHandler(requireContext())
            val username = _binding.emailInput.text.toString()
            val contrasena = _binding.passwordInput.text.toString()
            val usuario = Usuario(username, contrasena)

            if (username.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(context, "Faltan Datos", Toast.LENGTH_SHORT).show()
            } else {
                val validar = db.comprobarUsuario(usuario)
                if (validar) {
                    Toast.makeText(context, "¡Bienvenido!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireContext(), HomeActivity::class.java)
                    val dato = Bundle()
                    dato.putString("usuario", username)
                    intent.putExtras(dato)
                    startActivity(intent)
                } else {
                    Toast.makeText(context, "Datos Incorrectos", Toast.LENGTH_SHORT).show()
                }
            }


        }

        binding.toRegistro.setOnClickListener {

            val fragment = RegistroFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.main_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


}