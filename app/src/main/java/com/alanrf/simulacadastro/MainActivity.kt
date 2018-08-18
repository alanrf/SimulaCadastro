package com.alanrf.simulacadastro

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btCadastrar.setOnClickListener{
            cadastrar()
        }
    }

    fun validarCampos(): Boolean {
        var b = validaEmail()
        b = validaSenhas() && b
        return b
    }

    fun validaEmail(): Boolean {
        val b = edEmail.text.contains("@")
        if (!b)
            edEmail.setError(getString(R.string.msg_valida_email))
        return b
    }

    fun validaSenhas(): Boolean {
        val b = edSenha.text.toString() == edConfirmarSenha.text.toString()

        if (!b) {
            val msg = getString(R.string.msg_valida_senha)
            edSenha.setError(msg)
            edConfirmarSenha.setError(msg)
        }
        return b
    }

    fun cadastrar() {
        if (validarCampos()) {
            //TODO chamar a proxima tela e passar os dados do cadastro para a mesma.
            val it = Intent(this, ExibeDadosActivity::class.java)


            it.putExtra("nome", edNome.text)
            it.putExtra("dataNascimento", edDataNascimento.text)
            it.putExtra("email", edEmail.text)
            it.putExtra("estadoCivil", spEstadoCivil.selectedItem.toString())

            if (rgSexo.checkedRadioButtonId == rdMasculino.id)
                it.putExtra("sexo", "Masculino")
            else
                it.putExtra("sexo", "Feminino")




            startActivity(it)
        }
    }
}
