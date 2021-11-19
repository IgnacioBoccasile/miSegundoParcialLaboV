package com.example.boccasilesplabov;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.DialogFragment;
import java.util.List;

public class ListenerCrearUsuario implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener
{
    private DialogFragment dialog;

    private View viewDialog;

    private Activity activity;

    private List<Usuario> usuarios;

    private Usuario usuario;

    public ListenerCrearUsuario(Activity activity, View viewDialog, List<Usuario> usuarios)
    {
        this.activity = activity;

        this.viewDialog = viewDialog;

        this.usuarios = usuarios;

        this.usuario = new Usuario();

        this.usuario.setAdmin(false);

        this.usuario.setRol("Supervisor");
    }

    @Override
    public void onClick(View viewClicked)
    {
        if (viewClicked.getId() == R.id.buttonCancelar)
        {
            this.dialog.dismiss();
        }
        else if (viewClicked.getId() == R.id.buttonGuardar)
        {
            if (this.validarCampos())
            {
                String username = ((EditText) this.viewDialog.findViewById(R.id.inputNombreUsuario)).getText().toString();

                this.usuario.setId(this.generarId());

                this.usuario.setUsername(username);

                this.usuarios.add(this.usuario);

                ((MainActivity) this.activity).actualizarSP();

                ((MainActivity) this.activity).actualizarTV();

                this.dialog.dismiss();
            }
            else
            {
                Toast.makeText(viewDialog.getContext(), "Los datos están incompletos!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        this.usuario.setAdmin(Boolean.valueOf(isChecked));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        if (position == 0)
        {
            this.usuario.setRol("Supervisor");
        }

        else if (position == 1)
        {
            this.usuario.setRol("Construction Manager");
        }

        else if (position == 2)
        {
            this.usuario.setRol("Project Manager");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    public void setDialog(DialogFragment dialog)
    {
        this.dialog = dialog;
    }

    private boolean validarCampos()
    {
        EditText inputUsername = this.viewDialog.findViewById(R.id.inputNombreUsuario);

        if (inputUsername.getText().toString().isEmpty())
        {
            return false;
        }

        return true;
    }

    private int generarId()
    {
        int id_anterior = 0;

        for (int contador = 0; contador < this.usuarios.size(); contador++)
        {
            if (this.usuarios.get(contador).getId() == null)
            {
                continue;
            }

            int id = this.usuarios.get(contador).getId();

            if (id > id_anterior)
            {
                id_anterior = id;
            }
        }

        return id_anterior + 1;
    }
}