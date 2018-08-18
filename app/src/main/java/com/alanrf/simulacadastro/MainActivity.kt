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
        return validaSenhas()
    }

    fun validaSenhas(): Boolean {
        return edNome.text == edNome.text
    }
}
