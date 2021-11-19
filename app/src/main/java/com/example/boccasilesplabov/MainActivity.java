package com.example.boccasilesplabov;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Handler.Callback
{
    private List<Usuario> usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        this.usuarios = this.traerUsuarios();

        this.actualizarTV();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem miMenuItem = menu.findItem(R.id.buscar);

        ListenerSearchView miListenerSearchView = new ListenerSearchView(this.usuarios, this);

        SearchView miSearchView = (SearchView) miMenuItem.getActionView();

        miSearchView.setOnQueryTextListener(miListenerSearchView);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.agregar_usuario)
        {
            View miViewDialog = LayoutInflater.from(this).inflate(R.layout.crear_usuario, null);

            ListenerCrearUsuario miListenerCrearUsuario = new ListenerCrearUsuario(this, miViewDialog, this.usuarios);

            Button buttonCancelar = miViewDialog.findViewById(R.id.buttonCancelar);

            Button buttonGuardar = miViewDialog.findViewById(R.id.buttonGuardar);

            CompoundButton tbAdmin = miViewDialog.findViewById(R.id.tbAdmin);

            Spinner spinRol = miViewDialog.findViewById(R.id.spinRol);

            buttonCancelar.setOnClickListener(miListenerCrearUsuario);

            buttonGuardar.setOnClickListener(miListenerCrearUsuario);

            tbAdmin.setOnCheckedChangeListener(miListenerCrearUsuario);

            spinRol.setOnItemSelectedListener(miListenerCrearUsuario);

            ArrayAdapter<CharSequence> miAdapter = ArrayAdapter.createFromResource(this, R.array.arrayDeRoles, android.R.layout.simple_spinner_item);

            miAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinRol.setAdapter(miAdapter);

            MiDialog miDialog = new MiDialog("Crear Usuario", null, null, null, null, miViewDialog, null);

            miListenerCrearUsuario.setDialog(miDialog);

            miDialog.show(this.getSupportFragmentManager(), "Dialog agregar contacto");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<Usuario> traerUsuarios()
    {
        SharedPreferences misPreferences = this.getSharedPreferences("misUsuarios", Context.MODE_PRIVATE);

        String miString = misPreferences.getString("usuarios", "sinUsuarios");

        if ("sinUsuarios".equals(miString))
        {
            Handler miHandler = new Handler(this);

            MiConsulta miHiloHttp = new MiConsulta(miHandler);

            miHiloHttp.start();

            return new ArrayList<Usuario>();
        }
        else
        {
            return this.parsearJsonUsuarios(miString);
        }
    }

    private List<Usuario> parsearJsonUsuarios(String string)
    {
        List<Usuario> usuarios = new ArrayList<>();

        try
        {
            JSONArray miJsonArray = new JSONArray(string);

            for (int contador = 0; contador < miJsonArray.length(); contador++)
            {
                JSONObject miJsonObject = miJsonArray.getJSONObject(contador);

                Integer id = Integer.valueOf(miJsonObject.getString("id"));

                String username = miJsonObject.getString("username");

                String rol = miJsonObject.getString("rol");

                Boolean admin = Boolean.valueOf(miJsonObject.getString("admin"));

                Usuario usuario = new Usuario(id, username, rol, admin);

                usuarios.add(usuario);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return usuarios;
    }

    @Override
    public boolean handleMessage(@NonNull Message msg)
    {
        this.usuarios = this.parsearJsonUsuarios(msg.obj.toString());

        this.actualizarTV();

        this.actualizarSP();

        return false;
    }

    public void actualizarTV()
    {
        TextView textView = this.findViewById(R.id.usuarios);

        textView.setText(this.usuarios.toString());
    }

    public void actualizarSP()
    {
        SharedPreferences preferences = this.getSharedPreferences("misUsuarios", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("usuarios", this.usuarios.toString());

        editor.commit();
    }
}