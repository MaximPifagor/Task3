package Wallet.Client;

import Wallet.WalletService.WalletServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class WalletBeginning extends JFrame implements Runnable {
    JPanel jPanel;
    JPanel jPanelSignUp;
    JPanel jPanelSignIn;
    JPanel jPanelMain;
    Socket socket;
    JTextField loginUp;
    JTextField passwordUp;
    JTextField loginIn;
    JTextField passwordIn;
    DataInputStream in;
    DataOutputStream out;
    JLabel mainPanelLogin;
    JLabel getMainPanelAccount;
    JTextField sentTo;
    JTextField sum;
    Boolean listenServer = true;
    static int i = 0;

    @Override
    public void run() {
        while (listenServer) {
            try {
                Thread.currentThread().sleep(100);
            } catch (Exception e) {
            }
            synchronized (WalletBeginning.class) {
                try {
                    System.out.println("run s" + i);
                    String info = in.readUTF();
                    System.out.println(info);
                    System.out.println("run f" + i++);
                    if (info.startsWith("info")) {
                        String[] arr = info.split(":");
                        if (arr[0].equals("info")) {
                            mainPanelLogin.setText(arr[1]);
                            getMainPanelAccount.setText(arr[2]);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("hello");
                }
            }
        }
    }

    public WalletBeginning(Socket socket) throws HeadlessException {
        super("Wallet");
        this.socket = socket;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println("exceptionWalletBegining");
        }
        jPanel = new JPanel();
        JButton enter = new JButton("Войти");
        enter.addActionListener(new Enter());
        JButton registration = new JButton("Зарегистрироваться");
        registration.addActionListener(new Sign());
        jPanel.add(enter);
        jPanel.add(registration);
        add(jPanel, BorderLayout.CENTER);
        createSignUpPanel();
        createSignInPanel();
        createMainPanel();
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    void createSignUpPanel() {
        jPanelSignUp = new JPanel(new GridLayout(3, 2));
        JLabel labelLogin = new JLabel("Введиет логин");
        JLabel labelPassword = new JLabel("Введите пароль");
        loginUp = new JTextField();
        passwordUp = new JTextField();
        JButton signup = new JButton("Зарегистрироваться");
        signup.addActionListener(new SingUp());
        jPanelSignUp.add(labelLogin);
        jPanelSignUp.add(loginUp);
        jPanelSignUp.add(labelPassword);
        jPanelSignUp.add(passwordUp);
        jPanelSignUp.add(signup);
        jPanelSignUp.setVisible(true);
    }

    void createSignInPanel() {
        jPanelSignIn = new JPanel(new GridLayout(3, 2));
        JLabel labelLogin = new JLabel("Введиет логин");
        JLabel labelPassword = new JLabel("Введите пароль");
        loginIn = new JTextField();
        passwordIn = new JTextField();
        JButton signup = new JButton("Войти");
        signup.addActionListener(new SignIn());
        jPanelSignIn.add(labelLogin);
        jPanelSignIn.add(loginIn);
        jPanelSignIn.add(labelPassword);
        jPanelSignIn.add(passwordIn);
        jPanelSignIn.add(signup);
        jPanelSignIn.setVisible(true);
    }

    void createMainPanel() {
        jPanelMain = new JPanel(new GridLayout(1, 2));
        JPanel subPanel = new JPanel(new GridLayout(4, 1));
        JLabel label1 = new JLabel("Логин");
        JLabel label2 = new JLabel("Счет");
        mainPanelLogin = new JLabel("");
        getMainPanelAccount = new JLabel("");
        subPanel.add(label1);
        subPanel.add(mainPanelLogin);
        subPanel.add(label2);
        subPanel.add(getMainPanelAccount);
        jPanelMain.add(subPanel);
        JPanel panelSendTo = new JPanel(new GridLayout(4, 1));
        JLabel labelSend = new JLabel("Перевести");
        sentTo = new JTextField();
        sum = new JTextField();
        JButton send = new JButton("Отправить");
        send.addActionListener(new Send());
        panelSendTo.add(labelSend);
        panelSendTo.add(sentTo);
        panelSendTo.add(sum);
        panelSendTo.add(send);
        jPanelMain.add(panelSendTo);
        jPanelMain.setVisible(true);
    }

    class Send implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("sendBlock");
            String to = sentTo.getText();
            sentTo.setText("");
            String s = sum.getText();
            sum.setText("");
            try {
                out.writeUTF("send:" + to + ":" + s);
            } catch (Exception ex) {
                ex.printStackTrace();
            }


        }
    }

    class Enter implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("enterBlock");
            jPanel.setVisible(false);
            remove(jPanel);
            add(jPanelSignIn);

        }
    }

    class Sign implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("SignBlock");
            add(jPanelSignUp);
            remove(jPanel);
            repaint();
            setVisible(true);
        }
    }

    class SignIn implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("singInBlock");
            String log = loginIn.getText();
            String pas = passwordIn.getText();
            try {
                synchronized (WalletBeginning.class) {
                    out.writeUTF("auth:" + log + ":" + pas);
                    Thread.sleep(10);
                    String resp = in.readUTF();
                    if (resp.startsWith("suc")) {
                        out.writeUTF("info");
                        Thread.sleep(10);
                        String[] r = in.readUTF().split(":");
                        mainPanelLogin.setText(r[1]);
                        getMainPanelAccount.setText(r[2]);
                        add(jPanelMain);
                        remove(jPanelSignIn);
                        repaint();
                        setVisible(true);
                        Thread thread = new Thread(WalletBeginning.this);
                        thread.start();
                    } else {
                        loginIn.setText("");
                        passwordIn.setText("");
                    }
                }
            } catch (Exception e1) {

            }
        }
    }

    class SingUp implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("singupBlock");
            String log = loginUp.getText();
            String pas = passwordUp.getText();
            try {
                String s = "reg:" + log + ":" + pas;
                String resp = "";
                synchronized (WalletBeginning.class) {
                    out.writeUTF(s);
                    Thread.sleep(10);
                    resp = in.readUTF();
                }
                System.out.println(resp);
                if (resp.startsWith("suc")) {
                    loginUp.setText("");
                    passwordUp.setText("");
                    add(jPanel);
                    remove(jPanelSignUp);
                    repaint();
                    setVisible(true);
                } else {
                    loginUp.setText("");
                    passwordUp.setText("");
                }
            } catch (Exception ex) {
                System.out.println("exception");
            }

        }
    }

}
