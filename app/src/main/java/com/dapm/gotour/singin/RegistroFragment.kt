package com.dapm.gotour.singin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.dapm.gotour.R
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.database.model.Usuario
import com.dapm.gotour.databinding.FragmentRegistroBinding


class RegistroFragment : Fragment() {

    private lateinit var _binding: FragmentRegistroBinding
    private val binding get() = _binding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.registrarseButton.setOnClickListener {
            val db = DataBaseHandler(requireContext())
            val username = _binding.emailInput.text.toString()
            val contrasena = _binding.passwordInput.text.toString()
            val confirmar = _binding.confirmarInput.text.toString()
            val usuario = Usuario(username, contrasena)
            val registrarse = db.createUsuario(usuario)

            if (username.isEmpty() || contrasena.isEmpty() || confirmar.isEmpty()) {
                Toast.makeText(context, "Faltan Datos", Toast.LENGTH_SHORT).show()
            } else {
                if (contrasena.equals(confirmar)){
                    if (registrarse){
                        Toast.makeText(context, "Registro Exitoso", Toast.LENGTH_SHORT).show()
                        val fragment = LoginFragment()
                        val fragmentManager = requireActivity().supportFragmentManager
                        val transaction = fragmentManager.beginTransaction()
                        transaction.replace(R.id.main_container, fragment)
                        transaction.addToBackStack(null)
                        transaction.commit()
                    } else {
                        Toast.makeText(context, "El Usuario Ya Existe", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "La Contraseña No Coincide", Toast.LENGTH_SHORT).show()
                }
            }

        }

        _binding.toLogin.setOnClickListener {
            val fragment = LoginFragment()
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
        _binding = FragmentRegistroBinding.inflate(inflater, container, false)
        return binding.root


    }


}