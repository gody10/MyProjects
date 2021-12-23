package com.example.mmlabapp;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;



public class MessageSender extends AsyncTask<String,Void,Void>
{
    Socket s;
    DataOutputStream dos;
    PrintWriter pw;
    @Override
    protected Void doInBackground(String... voids) {

        String username = voids[0];
        String password = voids[1];
        String message = username + ":" + password;
        try{
            s = new Socket("192.168.2.8",7800);
            pw = new PrintWriter(s.getOutputStream());
            pw.write(message);
            pw.flush();
            pw.close();
            s.close();


        }
        catch (IOException e){
            e.printStackTrace();
        }


        return null;
    }

}
