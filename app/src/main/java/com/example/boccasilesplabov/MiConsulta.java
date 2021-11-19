package com.example.boccasilesplabov;

import android.os.Handler;
import android.os.Message;

public class MiConsulta extends Thread
{
    private Handler handler;

    public MiConsulta(Handler handler)
    {
        this.handler = handler;
    }

    @Override
    public void run()
    {
        HttpConnection miConnectionHTTP = new HttpConnection();

        byte[] respuestaJson = miConnectionHTTP.obtenerRespuesta("http://10.20.75.63:3001/usuarios");

        String respuestaString = new String(respuestaJson);

        Message msg = new Message();

        msg.obj = respuestaString;

        this.handler.sendMessage(msg);
    }
}