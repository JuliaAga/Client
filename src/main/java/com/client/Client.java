package com.client;
/*Класс, реализующий всю работу клиента: запуск, общение с сервером, закрытие.
 * Конструктор по полученному имени файла (который расположен в resources) берет информацию о адресе
 * сервера и подключается к нему.
 * Метод Process обрабатывает пользователя - заправшивает у пользователя имя, фамилию и сообщение;
 *  взаимодействует с сервером.*/

import com.common.MessageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

public class Client {

    private static Socket clientSocket;
    private static final Logger logger = LogManager.getLogger(Client.class);
    private String host;
    private Integer port;
    private UserMessage userMessage;

    public Client(String configFileName) {

        getServerAddressFromIni(configFileName);

        try {
            clientSocket = new Socket(host, port);
            System.out.println(String.format("Имя сервера: %s:%s", host, port));
            logger.info("Подключение к серверу установлено.");
        } catch (UnknownHostException uEx) {
            logger.error("Неизвестный адрес " + host + ":" + port + " " + uEx);
        } catch (IOException e) {
            logger.error(e);
        }

    }

    private void getServerAddressFromIni(String configFileName) throws NullPointerException {
        Properties property = new Properties();
        try {
            InputStream inputStream = Client.class.getClassLoader().getResourceAsStream(configFileName);
            property.load(inputStream);
            host = property.getProperty("server");
            port = Integer.parseInt(property.getProperty("port"));
            logger.info("Получены данные из конфигурации");
        } catch (IOException e) {
            logger.error(e);
        } catch (NullPointerException nEx) {
            String error = "Не удалось разобрать файл с инфо для подключения " + configFileName + " " + nEx;
            logger.error(error);
            throw new NullPointerException(error);
        }
    }

    public void process() {
        askUserInfo();
        processOutputMessage();
        processInputMessage();
    }

    private void processOutputMessage() {
        String xmlMessage = XMLMessageWriter.makeXmlMessageInString(userMessage, true);
        MessageUtils.sendMessage(clientSocket, xmlMessage);
        MessageUtils.sendMessage(clientSocket, "\n");
        logger.info("Сформировано сообщение для сервера.");
    }

    private void processInputMessage() {
        String serverAnswer = MessageUtils.getMessage(clientSocket);
        String serverMessage = ServerMessageParser.parseServerMessage(serverAnswer, true);
        if (serverMessage.length() != 0) {
            System.out.println(serverMessage);
            logger.info("Обработан ответ сервера");
        } else {
            System.out.println("Не удалось разобрать ответ сервера");
            logger.info("Не удалось разобрать ответ сервера");
        }

    }

    public void closeClient() {
        if (clientSocket != null) {
            try {
                clientSocket.close();
                System.out.println("Клиент закрыт");
                logger.info("Клиент закрыт");
            } catch (IOException e) {
                logger.error("Не смог закрыть сокет. " + e);

            }
        }
    }

    private void askUserInfo() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        userMessage = new UserMessage();
        try {
            System.out.println("Введите имя: ");
            userMessage.setName(reader.readLine());
            System.out.println("Введите фамилию: ");
            userMessage.setSecondname(reader.readLine());
            System.out.println("Введите сообщение: ");
            userMessage.setMessage(reader.readLine());
        } catch (IOException ex) {
            logger.error("Произошла ошибка при запросе информации пользователя. " + ex);
        }
    }


}

