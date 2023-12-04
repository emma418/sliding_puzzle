package com.ui;

import com.object.CodeUtil;
import com.object.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static javax.swing.JColorChooser.showDialog;

public class SignInFrame extends JFrame implements ActionListener {

    ArrayList<User> list = new ArrayList<User>();
//    static {
//        list.add(new User("emma", "123456"));
//    }
    File file = new File("/Users/emmazhang/IdeaProjects/sliding_puzzle/src/userinfo.txt");


    JButton loginBtn = new JButton();
    JButton signUpBtn = new JButton();
    JTextField veriField = new JTextField( 10);
    JLabel code = new JLabel();
    JTextField usernameField = new JTextField( 10);
    JPasswordField pwdField = new JPasswordField( 10);

    public SignInFrame() throws IOException {
        readInfo();
        initFrame();
        initView();
        this.setVisible(true);
    }

    private void readInfo() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            String name = line.split("&")[0].split("=")[1];
            String password = line.split("&")[1].split("=")[1];
            int count = Integer.parseInt(line.split("&")[2].split("=")[1]);
            User user = new User(name, password, count);
            list.add(user);
        }
        br.close();
    }

    private void initView() {
        JLabel usernameLabel = new JLabel(new ImageIcon("src/image/login/user.png"));
        usernameLabel.setBounds(160, 240, 30, 30);
        this.getContentPane().add(usernameLabel);

        usernameField.setBounds(200, 240, 150, 30);
        this.getContentPane().add(usernameField);

        JLabel pwdLabel = new JLabel(new ImageIcon("src/image/login/password.png"));
        pwdLabel.setBounds(160, 290, 30, 30);
        this.getContentPane().add(pwdLabel);

        pwdField.setBounds(200, 290, 150, 30);
        this.getContentPane().add(pwdField);

        JLabel veriLabel = new JLabel(new ImageIcon("src/image/login/verification.png"));
        veriLabel.setBounds(160, 340, 30, 30);
        this.getContentPane().add(veriLabel);

        veriField.setBounds(200, 340, 100, 30);
        this.getContentPane().add(veriField);

        code.setText(CodeUtil.getCode());
        code.setBounds(305, 340, 50, 30);
        this.getContentPane().add(code);

        //login button
        loginBtn.setIcon(new ImageIcon("src/image/login/login.png"));
        loginBtn.setBounds(160,400, 80, 40);
        loginBtn.setBorderPainted(false);
        loginBtn.setContentAreaFilled(false);
        this.getContentPane().add(loginBtn);
        loginBtn.addActionListener(this);

        //sign up button

        signUpBtn.setBounds(280,400, 80, 39);
        signUpBtn.setIcon(new ImageIcon("src/image/login/signup.png"));
        signUpBtn.setBorderPainted(false);
        signUpBtn.setContentAreaFilled(false);
        this.getContentPane().add(signUpBtn);
        signUpBtn.addActionListener(this);

        JLabel background = new JLabel(new ImageIcon("src/image/login/slide-puzzle.png"));
        background.setBounds(0, 0, 504, 502);
        this.getContentPane().add(background);
        this.getContentPane().repaint();
    }

    private void initFrame() {
        this.setSize(504, 502);
        this.setTitle("Sliding Puzzle");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        String username = usernameField.getText();
        String password = pwdField.getText();
        int index;
        if (obj == loginBtn) {
            if (veriField.getText().length() == 0){
                showDialog("Verification cannot be null");
            }else if (username.equals("") || password == "") {
                showDialog("Username or password cannot be null");
                code.setText(CodeUtil.getCode());
            } else if (!veriField.getText().equals(code.getText())) {
                showDialog("Verification code is wrong, please try again");
            } else if ((index = findUserByName(list,username)) != -1) {
                User user = list.get(index);
                int count = user.getCount();
                String rightPwd = user.getPassword();
                if (password.equals(rightPwd) && count < 3) {
                    new GameFrame(user);
                    this.setVisible(false);
                    list.set(index, new User(username, rightPwd, 0));
                    try {
                        writeInfo(list);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    if (count < 3) {
                        count++;
                        list.set(index, new User(username, rightPwd, count));
                        showDialog("Password is wrong, you have made " + count + " errors, and you have "
                                + (3-count) + " more chances");
                    } else {
                        showDialog("You have made three errors, your account is locked");
                    }
                    try {
                        writeInfo(list);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            } else {
                showDialog("User does not exit");
                code.setText(CodeUtil.getCode());
            }

        }
        if (obj == signUpBtn) {
            this.setVisible(false);
            try {
                new SignUpFrame(list);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    public void writeInfo(ArrayList<User> list) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        for (User user : list) {
            bw.write("username=" +user.getUsername()+ "&password=" +user.getPassword()+ "&count=" + user.getCount());
            bw.newLine();
        }
        bw.close();
    }

    public int findUserByName(ArrayList<User> list, String name) {
        for (int i = 0; i < list.size(); i++) {
            if (name.equals(list.get(i).getUsername())) {
                return i;
            }
        }
        return -1;
    }

    private void showDialog(String prompt) {
        JDialog message = new JDialog();
        message.setSize(200, 150);
        message.setAlwaysOnTop(true);
        message.setLocationRelativeTo(null);
        message.setModal(true);


        JTextArea content = new JTextArea(prompt);
        //content.setBounds(0,0,200,150);
        content.setWrapStyleWord(true);
        content.setLineWrap(true);
        content.setEditable(false);
        //content.setCaretPosition(0);
        message.getContentPane().add(content);

        message.setVisible(true);
    }
}
