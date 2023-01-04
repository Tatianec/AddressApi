package com.api.AddressApi.integrations;

import com.api.AddressApi.Dto.AddressDto;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class ViaCep {

    private final String url_via_cep = "https://viacep.com.br/ws/";

    public AddressDto findCep(String cep) {
        try {
            CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(formatarUrlCep(cep));
            HttpResponse response = closeableHttpClient.execute(request);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent()));
                String inputLine;
                StringBuilder responseString = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    responseString.append(inputLine);
                }
                in.close();
                closeableHttpClient.close();
                var address = new Gson().fromJson(responseString.toString(), AddressDto.class);

                return address;
            }else {
                closeableHttpClient.close();
                throw new RuntimeException("Erro!!!");
            }
        } catch (IOException ex) {
            throw new RuntimeException("Erro!!!");
        }
    }

    public String formatarUrlCep(String cep){
        return String.format(url_via_cep + cep + "/json");
    }
}

