package com.ui;

import com.object.GameInfo;
import com.object.User;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;

public class GameFrame extends JFrame implements MouseListener, KeyListener, ActionListener {

    User user;

    //record every picture's current location
    int[][] data = new int[4][4];
    int[] tempArr = {0, 1, 2, 3, 4, 5, 6, 7,8,9,10,11,12,13,14,15};

    //data when the game is win
    int[][] win = new int[][] {
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,0}
    };

    //record blank picture
    int x = 0;
    int y = 0;
    String path = "/Users/emmazhang/IdeaProjects/sliding_puzzle/src/image/animal/animal1/";
    int moves = 0;
    JMenuItem restart = new JMenuItem("Reshuffle");
    JMenuItem longin = new JMenuItem("Login");
    JMenuItem exit = new JMenuItem("Exit");
    JMenuItem linkedin = new JMenuItem("My Linkedin");
    JMenu changeImage = new JMenu("Change Image");
    JMenuItem animal = new JMenuItem("Animal");
    JMenuItem sport = new JMenuItem("Sport");
    JMenuItem save = new JMenuItem("Save");
    JMenuItem load = new JMenuItem("Load");

    public GameFrame(User user) {
        this.user = user;
        initFrame();

        initMenuBar();

        initData();
        initImage();

        this.setVisible(true);

    }

    private void initData() {

        Random r = new Random();

        for (int i = 0; i < tempArr.length; i++) {
            int randomIndex = r.nextInt(15);
            int temp = tempArr[i];
            tempArr[i] = tempArr[randomIndex];
            tempArr[randomIndex] = temp;
        }

        for (int i = 0; i < tempArr.length; i++) {
            data[i / 4][i % 4] = tempArr[i];
            if(tempArr[i] == 0) {
                x = i / 4;
                y = i % 4;
            }
        }
    }

    private void initImage() {
        getContentPane().removeAll();
        if(isWin()) {
            JLabel win = new JLabel(new ImageIcon("src/image/win3.png"));
            win.setBounds(175, 360, 200, 200);
            this.getContentPane().add(win);
        }
        JLabel moveLabel = new JLabel("Moves: " + moves);
        moveLabel.setBounds(10, 5, 100, 20);
        this.getContentPane().add(moveLabel);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int number = data[i][j];
                ImageIcon image = new ImageIcon(path + number +".jpg");
                JLabel label = new JLabel(image);
                label.setBounds(105 * j + 65,105 * i + 260,105,105);
                label.setBorder(new BevelBorder(1));
                label.setName(i + "," + j);
                label.addMouseListener(this);
                this.getContentPane().add(label);
                number++;
            }

        }
        JLabel background = new JLabel(new ImageIcon("src/image/slide-puzzle.png"));
        background.setBounds(20, 10, 508, 734);
        this.getContentPane().add(background);
        this.getContentPane().repaint();
    }

    private void initMenuBar() {
        JMenuBar jmb = new JMenuBar();
        JMenu game = new JMenu("Game");

        changeImage.add(animal);
        changeImage.add(sport);
        game.add(changeImage);
        game.add(restart);
        game.add(longin);
        game.add(exit);
        game.add(save);
        game.add(load);

        animal.addActionListener(this);
        sport.addActionListener(this);
        restart.addActionListener(this);
        longin.addActionListener(this);
        exit.addActionListener(this);
        save.addActionListener(this);
        load.addActionListener(this);


        JMenu aboutMe = new JMenu("About Me");

        aboutMe.add(linkedin);
        linkedin.addActionListener(this);

        jmb.add(game);
        jmb.add(aboutMe);
        jmb.setVisible(true);
        this.setJMenuBar(jmb);

    }

    private void initFrame() {
        setSize(550, 800);
        setTitle("Sliding Puzzle");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        this.setLayout(null);
        this.addKeyListener(this);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof JLabel) {
            JLabel clickLabel = (JLabel) e.getSource();
            String[] clickImg = clickLabel.getName().split(",");
            int i = Integer.parseInt(clickImg[0]);
            int j = Integer.parseInt(clickImg[1]);
            if ((i == x-1 && j == y) || (i == x + 1 && j == y) || (i == x && j == y - 1) || (i == x && j == y + 1)) {
                data[x][y] = data[i][j];
                data[i][j] = 0;
                moves++;
                initImage();

            } else {
                return;
            }
            x = i;
            y = j;

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 65) {
            this.getContentPane().removeAll();
            JLabel originalImg = new JLabel(new ImageIcon(path + "all.jpg"));
            originalImg.setBounds(65, 260, 420, 420);
            this.getContentPane().add(originalImg);

            JLabel background = new JLabel(new ImageIcon("src/image/slide-puzzle.png"));
            background.setBounds(20, 10, 508, 734);
            this.getContentPane().add(background);
            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == 65) {
            initImage();
        } else if (code == 87) {
            data = new int[][] {
                    {1,2,3,4},
                    {5,6,7,8},
                    {9,10,11,12},
                    {13,14,15,0}
            };
            initImage();

        }
    }
    public boolean isWin() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != win[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if(obj == restart) {
            moves = 0;
            initData();
            initImage();
        } else if (obj == longin) {
            this.setVisible(false);
            try {
                new SignInFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (obj == exit) {
            System.exit(1);
        } else if (obj == linkedin) {
            JDialog jd = new JDialog();
        } else if (obj == animal) {
            Random r = new Random();
            int randomImg = r.nextInt(8) + 1;
            path = "/Users/emmazhang/IdeaProjects/sliding_puzzle/src/image/animal/animal" + randomImg + "/";
            moves = 0;
            initData();
            initImage();
        } else if (obj == sport) {
            Random r = new Random();
            int randomImg = r.nextInt(10) + 1;
            path = "/Users/emmazhang/IdeaProjects/sliding_puzzle/src/image/sport/sport" + randomImg + "/";
            moves = 0;
            initData();
            initImage();
        } else if (obj == save) {
            GameInfo gi = new GameInfo(data, x, y, path, moves);
            File file = new File("/Users/emmazhang/IdeaProjects/sliding_puzzle/Save/GameInfo&" + user.getUsername());
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(gi);
                oos.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (obj == load) {
            GameInfo gi;
            File file = new File("/Users/emmazhang/IdeaProjects/sliding_puzzle/Save/GameInfo&" + user.getUsername());
            if (!file.exists()){
                showDialog("You haven't save a game yet");
                return;
            }
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                gi = (GameInfo) ois.readObject();
                ois.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            data = gi.getData();
            x = gi.getX();
            y = gi.getY();
            path = gi.getPath();
            moves = gi.getMoves();

            initImage();

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
}
