package Wallet.Client2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WalletBeginning extends JFrame implements Runnable {
    volatile StatesClient state;
    JPanel jPanel;
    JPanel jPanelSignUp;
    JPanel jPanelSignIn;
    JPanel jPanelUsing;
    JTextField loginUp;
    JTextField passwordUp;
    JTextField loginIn;
    JTextField passwordIn;
    JLabel mainPanelLogin;
    JLabel mainPanelAccount;
    JTextField sentTo;
    JTextField sum;
    SocketController controller;
    static int i = 0;

    @Override
    public void run() {
        while (true) {
            String s = controller.getRespond();
            if (s != null) {
                execute(s);
            }
        }
    }


    public void execute(String str) {
        if (state == StatesClient.SignUp && str.startsWith("suc:reg")) {
            System.out.println();
            return;
        }
        if (state == StatesClient.SingIn && str.startsWith("suc:auth")) {
            state = StatesClient.Using;
            jPanelSignIn.setVisible(false);
            remove(jPanelSignIn);
            jPanelUsing.setVisible(true);
            add(jPanelUsing);
            repaint();
            setVisible(true);
            controller.post("info");
            return;
        }
        if (state == StatesClient.Using && str.startsWith("suc:info")) {
            mainPanelLogin.setText(str.split(":")[2]);
            mainPanelAccount.setText(str.split(":")[3]);
            repaint();
            return;
        }
        if (state == StatesClient.Using && str.startsWith("update")) {
            mainPanelAccount.setText(str.split(":")[1]);
            return;
        }
        if (state == StatesClient.SignUp && str.startsWith("suc:reg")) {
            return;
        }
    }


    public WalletBeginning(SocketController controller) throws HeadlessException {
        super("Wallet");
        this.controller = controller;
        new Thread(this).start();
        state = StatesClient.Start;
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
        JButton b = new JButton("назад");
        b.addActionListener(new Quit());
        jPanelSignUp.add(b);
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
        jPanelUsing = new JPanel(new GridLayout(1, 2));
        JPanel subPanel = new JPanel(new GridLayout(5, 1));
        JLabel label1 = new JLabel("Логин");
        JLabel label2 = new JLabel("Счет");
        mainPanelLogin = new JLabel("");
        mainPanelAccount = new JLabel("");
        subPanel.add(label1);
        subPanel.add(mainPanelLogin);
        subPanel.add(label2);
        subPanel.add(mainPanelAccount);
        JButton b = new JButton("ВЫЙТИ");
        b.addActionListener(new Quit());
        subPanel.add(b);
        jPanelUsing.add(subPanel);
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
        jPanelUsing.add(panelSendTo);
        jPanelUsing.setVisible(true);
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
                controller.post("send:" + to + ":" + s);
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
            state = StatesClient.SingIn;

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
            state = StatesClient.SignUp;
        }
    }

    class SignIn implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("singInBlock");
            String log = loginIn.getText();
            String pas = passwordIn.getText();
            try {
                controller.post("auth:" + log + ":" + pas);
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
                controller.post(s);
                loginUp.setText("");
                passwordUp.setText("");
            } catch (Exception ex) {
                System.out.println("exception");
            }
        }
    }

    class Quit implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (state == StatesClient.Using)
                controller.post("quit");
            jPanelSignUp.setVisible(false);
            remove(jPanelSignUp);
            jPanel.setVisible(true);
            add(jPanel);
            state = StatesClient.Start;
        }
    }

}
