package com.alanrf.simulacadastro

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.alanrf.simulacadastro.R.id.*
import kotlinx.android.synthetic.main.activity_exibe_dados.*

class ExibeDadosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exibe_dados)

        btVoltar.setOnClickListener{
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        edNome.text = intent.extras.getCharSequence("nome")
        edDataNascimento.text = intent.extras.getCharSequence("dataNascimento")
        edProfissao.text = intent.extras.getCharSequence("profissao")
        edSexo.text = intent.extras.getCharSequence("sexo")
        edEstadoCivil.text = intent.extras.getCharSequence("estadoCivil")
        edEmail.text = intent.extras.getCharSequence("email")

    }
}
