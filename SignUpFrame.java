package com.ui;

import com.object.CodeUtil;
import com.object.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class SignUpFrame extends JFrame implements ActionListener {
    File file = new File("/Users/emmazhang/IdeaProjects/sliding_puzzle/src/userinfo.txt");
    ArrayList<User> userList;
    JButton registerBtn = new JButton();
    JButton resetBtn = new JButton();

    JTextField usernameField = new JTextField( 10);
    JTextField pwdField = new JTextField( 10);
    JTextField rePwdField = new JTextField( 10);

    public SignUpFrame(ArrayList<User> userList) throws IOException {
        this.userList = userList;
        initFrame();
        initView();
        this.setVisible(true);
    }



    private void initView() {
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(130, 240, 80, 30);
        this.getContentPane().add(usernameLabel);

        usernameField.setBounds(200, 240, 150, 30);
        this.getContentPane().add(usernameField);

        JLabel pwdLabel = new JLabel("Password");
        pwdLabel.setBounds(130, 290, 80, 30);
        this.getContentPane().add(pwdLabel);

        pwdField.setBounds(200, 290, 150, 30);
        this.getContentPane().add(pwdField);

        JLabel rePwdLabel = new JLabel("Password");
        rePwdLabel.setBounds(130, 340, 80, 30);
        this.getContentPane().add(rePwdLabel);

        rePwdField.setBounds(200, 340, 150, 30);
        this.getContentPane().add(rePwdField);



        //login button
        registerBtn.setIcon(new ImageIcon("src/image/login/signup.png"));
        registerBtn.setBounds(160,400, 80, 40);
        registerBtn.setBorderPainted(false);
        registerBtn.setContentAreaFilled(false);
        this.getContentPane().add(registerBtn);
        registerBtn.addActionListener(this);

        //sign up button

        resetBtn.setBounds(280,400, 80, 39);
        resetBtn.setIcon(new ImageIcon("src/image/login/signup.png"));
        resetBtn.setBorderPainted(false);
        resetBtn.setContentAreaFilled(false);
        this.getContentPane().add(resetBtn);
        resetBtn.addActionListener(this);

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
        String userName = usernameField.getText();
        String pwd = pwdField.getText();
        String rePwd = rePwdField.getText();
        if (obj == registerBtn) {
            if (userName.length() == 0 || pwd.length() == 0 || rePwd.length() == 0) {
                showDialog("Username or password cannot be empty");
            } else if (!pwd.equals(rePwd)) {
                showDialog("Inconsistency in passwords");
            } else if (contains(userName)) {
                showDialog("Username already exists");
            } else {
                User user = new User(userName, pwd);
                userList.add(user);
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                    for (User u : userList) {
                        bw.write("username=" + u.getUsername() + "&password=" + pwd);
                        bw.newLine();
                    }
                    bw.close();
                    showDialog("Register Successfully");
                    this.setVisible(false);
                    new SignInFrame();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        } else if (obj == resetBtn) {
            usernameField.setText("");
            pwdField.setText("");
            rePwdField.setText("");
        }

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
    public boolean contains(String name) {
        for (User user : userList) {
           if (name.equals(user.getUsername())) {
               return true;
           }
        }
        return false;
    }
}
