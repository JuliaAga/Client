package com.common;
/*Класс для передачи и получения сообщения из сокета.
Сообщение получает и возвращает в виде строки.*/

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;

public class MessageUtils {
    private static final Logger logger = LogManager.getLogger(MessageUtils.class);

    public static String getMessage(Socket socket) {
        String word = "";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            word = in.readLine();
        } catch (IOException e) {
            logger.error("Ошибка при попытке принять сообщение. " + e.getMessage());
        }
        return word;

    }

    public static void sendMessage(Socket socket, String message) {
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.write(message);
            out.flush();
        } catch (IOException e) {
            logger.error("Не удалось отправить ответное сообщение. " + e.getMessage());
        }
    }
}
