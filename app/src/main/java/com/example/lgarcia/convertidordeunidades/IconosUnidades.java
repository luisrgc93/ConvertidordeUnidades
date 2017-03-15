package com.example.lgarcia.convertidordeunidades;

/**
 * Created by lgarcia on 23/02/2017.
 */

public class IconosUnidades {
    private String name;

    private int icon;

    public IconosUnidades(String nombre, int icono)
    {
        super();
        this.name = nombre;
        this.icon = icono;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getIcon()
    {
        return icon;
    }

    public void setIcon(int icon)
    {
        this.icon = icon;
    }
}
