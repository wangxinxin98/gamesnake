package com.wang.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    int length;     // 蛇的长度
    int[] snakeX = new int[600];    // 蛇的X坐标
    int[] snakeY = new int[500];    // 蛇的Y坐标
    String fx;      // 头部的方向
    boolean isStart = false;        // 判断游戏是否开始
    Timer timer = new Timer(100,this);      // 定时器
    int foodx;      // 食物X坐标
    int foody;      // 食物Y坐标
    Random random = new Random();
    boolean isFail = false;     // 判断游戏结束
    int score;      // 积分

    // 构造器
    public GamePanel(){
        init();
        // 获取键盘的监听事件
        this.setFocusable(true);
        this.addKeyListener(this);
        timer.start();      // 让时间动起来
    }

    // 初始化
    public void init(){
        length = 3;
        snakeX[0] = 100;    snakeY[0] = 100;    // 头部坐标
        snakeX[1] = 75;     snakeY[1] = 100;    // 第一个身体坐标
        snakeX[2] = 50;     snakeY[2] = 100;    // 第二个身体坐标
        fx = "R";   // 头部初始向右
        foodx = 25 + 25 * random.nextInt(34);   // 食物坐标
        foody = 75 + 25 * random.nextInt(24);
        score = 0;  // 积分
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);    // 清屏
        this.setBackground(Color.GRAY);    // 设置背景颜色
        // 绘制头部的广告栏
        // Data.header.paintIcon(this,g,25,11);
        g.fillRect(25,10,850,55);
        // 绘制游戏区域
        g.fillRect(25,75,850,600);
        // 画一条静态的小蛇
        if (fx.equals("R")){
            Data.right.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if (fx.equals("L")){
            Data.left.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if (fx.equals("U")){
            Data.up.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if (fx.equals("D")){
            Data.down.paintIcon(this,g,snakeX[0],snakeY[0]);
        }
        for (int i = 1; i < length; i++) {
            Data.body.paintIcon(this,g,snakeX[i],snakeY[i]);
        }

        // 画积分
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial",Font.BOLD,18));     // 设置字体
        g.drawString("length:  " + length,420,35);
        g.drawString("score:   " + score,420,50);

        // 画食物
        Data.food.paintIcon(this,g,foodx,foody);

        // 游戏提示：是否开始
        if (!isStart){
            // 画出游戏提示
            g.setColor(Color.YELLOW);       // 设置画笔颜色
            g.setFont(new Font("Times New Roman",Font.BOLD,40));     // 设置字体
            g.drawString("Please enter SPACE!",300,300);
        }

        // 游戏结束提醒
        if (isFail){
            g.setColor(Color.RED);       // 设置画笔颜色
            g.setFont(new Font("Times New Roman",Font.BOLD,40));     // 设置字体
            g.drawString("Game Over!",350,300);
        }
    }

    // 接收键盘的输入：监听
    @Override
    public void keyPressed(KeyEvent e) {
        // 键盘按键按下，未释放
        // 获取当前按下的按键
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_SPACE){      // 如果按下空格键
            if (isFail){    // 游戏结束，重新开始
                isFail = false;
                init();     // 重新开始
            }else{          // 暂停游戏
                isStart = !isStart;
            }
            repaint();      // 重绘
        }

        // 通过键盘控制走向
        if (keyCode == KeyEvent.VK_RIGHT){
            fx = "R";
        }else if (keyCode == KeyEvent.VK_LEFT){
            fx = "L";
        }else if (keyCode == KeyEvent.VK_UP){
            fx = "U";
        }else if (keyCode == KeyEvent.VK_DOWN){
            fx = "D";
        }
    }

    // 定时器，监听时间，帧：执行定时操作
    @Override
    public void actionPerformed(ActionEvent e) {
        // 如果游戏处于开始状态，并且游戏没有结束
        if (isStart && !isFail){
            // 右移
            for (int i = length-1; i > 0; i--){     // 身体移动
                snakeX[i] = snakeX[i-1];
                snakeY[i] = snakeY[i-1];
            }
            // 移动移动时需要判断方向
            if (fx.equals("R")){
                snakeX[0] = snakeX[0] + 25;             // 头部移动
                if (snakeX[0]>850){ snakeX[0] = 25; }   // 边界判断
            }else if (fx.equals("L")){
                snakeX[0] = snakeX[0] - 25;
                if (snakeX[0]<25){ snakeX[0] = 850; }
            }else if (fx.equals("U")){
                snakeY[0] = snakeY[0] - 25;
                if (snakeY[0]<75){ snakeY[0] = 650; }
            }else if (fx.equals("D")){
                snakeY[0] = snakeY[0] + 25;
                if (snakeY[0]>650){ snakeY[0] = 75; }
            }

            // 如果头部与食物坐标重合
            if (snakeX[0]==foodx && snakeY[0]==foody){
                length++;
                score += 10;

                // 重新生成食物
                foodx = 25 + 25 * random.nextInt(34);
                foody = 75 + 25 * random.nextInt(24);
            }

            // 结束判断
            for (int i = 1; i < length; i++) {
                if (snakeX[0]==snakeX[i] && snakeY[0]==snakeY[i]){
                    isFail = true;
                }
            }

            repaint();      // 重绘
        }
        timer.start();      // 让时间动起来
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // 键盘按键按下，弹起
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // 键盘按键释放
    }
}
