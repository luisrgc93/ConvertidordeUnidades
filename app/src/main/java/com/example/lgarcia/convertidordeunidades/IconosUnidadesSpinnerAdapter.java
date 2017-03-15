package com.example.lgarcia.convertidordeunidades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/**
 * Created by lgarcia on 23/02/2017.
 */

public class IconosUnidadesSpinnerAdapter extends ArrayAdapter<IconosUnidades>
{
    private Context context;

    List<IconosUnidades> datos = null;

    public IconosUnidadesSpinnerAdapter(Context context, List<IconosUnidades> datos)
    {
        //se debe indicar el layout para el item que seleccionado (el que se muestra sobre el botón del botón)
        super(context, R.layout.spinner_selected_item, datos);
        this.context = context;
        this.datos = datos;
    }

    //este método establece el elemento seleccionado sobre el botón del spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.spinner_selected_item,null);
        }
        ((TextView) convertView.findViewById(R.id.texto)).setText(datos.get(position).getName());
        ((ImageView) convertView.findViewById(R.id.icono)).setBackgroundResource(datos.get(position).getIcon());

        return convertView;
    }

    //gestiona la lista usando el View Holder Pattern. Equivale a la típica implementación del getView
    //de un Adapter de un ListView ordinario
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        if (row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.spinner_list, parent, false);
        }

        if (row.getTag() == null)
        {
            IconosUnidadesHolder iconosUnidadesHolder = new IconosUnidadesHolder();
            iconosUnidadesHolder.setIcono((ImageView) row.findViewById(R.id.icono));
            iconosUnidadesHolder.setTextView((TextView) row.findViewById(R.id.texto));
            row.setTag(iconosUnidadesHolder);
        }

        //rellenamos el layout con los datos de la fila que se está procesando
        IconosUnidades iconosUnidades = datos.get(position);
        ((IconosUnidadesHolder) row.getTag()).getIcono().setImageResource(iconosUnidades.getIcon());
        ((IconosUnidadesHolder) row.getTag()).getTextView().setText(iconosUnidades.getName());

        return row;
    }

    /**
     * Holder para el Adapter del Spinner
     * @author danielme.com
     *
     */

    private static class IconosUnidadesHolder
    {

        private ImageView icono;

        private TextView textView;

        public ImageView getIcono()
        {
            return icono;
        }

        public void setIcono(ImageView icono)
        {
            this.icono = icono;
        }

        public TextView getTextView()
        {
            return textView;
        }

        public void setTextView(TextView textView)
        {
            this.textView = textView;
        }

    }
}
