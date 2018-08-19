package com.alanrf.simulacadastro

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.Calendar


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btCadastrar.setOnClickListener {
            cadastrar()
        }

        spProfissao.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                val selected = spProfissao.selectedItem.toString()
                if ("Outra" == selected) {
                    //Limpa o conteudo e exibe o valor
                    edProfissaoEspecifica.setText("")
                    edProfissaoEspecifica.setVisibility(View.VISIBLE)
                } else {
                    edProfissaoEspecifica.setVisibility(View.INVISIBLE)
                }
            }
        })

        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd/MM/yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat)

            val str = sdf.format(cal.time)
            edDataNascimento.setText(str)

        }

        edDataNascimento.setOnClickListener {
            DatePickerDialog(this@MainActivity, dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        edDataNascimento.addTextChangedListener(object : TextWatcher {
            var strAnterior = ""
            var ddmmyyyy = "DDMMYYYY"
            var cal = Calendar.getInstance()
            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                if (charSequence.toString() != strAnterior) {
                    var strLimpa = charSequence.toString().replace(("[^\\d.]").toRegex(), "")
                    val strLimpaAnterior = strAnterior.replace(("[^\\d.]").toRegex(), "")
                    val cl = strLimpa.length
                    var sel = cl
                    var i = 2
                    while (i <= cl && i < 6) {
                        sel++
                        i += 2
                    }
                    //Fix para o delete
                    if (strLimpa == strLimpaAnterior) sel--
                    if (strLimpa.length < 8) {
                        strLimpa = strLimpa + ddmmyyyy.substring(strLimpa.length)
                    } else {
                        var dia = Integer.parseInt(strLimpa.substring(0, 2))
                        var mes = Integer.parseInt(strLimpa.substring(2, 4))
                        var ano = Integer.parseInt(strLimpa.substring(4, 8))
                        if (mes > 12) {
                            mes = 12
                        }
                        //no java janeiro = 0
                        cal.set(Calendar.MONTH, mes - 1)
                        //se selecionar um ano menor que 1900, assume 1900, se for um ano maior que 2100, assume 2100
                        ano = if ((ano < 1900)) 1900 else if ((ano > 2100)) 2100 else ano
                        cal.set(Calendar.YEAR, ano)
                        //se informou um dia maior que o maior dia para o mês e ano previamente
                        // definidos, corrige colocando o maior dia do mês
                        dia = if ((dia > cal.getActualMaximum(Calendar.DATE))) cal.getActualMaximum(Calendar.DATE) else dia
                        strLimpa = String.format("%02d%02d%02d", dia, mes, ano)
                    }
                    strLimpa = String.format("%s/%s/%s", strLimpa.substring(0, 2),
                            strLimpa.substring(2, 4),
                            strLimpa.substring(4, 8))
                    sel = if (sel < 0) 0 else sel
                    strAnterior = strLimpa

                    edDataNascimento.setText(strAnterior)
                    edDataNascimento.setSelection(if (sel < strAnterior.length) sel else strAnterior.length)
                }
            }

            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {}
        })
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

    fun validaOutraProfissaoPreenchida(): Boolean {
        var b = true
        if (edProfissaoEspecifica.visibility == View.VISIBLE) {
            b = !edProfissaoEspecifica.text.isEmpty()
            if (!b)
                edProfissaoEspecifica.setError("Especifique a profissão")
        }

        return b
    }

    fun cadastrar() {
        if (validarCampos()) {
            val it = Intent(this, ExibeDadosActivity::class.java)

            it.putExtra("nome", edNome.text)
            it.putExtra("dataNascimento", edDataNascimento.text)
            it.putExtra("email", edEmail.text)
            it.putExtra("estadoCivil", spEstadoCivil.selectedItem.toString())

            if (rgSexo.checkedRadioButtonId == rdMasculino.id)
                it.putExtra("sexo", "Masculino")
            else
                it.putExtra("sexo", "Feminino")

            if (edProfissaoEspecifica.visibility == View.INVISIBLE)
                it.putExtra("profissao", spProfissao.selectedItem.toString())
            else
                it.putExtra("profissao", edProfissaoEspecifica.text)

            startActivity(it)
        }
    }
}
