package com.client;


public class Main {
    public static void main(String[] args)  {

        try {
            Client client = new Client("server.ini");
            client.process();
            client.closeClient();
        }
        catch (NullPointerException e){
            System.out.println("Не удалось запустить клиента. "+ e);
        }

    }
}
