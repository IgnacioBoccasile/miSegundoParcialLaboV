package com.example.boccasilesplabov;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MiDialog extends DialogFragment
{
    private String titulo;

    private String mensaje;

    private String btnPositivo;

    private String btnNegativo;

    private String btnNeutral;

    private View view;

    private DialogInterface.OnClickListener listener;

    public MiDialog(View view)
    {
        this.view = view;
    }

    public MiDialog(String titulo, String mensaje, String btnPositivo, String btnNegativo, String btnNeutral, View view, DialogInterface.OnClickListener listener)
    {
        this.titulo = titulo;

        this.mensaje = mensaje;

        this.btnPositivo = btnPositivo;

        this.btnNegativo = btnNegativo;

        this.btnNeutral = btnNeutral;

        this.view = view;

        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(super.getActivity());

        if (!("".equals(this.titulo)) && this.titulo != null)
        {
            builder.setTitle(this.titulo);
        }

        if (!("".equals(this.mensaje)) && this.mensaje != null)
        {
            builder.setMessage(this.mensaje);
        }

        if (!("".equals(this.btnPositivo)) && this.btnPositivo != null)
        {
            builder.setPositiveButton(this.btnPositivo, this.listener);
        }

        if (!("".equals(this.btnNegativo)) && this.btnNegativo != null)
        {
            builder.setNegativeButton(this.btnNegativo, this.listener);
        }

        if (!("".equals(this.btnNeutral)) && this.btnNeutral != null)
        {
            builder.setNeutralButton(this.btnNeutral, this.listener);
        }

        if (this.view != null)
        {
            builder.setView(this.view);
        }

        return builder.create();
    }
}