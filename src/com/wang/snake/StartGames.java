package com.wang.snake;

import javax.swing.*;

public class StartGames {
    public static void main(String[] args) {
        // 1.绘制一个静态窗口JFrame
        JFrame frame = new JFrame("Game - Snake");
        // 设置界面大小
        frame.setBounds(10,10,900,720);
        // 设置窗口大小不可改变
        frame.setResizable(false);
        // 设置关闭事件
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 2.面板JPanel，添加到JFrame
        frame.add(new GamePanel());

        // 设置窗口可见
        frame.setVisible(true);
    }
}
