package com.pb.shevchuk.hw14;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class Client extends JFrame implements ActionListener {
    private static final int PORT = Server.PORT;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(Client::new);
    }

    private String name;
    BufferedReader server;
    PrintWriter writer;
    private final JTextArea log = new JTextArea();
    private final JTextField input = new JTextField();

    private Client() {
        try {
            Socket socket = new Socket("localhost", PORT);
            server = new BufferedReader(new InputStreamReader(socket.getInputStream(), Server.UTF8));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), Server.UTF8), true);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        log.setEditable(false);
        log.setLineWrap(true);
        log.setText("введіть ваше ім'я\n");
        add(log, BorderLayout.CENTER);

        input.addActionListener(this);
        add(input, BorderLayout.SOUTH);

        setVisible(true);
        new Thread(this::listenMessage).start();
    }

    private void listenMessage() {
        String message;
        try {
            while ((message = server.readLine()) != null) {
                print(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void print(String message) {
        SwingUtilities.invokeLater(() -> {
            log.append(message + "\n");
            log.setCaretPosition(log.getDocument().getLength());
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String message = input.getText();

        if (name == null) {
            name = (message.length() != 0) ? message : "anonim";
            setTitle(name);
            message = name;
            log.setText("введіть повідомлення\n");
        } else {
            message = input.getText();
            if (message.equals("")) return;
        }
        writer.println(message);
        input.setText("");
    }
}
