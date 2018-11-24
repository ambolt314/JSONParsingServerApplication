/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author 611213417
 */
public class HTTP {
    
    public static String sendGET(String serverURL) throws IOException {
        
        //creates GET URL from string
        URL getConnection = new URL(serverURL);
        HttpURLConnection con = (HttpURLConnection) getConnection.openConnection();
        con.setRequestMethod("GET");
        
        //returns an integer value (200 = OK)
        int responseCode = con.getResponseCode();
//        System.out.println("GET Response Code :: " + responseCode);
        
        StringBuilder response = new StringBuilder();
        
        if (responseCode == HttpURLConnection.HTTP_OK) { 
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
//            System.out.println(response.toString());
        } 
        else {
            System.err.println("Error: expected HTTP 200. Got HTTP " + responseCode);
        }
        return response.toString();
    }
    
    public static int sendPOST(String answer, String serverURL) throws IOException {
        
        //creates POST URL from string
        URL postConnection = new URL(serverURL);
        System.out.println(answer);
        HttpURLConnection con = (HttpURLConnection) postConnection.openConnection();
        System.out.println("Post");
        con.setRequestMethod("POST");
        
        String urlParameters = answer;
        
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        System.out.println("wr");
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
        
        int responseCode = con.getResponseCode();
        System.out.println(responseCode);
        
        return responseCode;
    }
}
