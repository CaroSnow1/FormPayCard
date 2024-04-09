package com.example.formpaycard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.example.formpaycard.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    //Agregamos la declaracion del viewBinding
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        //Recibe el parametro de MainActivity
        val params = intent.extras

        //Instanciamos el binding
        binding = ActivityMain2Binding.inflate(layoutInflater)
        enableEdgeToEdge()
        //Le pasamos el binding como el contetView que va a pintar
        setContentView(binding.root)

        if(params!=null){
            val montoPago = params.getString("montoPago", "")
            val nombre = params.getString("nombre", "")
            val numTarjeta = params.getString("numTarjeta", "")
            val resOperacion = params.getInt("resOperacion", 0)

            /*
            Toast.makeText(this, "Hola $name, tu total fue de de \$$total", Toast.LENGTH_LONG).show()*/

            //mostramos monto de pago
            if(resOperacion == 1){
                binding.tvResultadoOperacion.text = getString(R.string.res_op_exitosa)
            }else{
                binding.tvResultadoOperacion.text = getString(R.string.res_op_fallifa)
            }
            binding.tvCantidad.text = getString(R.string.r_pago, montoPago)
            binding.tvNombre.text = getString(R.string.r_nombre, nombre)
            binding.tvTarjeta.text = getString(R.string.r_tarjeta, numTarjeta)

        }


    }
}