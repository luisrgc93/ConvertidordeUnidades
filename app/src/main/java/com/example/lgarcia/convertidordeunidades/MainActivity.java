package com.example.lgarcia.convertidordeunidades;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity
{

    EditText cantidad;
    Spinner unidad1;
    Spinner unidad2;
    TextView result;
    DecimalFormat formato = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<IconosUnidades> itemsPeso = new ArrayList<IconosUnidades>(3);
        itemsPeso.add(new IconosUnidades(getString(R.string.libra), R.drawable.libra));
        itemsPeso.add(new IconosUnidades(getString(R.string.kg), R.drawable.kg));
        itemsPeso.add(new IconosUnidades(getString(R.string.gramos), R.drawable.gramos));
        itemsPeso.add(new IconosUnidades(getString(R.string.onzas), R.drawable.onzas));

        final List<IconosUnidades> itemsMedidas = new ArrayList<IconosUnidades>(5);
        itemsMedidas.add(new IconosUnidades(getString(R.string.milimetros), R.drawable.milimetros));
        itemsMedidas.add(new IconosUnidades(getString(R.string.centimetros), R.drawable.centimetros));
        itemsMedidas.add(new IconosUnidades(getString(R.string.pulgadas), R.drawable.pulgadas));
        itemsMedidas.add(new IconosUnidades(getString(R.string.metros), R.drawable.metros));
        itemsMedidas.add(new IconosUnidades(getString(R.string.km), R.drawable.kilometros));
        itemsMedidas.add(new IconosUnidades(getString(R.string.pies), R.drawable.pies));


        cantidad = (EditText) findViewById(R.id.cantidad);
        result = (TextView) findViewById(R.id.resultado);
        unidad1 = (Spinner) findViewById(R.id.unidad1);
        unidad2 = (Spinner) findViewById(R.id.unidad2);
        Button calcular = (Button) findViewById(R.id.calcular);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.unidadesMedida,
                R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter<CharSequence> adapterUnidad = ArrayAdapter.createFromResource(this,
                R.array.unidadesPeso,
                R.layout.support_simple_spinner_dropdown_item);
        adapterUnidad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter<CharSequence> adapterDistancia = ArrayAdapter.createFromResource(this,
                R.array.unidadesDistancia,
                R.layout.support_simple_spinner_dropdown_item);
        adapterDistancia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        unidad1.setAdapter(adapter);

        unidad1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Libras") ||
                        parent.getItemAtPosition(position).equals("Kg") ||
                        parent.getItemAtPosition(position).equals("Gramos") ||
                        parent.getItemAtPosition(position).equals("Onzas"))
                {
                    unidad2.setAdapter(new IconosUnidadesSpinnerAdapter(getApplicationContext(), itemsPeso));
                }
                else
                {
                    unidad2.setAdapter(new IconosUnidadesSpinnerAdapter(getApplicationContext(), itemsMedidas));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        unidad2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(), ((IconosUnidades) parent.getItemAtPosition(position)).getName(), Toast.LENGTH_SHORT).show();


                String Tipo1, Tipo2;
                Tipo1 = unidad1.getSelectedItem().toString();
                Tipo2 = ((IconosUnidades) parent.getItemAtPosition(position)).getName();
                if(Tipo1.equals("Libras") || Tipo1.equals("Kg") || Tipo1.equals("Gramos") || Tipo1.equals("Onzas"))
                {
                    Calcular(Tipo1, Tipo2);
                }
                else
                {
                    CalcularMedidas(Tipo1, Tipo2);
                }

            }
            ///FIn
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void Calcular(String Tipo1, String Tipo2)
    {
        String cant = cantidad.getText().toString();
        final String select1 = Tipo1;
        final String select2 = Tipo2;

        if(cant.equals(""))
        {
            cant = "0";
        }

        if(select1.equals("Libras") && select2.equals("Libras")) {
            result.setText(cant + " libras");
            return;
        }
        if(select1.equals("Libras") && select2.equals("Kg"))
        {
            double resultado = convertirLibrasToKg(Double.valueOf((cant)));
            result.setText(formato.format(resultado)+" Kg");
        }
        if(select1.equals("Libras") && select2.equals("Gramos"))
        {
            double resultado = convertirLibrasToGramos(Double.valueOf((cant)));
            result.setText(formato.format(resultado) + " Gramos");
        }
        if(select1.equals("Libras") && select2.equals("Onzas"))
        {
            double resultado = convertirLibrasToOnzas(Double.valueOf((cant)));
            result.setText(formato.format(resultado) + " Onzas");
        }
        /*KG A x*/
        if(select1.equals("Kg") && select2.equals("Libras"))
        {
            double resultado = convertirKgToLibras(Double.valueOf(cant));
            result.setText(formato.format(resultado)+" libras");
        }
        if(select1.equals("Kg") && select2.equals("Gramos"))
        {
            double resultado = convertirKgToGramos(Double.valueOf(cant));
            result.setText(formato.format(resultado)+" Gramos");
        }
        if(select1.equals("Kg") && select2.equals("Kg"))
        {
            result.setText(cant+" Kg");
        }
        if(select1.equals("Kg") && select2.equals("Onzas"))
        {
            double resultado = convertirKgToOnzas(Double.valueOf((cant)));
            result.setText(formato.format(resultado) + " Onzas");
        }
        /*GRAMOS A x*/
        if(select1.equals("Gramos") && select2.equals("Libras"))
        {
            double resultado = convertirGramosToLibras(Double.valueOf(cant));
            result.setText(formato.format(resultado)+" libras");
        }
        if(select1.equals("Gramos") && select2.equals("Kg"))
        {
            double resultado = convertirGramosToKg(Double.valueOf(cant));
            result.setText(formato.format(resultado)+" Kg");
        }
        if(select1.equals("Gramos") && select2.equals("Gramos"))
        {
            result.setText(cant+" gramos");
        }

        if(select1.equals("Gramos") && select2.equals("Onzas"))
        {
            double resultado = convertirGramosToOnzas(Double.valueOf((cant)));
            result.setText(formato.format(resultado) + " Onzas");
        }

        /*ONZAS A x*/
        if(select1.equals("Onzas") && select2.equals("Libras"))
        {
            double resultado = convertirOnzasToLibras(Double.valueOf(cant));
            result.setText(formato.format(resultado)+" libras");
        }
        if(select1.equals("Onzas") && select2.equals("Kg"))
        {
            double resultado = convertirOnzasToKg(Double.valueOf(cant));
            result.setText(formato.format(resultado)+" Kg");
        }
        if(select1.equals("Onzas") && select2.equals("Gramos"))
        {
            double resultado = convertirOnzasToGramos(Double.valueOf((cant)));
            result.setText(formato.format(resultado) + " Gramos");
        }

        if(select1.equals("Onzas") && select2.equals("Onzas"))
        {
            result.setText(cant+" onzas");

        }

    }

    public void CalcularMedidas(String tipo1, String tipo2)
    {
        String cant = cantidad.getText().toString();
        final String select1 = tipo1;
        final String select2 = tipo2;

        if(cant.equals(""))
        {
            cant = "0";
        }

        if(select1.equals("Milimetros") && select2.equals("Milimetros")) {
            result.setText(cant + " Milimetros");
            return;
        }
        if(select1.equals("Milimetros") && select2.equals("Centimetros"))
        {
            double resultado = ConvertirDatosMilimetros(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado)+" Centimetros");
            return;
        }
        if(select1.equals("Milimetros") && select2.equals("Pulgadas"))
        {
            double resultado = ConvertirDatosMilimetros(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado) + " Pulgadas");
        }
        if(select1.equals("Milimetros") && select2.equals("Metros"))
        {
            double resultado = ConvertirDatosMilimetros(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado) + " Metros");
        }
        if(select1.equals("Milimetros") && select2.equals("Kilometros"))
        {
            double resultado = ConvertirDatosMilimetros(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado) + " Kilometros");
        }
        if(select1.equals("Milimetros") && select2.equals("Pies"))
        {
            double resultado = ConvertirDatosMilimetros(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado) + " Pies");
        }

        /*************************  CENTIMETROS  *****************************/

        if(select1.equals("Centimetros") && select2.equals("Centimetros")) {
            result.setText(cant + " Centimetros");
            return;
        }
        if(select1.equals("Centimetros") && select2.equals("Milimetros"))
        {
            double resultado = ConvertirDatosCentimetros(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado)+" Milimetros");
            return;
        }
        if(select1.equals("Centimetros") && select2.equals("Pulgadas"))
        {
            double resultado = ConvertirDatosCentimetros(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado) + " Pulgadas");
        }
        if(select1.equals("Centimetros") && select2.equals("Metros"))
        {
            double resultado = ConvertirDatosCentimetros(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado) + " Metros");
        }
        if(select1.equals("Centimetros") && select2.equals("Kilometros"))
        {
            double resultado = ConvertirDatosCentimetros(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado) + " Kilometros");
        }
        if(select1.equals("Centimetros") && select2.equals("Pies"))
        {
            double resultado = ConvertirDatosCentimetros(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado) + " Pies");
        }

        /*************************  PULGADAS  *****************************/

        if(select1.equals("Pulgadas") && select2.equals("Pulgadas")) {
            result.setText(cant + " Pulgadas");
            return;
        }
        if(select1.equals("Pulgadas") && select2.equals("Milimetros"))
        {
            double resultado = ConvertirDatosPulgadas(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado)+" Milimetros");
            return;
        }
        if(select1.equals("Pulgadas") && select2.equals("Centimetros"))
        {
            double resultado = ConvertirDatosPulgadas(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado) + " Centimetros");
        }
        if(select1.equals("Pulgadas") && select2.equals("Metros"))
        {
            double resultado = ConvertirDatosPulgadas(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado) + " Metros");
        }
        if(select1.equals("Pulgadas") && select2.equals("Kilometros"))
        {
            double resultado = ConvertirDatosPulgadas(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado) + " Kilometros");
        }
        if(select1.equals("Pulgadas") && select2.equals("Pies"))
        {
            double resultado = ConvertirDatosPulgadas(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado) + " Pies");
        }

        /*************************  Metros  *****************************/

        if(select1.equals("Metros") && select2.equals("Metros")) {
            result.setText(cant + " Metros");
            return;
        }
        if(select1.equals("Metros") && select2.equals("Milimetros"))
        {
            double resultado = ConvertirDatosMetros(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado)+" Milimetros");
            return;
        }
        if(select1.equals("Metros") && select2.equals("Centimetros"))
        {
            double resultado = ConvertirDatosMetros(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado) + " Centimetros");
        }
        if(select1.equals("Metros") && select2.equals("Pulgadas"))
        {
            double resultado = ConvertirDatosMetros(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado) + " Pulgadas");
        }
        if(select1.equals("Metros") && select2.equals("Kilometros"))
        {
            double resultado = ConvertirDatosMetros(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado) + " Kilometros");
        }
        if(select1.equals("Metros") && select2.equals("Pies"))
        {
            double resultado = ConvertirDatosMetros(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado) + " Pies");
        }

        /*************************  Kilometros  *****************************/

        if(select1.equals("Kilometros") && select2.equals("Kilometros")) {
            result.setText(cant + " Kilometros");
            return;
        }
        if(select1.equals("Kilometros") && select2.equals("Milimetros"))
        {
            double resultado = ConvertirDatosKilometros(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado)+" Milimetros");
            return;
        }
        if(select1.equals("Kilometros") && select2.equals("Centimetros"))
        {
            double resultado = ConvertirDatosKilometros(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado) + " Centimetros");
        }
        if(select1.equals("Kilometros") && select2.equals("Pulgadas"))
        {
            double resultado = ConvertirDatosKilometros(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado) + " Pulgadas");
        }
        if(select1.equals("Kilometros") && select2.equals("Metros"))
        {
            double resultado = ConvertirDatosKilometros(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado) + " Metros");
        }
        if(select1.equals("Kilometros") && select2.equals("Pies"))
        {
            double resultado = ConvertirDatosKilometros(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado) + " Pies");
        }

        /*************************  Pies  *****************************/

        if(select1.equals("Pies") && select2.equals("Pies")) {
            result.setText(cant + " Pies");
            return;
        }
        if(select1.equals("Pies") && select2.equals("Milimetros"))
        {
            double resultado = ConvertirDatosPies(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado)+" Milimetros");
            return;
        }
        if(select1.equals("Pies") && select2.equals("Centimetros"))
        {
            double resultado = ConvertirDatosPies(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado) + " Centimetros");
        }
        if(select1.equals("Pies") && select2.equals("Pulgadas"))
        {
            double resultado = ConvertirDatosPies(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado) + " Pulgadas");
        }
        if(select1.equals("Pies") && select2.equals("Metros"))
        {
            double resultado = ConvertirDatosPies(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado) + " Metros");
        }
        if(select1.equals("Pies") && select2.equals("Kilometros"))
        {
            double resultado = ConvertirDatosPies(Double.valueOf((cant)), select2);
            result.setText(formato.format(resultado) + " Kilometros");
        }
    }


    /*
    *  CONVERSIONES DE LIBRAS A ----
    *
    * */
    public double convertirLibrasToKg(double cantidad)
    {
        double Kg = cantidad / 2.2046;
        return Kg;
    }

    public double convertirLibrasToGramos(double cantidad)
    {
        double grms = cantidad / 0.0022046;
        return grms;
    }

    public double convertirLibrasToOnzas(double cantidad)
    {
        double Onza = cantidad * 16.000;
        return Onza;
    }

    /*
    *  CONVERSIONES DE KG A ---
    * */
    public double convertirKgToLibras(double cantidad)
    {
        double Libras = cantidad * 2.2046;
        return Libras;
    }
    public double convertirKgToGramos(double cantidad)
    {
        double Gramos = cantidad / 0.0010000;
        return Gramos;
    }

    public double convertirKgToOnzas(double cantidad)
    {
        double Onza = cantidad * 35.274;
        return Onza;
    }
    /*
    *  CONVERSIONES DE GRAMOS A ---
    * */

    public double convertirGramosToKg(double cantidad)
    {
        double Kg = cantidad / 1000.0;
        return Kg;
    }

    public double convertirGramosToLibras(double cantidad)
    {
        double Libras = cantidad * 0.0022046;
        return Libras;
    }

    public double convertirGramosToOnzas(double cantidad)
    {
        double Onza = cantidad * 0.035274;
        return Onza;
    }

    /*
    *  CONVERSION DE ONZAS A ----
    * */

    public double convertirOnzasToGramos(double cantidad)
    {
        double gramos = cantidad / 0.035274;
        return gramos;
    }
    public double convertirOnzasToKg(double cantidad)
    {
        double kg = cantidad / 35.274;
        return kg;
    }
    public double convertirOnzasToLibras(double cantidad)
    {
        double libras = cantidad * 0.062500;
        return libras;
    }


    public double ConvertirDatosMilimetros(double cantidad, String tipo)
    {
        double resultado = 0;
        if(tipo.equals("Centimetros"))
        {
            resultado = cantidad / 10;
            return resultado;
        }
        if(tipo.equals("Pulgadas"))
        {
            resultado = cantidad * 0.039370;
            return resultado;
        }
        if(tipo.equals("Metros"))
        {
            resultado = cantidad / 1000;
            return resultado;
        }
        if(tipo.equals("Kilometros"))
        {
            resultado = cantidad / 1000000;
            return resultado;
        }
        if(tipo.equals("Pies"))
        {
            resultado = cantidad * 0.0032808;
            return resultado;
        }
        return 0;
    }

    public double ConvertirDatosCentimetros(double cantidad, String tipo)
    {
        double resultado = 0;
        if(tipo.equals("Milimetros"))
        {
            resultado = cantidad / 0.10000;
            return resultado;
        }
        if(tipo.equals("Pulgadas"))
        {
            resultado = cantidad * 0.39370;
            return resultado;
        }
        if(tipo.equals("Metros"))
        {
            resultado = cantidad / 100;
            return resultado;
        }
        if(tipo.equals("Kilometros"))
        {
            resultado = cantidad / 100000;
            return resultado;
        }
        if(tipo.equals("Pies"))
        {
            resultado = cantidad * 0.0032808;
            return resultado;
        }
        return 0;
    }
    public double ConvertirDatosPulgadas(double cantidad, String tipo)
    {
        double resultado = 0;
        if(tipo.equals("Milimetros"))
        {
            resultado = cantidad / 0.039370;
            return resultado;
        }
        if(tipo.equals("Centimetros"))
        {
            resultado = cantidad / 0.39370;
            return resultado;
        }
        if(tipo.equals("Metros"))
        {
            resultado = cantidad / 39.370;
            return resultado;
        }
        if(tipo.equals("Kilometros"))
        {
            resultado = cantidad / 39370;
            return resultado;
        }
        if(tipo.equals("Pies"))
        {
            resultado = cantidad * 0.083333;
            return resultado;
        }
        return 0;
    }

    public double ConvertirDatosMetros(double cantidad, String tipo)
    {
        double resultado = 0;
        if(tipo.equals("Milimetros"))
        {
            resultado = cantidad / 0.0010000;
            return resultado;
        }
        if(tipo.equals("Centimetros"))
        {
            resultado = cantidad / 0.010000;
            return resultado;
        }
        if(tipo.equals("Metros"))
        {
            resultado = cantidad * 39.370;
            return resultado;
        }
        if(tipo.equals("Kilometros"))
        {
            resultado = cantidad / 1000;
            return resultado;
        }
        if(tipo.equals("Pies"))
        {
            resultado = cantidad * 3.2808;
            return resultado;
        }
        return 0;
    }

    public double ConvertirDatosKilometros(double cantidad, String tipo)
    {
        double resultado = 0;
        if(tipo.equals("Milimetros"))
        {
            resultado = cantidad / 0.0000010000;
            return resultado;
        }
        if(tipo.equals("Centimetros"))
        {
            resultado = cantidad / 0.000010000;
            return resultado;
        }
        if(tipo.equals("Pulgadas"))
        {
            resultado = cantidad * 39370.08;
            return resultado;
        }
        if(tipo.equals("Metros"))
        {
            resultado = cantidad / 0.0010000;
            return resultado;
        }
        if(tipo.equals("Pies"))
        {
            resultado = cantidad * 3280.8;
            return resultado;
        }
        return 0;
    }

    public double ConvertirDatosPies(double cantidad, String tipo)
    {
        double resultado = 0;
        if(tipo.equals("Milimetros"))
        {
            resultado = cantidad / 0.0032808;
            return resultado;
        }
        if(tipo.equals("Centimetros"))
        {
            resultado = cantidad / 0.032808;
            return resultado;
        }
        if(tipo.equals("Metros"))
        {
            resultado = cantidad / 3.2808;
            return resultado;
        }
        if(tipo.equals("Kilometros"))
        {
            resultado = cantidad / 3280.8;
            return resultado;
        }
        if(tipo.equals("Pulgadas"))
        {
            resultado = cantidad * 12.000;
            return resultado;
        }
        return 0;
    }

}
//Double.valueOf(dosDecimas.format(Libras));