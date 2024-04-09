package com.example.formpaycard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.formpaycard.databinding.ActivityMainBinding
import java.text.DecimalFormat
import java.util.Calendar
import java.util.regex.Pattern
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    //Agregamos la declaracion del viewBinding
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("StringFormatMatches")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Instanciamos el binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        //Le pasamos el binding como el contetView que va a pintar
        setContentView(binding.root)

        //variables
        val montoPago = pagoAleatorio()

        val tipoTarjetas = listOf("Visa", "MasterCard", "American Express")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipoTarjetas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //mostramos monto de pago
        binding.tvTotalPago.text = getString(R.string.pago, montoPago)

        // Asignar el ArrayAdapter al Spinner
        binding.sTipo.adapter = adapter

        /*
        //Accion al seleccionar del Spiner
        binding.sTipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedTarjeta = tipoTarjetas[position]
                Toast.makeText(applicationContext, "Seleccionaste: $selectedTarjeta", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(applicationContext, resources.getString(R.string.ingresa_tipo), Toast.LENGTH_SHORT).show()
            }
        }*/

        //Accion al dar click en Pagar
        binding.btnPagar.setOnClickListener{
            //Parametros
            val params = Bundle()
            var verificar = 0
            params.putString("montoPago", montoPago)

            //Verificaciones de vacio
            if(binding.etNombre.text.isNotEmpty()){
                //tomamos el texto que se guarda en la caja
                val nombre = binding.etNombre.text.toString()
                params.putString("nombre", nombre)
            }else{
                Toast.makeText(this, resources.getString(R.string.ingresa_nombre), Toast.LENGTH_SHORT).show()
                verificar += 1
                //Nostramos un mensaje para indicar que se necesita el campo
                binding.etNombre.error = getString(R.string.valor_requerido)
                binding.etNombre.requestFocus()
            }
            if(binding.etNumTarjeta.text.isNotEmpty()) {
                //tomamos el texto que se guarda en la caja
                val numTarjeta = binding.etNumTarjeta.text.toString()
                if(!validarLongitudTarjeta(numTarjeta)){
                    binding.etNumTarjeta.error = getString(R.string.tarjeta_invalido)
                    binding.etNumTarjeta.requestFocus()
                    verificar += 1
                }
                params.putString("numTarjeta", numTarjeta)
            }else{
                Toast.makeText(this, resources.getString(R.string.ingresa_numtarj), Toast.LENGTH_SHORT).show()
                verificar += 1
                //Nostramos un mensaje para indicar que se necesita el campo
                binding.etNumTarjeta.error = getString(R.string.valor_requerido)
                binding.etNumTarjeta.requestFocus()
            }
            if(binding.etFechaMes.text.isNotEmpty()){
                //tomamos el texto que se guarda en la caja
                val fechaMes = binding.etFechaMes.text.toString()
                if(!validarFechaMes(fechaMes)){
                    binding.etFechaMes.error = getString(R.string.mes_invalido)
                    binding.etFechaMes.requestFocus()
                    verificar += 1
                }
            }else{
                Toast.makeText(this, resources.getString(R.string.ingresa_mes), Toast.LENGTH_SHORT).show()
                verificar += 1
                //Nostramos un mensaje para indicar que se necesita el campo
                binding.etFechaMes.error = getString(R.string.valor_requerido)
                binding.etFechaMes.requestFocus()

            }
            if(binding.etFechaAnio.text.isNotEmpty()){
                //tomamos el texto que se guarda en la caja
                val fechaAnio = binding.etFechaAnio.text.toString()
                if(!validarFechaAnio(fechaAnio)){
                    binding.etFechaAnio.error = getString(R.string.anio_invalido)
                    binding.etFechaAnio.requestFocus()
                    verificar += 1
                }

            }else {
                Toast.makeText(this, resources.getString(R.string.ingresa_anio), Toast.LENGTH_SHORT).show()
                verificar += 1
                //Nostramos un mensaje para indicar que se necesita el campo
                binding.etFechaAnio.error = getString(R.string.valor_requerido)
                binding.etFechaAnio.requestFocus()
            }
            if(binding.etCVV.text.isNotEmpty()){
                //tomamos el texto que se guarda en la caja
                val cvv = binding.etCVV.text.toString()
                if(!validarLongitudCVV(cvv)){
                    binding.etCVV.error = getString(R.string.cvv_invalido)
                    binding.etCVV.requestFocus()
                    verificar += 1
                }
            }else{
                Toast.makeText(this, resources.getString(R.string.ingresa_cvv), Toast.LENGTH_SHORT).show()
                verificar += 1
                //Nostramos un mensaje para indicar que se necesita el campo
                binding.etCVV.error = getString(R.string.valor_requerido)
                binding.etCVV.requestFocus()

            }
            if(binding.etCorreo.text.isNotEmpty()){
                //tomamos el texto que se guarda en la caja
                val correo = binding.etCorreo.text.toString()
                //Validar correo
                if (!validarEmail(correo)){
                    binding.etCorreo.error = getString(R.string.email_invalido)
                    binding.etCorreo.requestFocus()
                    verificar += 1
                }

            }else{
                Toast.makeText(this, resources.getString(R.string.ingresa_correo), Toast.LENGTH_SHORT).show()
                verificar += 1
                //Nostramos un mensaje para indicar que se necesita el campo
                binding.etCorreo.error = getString(R.string.valor_requerido)
                binding.etCorreo.requestFocus()

            }

            if(verificar  == 0){
                //Pasamos los parametros a la otra activity

                val resultadoOperacion = generarResultadoAleatorio()

                params.putInt("resOperacion", resultadoOperacion)

                val intent = Intent(this, MainActivity2::class.java)
                intent.putExtras(params)

                startActivity(intent)
            }


        }
    }

    //Funciones
    private fun pagoAleatorio(): String {
        val montoRandom = Random.nextDouble(100.0, 5000.0)
        val decimalFormat = DecimalFormat("###,###,###.00")
        return decimalFormat.format(montoRandom)
    }

    private fun generarResultadoAleatorio(): Int {
        val randomNumber = Random.nextInt(100)
        // Establecer el umbral para decidir el resultado
        val probabilidad1 = 75
        // Comparar el número aleatorio con el umbral
        return if (randomNumber < probabilidad1) {
            1 // Devolver 1 (75% de las veces)
        } else {
            0 // Devolver 0 (25% de las veces)
        }
    }

    private fun validarEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    private fun validarLongitudCVV(cvv: String): Boolean {
        val longitud = cvv.length
        // Verificar la longitud del número
        return if (longitud == 3 || longitud == 4) {
            true
        }else{
            false
        }
    }

    private fun validarLongitudTarjeta(numTarjeta: String): Boolean {
        val longitud = numTarjeta.length
        // Verificar la longitud del número
        return if (longitud == 16) {
            true
        }else{
            false
        }
    }

    private fun validarFechaMes(mes: String):Boolean {
        val mesN = mes.toInt()
        return if (mesN <= 12){
            true
        }
        else{
            false
        }
    }

    private fun validarFechaAnio(anioYY: String): Boolean {
        val anioActual = Calendar.getInstance().get(Calendar.YEAR) - 2000  // Obtener los últimos dos dígitos del año actual
        if (anioYY.length != 2) {
            return false
        }
        // Convertir el año en formato YY a entero
        val anioInt = anioYY.toIntOrNull() ?: return false
        return anioInt > anioActual
    }

}