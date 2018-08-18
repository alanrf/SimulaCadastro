package com.alanrf.simulacadastro

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun validarCampos(): Boolean {
        return validaEmail() && validaSenhas()
    }

    fun validaEmail(): Boolean {
        val b = edEmail.text.contains("@")
        if (!b) {
            edEmail.error = "Email não está num formato reconhecido."
        }
        return b
    }

    fun validaSenhas(): Boolean {
        val b = edSenha.text == edConfirmarSenha.text

        if (!b) {
            val msg = "Senha e Confirmar Senha devem ser iguais"
            edSenha.error = msg
            edConfirmarSenha.error = msg
        }
        return b
    }

    fun cadastrar() {
        if (validarCampos()) {
            //TODO chamar a proxima tela e passar os dados do cadastro para a mesma.
        }
    }
}
