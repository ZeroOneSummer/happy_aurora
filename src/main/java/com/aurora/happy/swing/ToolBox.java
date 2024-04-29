package com.aurora.happy.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 工具箱
 * 1.xml转义
 * 2.xml格式化
 * 3.json格式化
 */
public class ToolBox {

    public static void main(String[] args) {

        //事件调度线程，保证UI操作线程安全
        SwingUtilities.invokeLater(() -> {

            //主窗口
            JFrame frame = new JFrame("迷你工具箱");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //窗口关闭，程序退出
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);  //弹窗居中
            frame.setResizable(true);  //是否可拉伸

            //主画板  mainPanel - tabPane - centerPanel - middePanl
            JPanel mainPanel = new JPanel(new BorderLayout());  //主布局管理器
            //center画板
            JPanel centerPanel = new JPanel(new BorderLayout());

            //tab
            JTabbedPane tabPane = new JTabbedPane();
            tabPane.addTab("xml转义", centerPanel);
            tabPane.addTab("xml格式化", new JPanel());
            tabPane.addTab("json格式化", new JPanel());
            mainPanel.add(tabPane, BorderLayout.CENTER);

            JPanel middePanl = new JPanel(new GridLayout(1, 2)); //网格布局管理器
            //左边
            JTextArea leftTextArea = new JTextArea();
            leftTextArea.setLineWrap(true); // 自动换行
            JScrollPane leftScrollPane = new JScrollPane(leftTextArea);
            //右边
            JTextArea rightTextArea = new JTextArea();
            leftTextArea.setLineWrap(true); // 自动换行
            rightTextArea.setEditable(false); // 设置为只读
            JScrollPane rightScrollPane = new JScrollPane(rightTextArea);

            middePanl.add(leftScrollPane);
            middePanl.add(rightScrollPane);
            centerPanel.add(middePanl, BorderLayout.CENTER);

            //top画板
            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 10));  // 右对齐，按钮间10像素间隔
            //button
            Font font = new Font("宋体", Font.BOLD + Font.ITALIC, 12);  //加粗 + 斜体
            JButton submitButton = new JButton("转换");
            submitButton.setFocusPainted(false); // 防止绘制焦点边框
            submitButton.setFont(font);
            topPanel.add(submitButton);  // 按钮在右侧
            JButton clearButton = new JButton("清空");
            clearButton.setFocusPainted(false);
            clearButton.setFont(font);
            topPanel.add(clearButton);
            mainPanel.add(topPanel, BorderLayout.NORTH);


            //bottom画板-错误回显
            JLabel errLable = new JLabel("");
            errLable.setForeground(Color.RED);
            JPanel bottomPane = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
            Dimension preferredSize = new Dimension(bottomPane.getPreferredSize().width, 25);
            bottomPane.setPreferredSize(preferredSize);  //只设置高度
            bottomPane.add(errLable);
            mainPanel.add(bottomPane, BorderLayout.SOUTH);

            //事件
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String oldText = leftTextArea.getText();
                        //todo 转换内容
                        String newText = "---------------\n" + oldText;
                        rightTextArea.setText(newText);
                        errLable.setText("错误原因：404");
                    } catch (Exception ex) {
                        errLable.setText("错误原因：" + ex.getMessage());
                    }
                }
            });
            clearButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    leftTextArea.setText(null);
                    rightTextArea.setText(null);
                }
            });

            //整合
            frame.setContentPane(mainPanel);  //设置为容器
            frame.setVisible(true); //是否可见
        });
    }
}
