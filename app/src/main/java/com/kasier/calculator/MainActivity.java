package com.kasier.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    AppCompatButton btnUno,btnDos, btnTres, btnCuatro, btnCinco, btnSeis, btnSiete, btnOcho, btnNueve, btnCero;
    AppCompatButton btnDiv, btnSuma, btnMenos, btnMult, btnEqual;
    TextView tvResult;

    String numActual = "";
    String lastOperator = "";
    float result = 0;
    boolean nuevaOpera = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        inicializar();
        configurarEventos();
    }

    public void inicializar() {
        btnCero = findViewById(R.id.btnCero);
        btnUno = findViewById(R.id.btnUno);
        btnDos = findViewById(R.id.btnDos);
        btnTres = findViewById(R.id.btnTres);
        btnCuatro = findViewById(R.id.btnCuatro);
        btnCinco = findViewById(R.id.btnCinco);
        btnSeis = findViewById(R.id.btnSeis);
        btnSiete = findViewById(R.id.btnSiete);
        btnOcho = findViewById(R.id.btnOcho);
        btnNueve = findViewById(R.id.btnNueve);
        btnDiv = findViewById(R.id.btnDiv);
        btnSuma = findViewById(R.id.btnSuma);
        btnMenos = findViewById(R.id.btnMenos);
        btnMult = findViewById(R.id.btnMult);
        btnEqual = findViewById(R.id.btnEqual);
        tvResult = findViewById(R.id.tvResult);
    }

    public void configurarEventos() {
        View.OnClickListener numberClickListener = view -> {
            AppCompatButton btn = (AppCompatButton) view;
            String numero = btn.getText().toString(); //obtener el valor del boton en texto

            if (nuevaOpera) { //empieza con una nueva operacion al ejecutar
                numActual = "";
                nuevaOpera = false;
            }

            if (numero.equals("0") && tvResult.getText().equals("0")) return; // No más ceros a la izquierda
            if (numActual.equals("0")) {
                numActual = numero; // Reemplaza el cero
            } else {
                numActual += numero;
            }

            tvResult.setText(numActual);
        };

        btnCero.setOnClickListener(numberClickListener);
        btnUno.setOnClickListener(numberClickListener);
        btnDos.setOnClickListener(numberClickListener);
        btnTres.setOnClickListener(numberClickListener);
        btnCuatro.setOnClickListener(numberClickListener);
        btnCinco.setOnClickListener(numberClickListener);
        btnSeis.setOnClickListener(numberClickListener);
        btnSiete.setOnClickListener(numberClickListener);
        btnOcho.setOnClickListener(numberClickListener);
        btnNueve.setOnClickListener(numberClickListener);

        btnSuma.setOnClickListener(v -> operar("+"));
        btnMenos.setOnClickListener(v -> operar("-"));
        btnMult.setOnClickListener(v -> operar("*"));
        btnDiv.setOnClickListener(v -> operar("/"));
        btnEqual.setOnClickListener(v -> resolverOperacion());
    }

    private void operar(String operador) {
        if (!numActual.isEmpty()) {
            float numeroActual = Float.parseFloat(numActual); // pasar texto a flotante

            try {
                if (lastOperator.isEmpty()) {
                    result = numeroActual;
                } else {
                    result = operacion(result, numeroActual, lastOperator);
                }
                //mostrar en el numero como entero o flotante
                if (result % 1 == 0) {
                    tvResult.setText(String.valueOf((int) result));
                } else {
                    tvResult.setText(String.valueOf(result));
                }

            } catch (ArithmeticException e) {
                tvResult.setText("Math Error");
                result = 0;
            }

            numActual = "";
        }
        lastOperator = operador;
        nuevaOpera = true;
    }


    private void resolverOperacion() {
        if (!numActual.isEmpty() && !lastOperator.isEmpty()) {
            float numeroActual = Float.parseFloat(numActual);

            try {
                result = operacion(result, numeroActual, lastOperator);
                if (result % 1 == 0) {
                    tvResult.setText(String.valueOf((int) result));
                } else {
                    tvResult.setText(String.valueOf(result));
                }

            } catch (ArithmeticException e) {
                tvResult.setText("Math Error");
                result = 0;
            }

            numActual = "";
            lastOperator = "";
            nuevaOpera = true;
        }
    }


    public float operacion(float num1, float num2, String operador) {
        switch (operador) {
            case "+": return num1 + num2;
            case "-": return num1 - num2;
            case "*": return num1 * num2;
            case "/":
                if (num2 == 0) {
                    throw new ArithmeticException("División entre cero");
                }
                return num1 / num2;
            default: return num2;
        }
    }


}

