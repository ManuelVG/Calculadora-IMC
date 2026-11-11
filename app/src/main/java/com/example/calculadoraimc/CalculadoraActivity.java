package com.example.calculadoraimc;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class CalculadoraActivity extends AppCompatActivity {

    private EditText etPeso;
    private EditText etEstatura;
    private TextView tvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        etPeso = findViewById(R.id.etPeso);
        etEstatura = findViewById(R.id.etEstatura);
        tvResultado = findViewById(R.id.tvResultado);
        Button btnCalcular = findViewById(R.id.btnCalcular);
        Button btnVolver = findViewById(R.id.btnVolver);

        // Evento para calcular el IMC
        btnCalcular.setOnClickListener(v -> calcularIMC());

        // Evento para volver a la pantalla principal
        btnVolver.setOnClickListener(v -> finish());
    }

    private void calcularIMC() {
        String strPeso = etPeso.getText().toString().trim();
        String strEstatura = etEstatura.getText().toString().trim();

        // Control de errores: comprobar campos vacíos
        if (strPeso.isEmpty() || strEstatura.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_campos_vacios), Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double peso = Double.parseDouble(strPeso.replace(',', '.'));
            double estaturaCm = Double.parseDouble(strEstatura.replace(',', '.'));

            // Control de errores: comprobar valores positivos
            if (peso <= 0 || estaturaCm <= 0) {
                Toast.makeText(this, getString(R.string.error_valores_invalidos), Toast.LENGTH_SHORT).show();
                return;
            }

            // Convertir la estatura de centímetros a metros
            double estaturaM = estaturaCm / 100.0;

            // Cálculo del IMC: peso / (estatura en metros ^ 2)
            double imc = peso / (estaturaM * estaturaM);

            // Determinar la categoría según el resultado
            String categoria;
            if (imc < 18.5) {
                categoria = "Bajo peso";
            } else if (imc < 25.0) {
                categoria = "Peso normal";
            } else if (imc < 30.0) {
                categoria = "Sobrepeso";
            } else {
                categoria = "Obesidad";
            }

            String mensajeResultado = String.format(Locale.getDefault(), "IMC: %.2f (%s)", imc, categoria);

            // Mostrar resultado en el TextView y Toast
            tvResultado.setText(mensajeResultado);
            Toast.makeText(this, mensajeResultado, Toast.LENGTH_LONG).show();

        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.error_valores_invalidos), Toast.LENGTH_SHORT).show();
        }
    }
}
