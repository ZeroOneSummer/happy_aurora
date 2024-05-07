package com.aurora.happy.swing;

import cn.hutool.core.date.ChineseDate;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import javafx.util.Pair;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Element;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Timer;
import java.util.*;

/**
 * 迷你工具箱
 */
public class ToolBox {
    private static final String TO_XML_ESCAPE = "toXmlEscape",
                                TO_JSON_ESCAPE = "toJsonEscape",
                                TO_XML_PRETTY = "toXmlPretty",
                                TO_JSON_PRETTY = "toJsonPretty";

    public static void main(String[] args) {
        //事件调度线程，保证UI操作线程安全
        SwingUtilities.invokeLater(ToolBox::doPaint);
    }

    /**
     * 主画板
     */
    private static void doPaint(){
        //主窗口
        JFrame frame = new JFrame("mini工具箱");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //窗口关闭，程序退出
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);  //弹窗居中
        frame.setResizable(true);  //是否可拉伸

        //主画板
        JPanel mainPanel = new JPanel(new BorderLayout());  //主布局管理器

        //文本域（由于panel不能共享）
        List<Pair<JTextArea, JTextArea>> areaList = new ArrayList<>();
        JTextArea left1 = new JTextArea(), right1 = new JTextArea();
        JTextArea left2 = new JTextArea(), right2 = new JTextArea();
        JTextArea left3 = new JTextArea(), right3 = new JTextArea();
        JTextArea left4 = new JTextArea(), right4 = new JTextArea();
        areaList.add(new Pair<>(left1, right1));
        areaList.add(new Pair<>(left2, right2));
        areaList.add(new Pair<>(left3, right3));
        areaList.add(new Pair<>(left4, right4));

        //middle画板 - tab
        JTabbedPane tabPanel = new JTabbedPane();
        tabPanel.addTab("xml转义", getTabPanel(TO_XML_ESCAPE, left1, right1));
        tabPanel.addTab("xml格式化", getTabPanel(TO_XML_PRETTY, left2, right2));
        tabPanel.addTab("json转义", getTabPanel(TO_JSON_ESCAPE, left3, right3));
        tabPanel.addTab("json格式化", getTabPanel(TO_JSON_PRETTY, left4, right4));
        mainPanel.add(tabPanel, BorderLayout.CENTER);

        //按钮
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));  //右对齐，按钮间5像素间隔
        AbstractButton prettyButton = getButton("美化", true);
        AbstractButton btn1 = getButton("转换", false);
        AbstractButton btn2 = getButton("复制", false);
        AbstractButton btn3 = getButton("截图", false);
        AbstractButton cleanButton = getButton("清空", false);
        btn1.setToolTipText("CTRL+ENTER");  //快捷键提示
        prettyButton.setSelected(true);  //默认美化
        topPanel.add(prettyButton);
        topPanel.add(btn1);
        topPanel.add(btn2);
        topPanel.add(btn3);
        topPanel.add(cleanButton);

        //bottom
        JPanel buttomPanel = new JPanel(new BorderLayout());
        JLabel errLable = new JLabel("");
        errLable.setForeground(Color.RED);
        buttomPanel.add(errLable, BorderLayout.WEST);
        buttomPanel.setPreferredSize(new Dimension(buttomPanel.getPreferredSize().width, 20));  //只设置高度
        addClock(buttomPanel);  //time

        //整合
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(buttomPanel, BorderLayout.SOUTH);
        frame.add(mainPanel);
        frame.setVisible(true);     //可见

        //清空
        cleanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pair<JTextArea, JTextArea> pair = areaList.get(tabPanel.getSelectedIndex());  //当前tab索引
                pair.getKey().setText(null);  //左文本域
                pair.getValue().setText(null); //右文本域
            }
        });
        //转换
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doSubmit(errLable, prettyButton, tabPanel, areaList);
            }
        });
        //复制
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pair<JTextArea, JTextArea> pair = areaList.get(tabPanel.getSelectedIndex());
                JTextArea rightArea = pair.getValue();
                //全选
                rightArea.selectAll();
                rightArea.requestFocusInWindow(); //聚焦
                //获取文本
                String rightText = rightArea.getText();
                StringSelection selection = new StringSelection(rightText);
                //获取系统剪切板
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, null);
                //信息弹窗
                JOptionPane.showMessageDialog(null, "已复制", "通知", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        //截图
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takeScreen();
            }
        });

        //快捷键
        bindKeyBoard(areaList, tabPanel, prettyButton, errLable);  //初始tab绑定
        tabPanel.addChangeListener(new ChangeListener() {
            //切换重新加载绑定
            @Override
            public void stateChanged(ChangeEvent e) {
                bindKeyBoard(areaList, tabPanel, prettyButton, errLable);
            }
        });

        //整合
        frame.setContentPane(mainPanel);  //设置为容器
        frame.setVisible(true); //是否可见
    }

    /**
     * 快捷键绑定
     * @param areaList
     * @param tabPanel
     * @param prettyButton
     * @param errLable
     */
    private static void bindKeyBoard(List<Pair<JTextArea, JTextArea>> areaList, JTabbedPane tabPanel, AbstractButton prettyButton, JLabel errLable) {
        Pair<JTextArea, JTextArea> pair = areaList.get(tabPanel.getSelectedIndex());
        JTextArea leftArea = pair.getKey();
        //ctrl+enter 转换
        leftArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.CTRL_DOWN_MASK), "submit");
        leftArea.getActionMap().put("submit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doSubmit(errLable, prettyButton, tabPanel, areaList);
            }
        });
        //撤销管理器
        UndoManager undoManager = new UndoManager();
        leftArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
            }
        });
        //ctrl+z 撤销
        leftArea.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "undo");
        leftArea.getActionMap().put("undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canUndo()){
                    undoManager.undo();
                }
            }
        });
        //ctrl+shift+z 反撤销
        leftArea.getInputMap().put(KeyStroke.getKeyStroke("ctrl shift Z"), "redo");
        leftArea.getActionMap().put("redo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canRedo()){
                    undoManager.redo();
                }
            }
        });
        //ctrl+d 复制当前行
        leftArea.getInputMap().put(KeyStroke.getKeyStroke("ctrl D"), "copyLine");
        leftArea.getActionMap().put("copyLine", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int position = leftArea.getCaretPosition();                                         //光标位置
                    Element rootElement = leftArea.getDocument().getDefaultRootElement();
                    int lineNum = rootElement.getElementIndex(position);                                //光标所在行
                    Element lineEle = rootElement.getElement(lineNum);                                  //行元素
                    int start = lineEle.getStartOffset();                                               //行头
                    int end = lineEle.getEndOffset();                                                   //行尾
                    String lineText = leftArea.getDocument().getText(start, end - start);       //行内容
                    int nextLineStart = (lineNum <rootElement.getElementCount() - 1)
                            ? rootElement.getElement(lineNum + 1).getStartOffset()
                            : leftArea.getDocument().getLength() + 1;                   //下一行行头
                    leftArea.getDocument().insertString(nextLineStart, lineText, null);             //在下一行追加内容
                } catch (Exception ex) {
                    String errMessage = "错误提示: " + ex.getMessage();
                    errLable.setText(errMessage);
                    errLable.setToolTipText(errMessage);   //鼠标悬浮提示
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * 转换提交
     * @param errLable
     * @param prettyButton
     * @param tabPanel
     * @param areaList
     */
    private static void doSubmit(JLabel errLable, AbstractButton prettyButton, JTabbedPane tabPanel, List<Pair<JTextArea, JTextArea>> areaList) {
        try {
            errLable.setText(null); //清空错误栏
            boolean isPretty = prettyButton.isSelected();
            String tabName = tabPanel.getSelectedComponent().getName();
            Pair<JTextArea, JTextArea> pair = areaList.get(tabPanel.getSelectedIndex());
            JTextArea leftArea = pair.getKey();
            JTextArea rightArea = pair.getValue();
            String oldText = leftArea.getText();
            String newText = "";
            switch (tabName){
                case TO_XML_ESCAPE:
                    oldText = toXmlPretty(oldText, isPretty); //是否一行
                    newText = toXmlEscape(oldText);
                    break;
                case TO_XML_PRETTY:
                    newText = toXmlPretty(oldText, isPretty);
                    break;
                case TO_JSON_ESCAPE:
                    oldText = toJsonPretty(oldText, isPretty);
                    newText = toJsonEscape(oldText);
                    break;
                case TO_JSON_PRETTY:
                    newText = toJsonPretty(oldText, isPretty);
                    break;
                default:
                    throw new RuntimeException("未知操作类型！");
            }
            rightArea.setText(newText);
        } catch (Exception ex) {
            String errMessage = "错误提示: " + ex.getMessage();
            errLable.setText(errMessage);
            errLable.setToolTipText(errMessage);   //鼠标悬浮提示
            ex.printStackTrace();
        }
    }

    /**
     * 公共tab画板
     * @param panelName
     * @param leftArea
     * @param rightArea
     * @return
     */
    private static JPanel getTabPanel(String panelName, JTextArea leftArea, JTextArea rightArea){
        JPanel middePanl = new JPanel(new GridLayout(1, 2)); //网格布局管理器
        //左边
        leftArea.setLineWrap(true); // 自动换行
        JScrollPane leftScrollPane = new JScrollPane(leftArea);
        middePanl.add(leftScrollPane);
        //右边
        rightArea.setLineWrap(true);
        rightArea.setEditable(false); // 设置为只读
        JScrollPane rightScrollPane = new JScrollPane(rightArea);
        middePanl.add(rightScrollPane);
        middePanl.setName(panelName);
        return middePanl;
    }

    /**
     * 公共按钮
     * @param buttonName
     * @param isRadio
     * @return
     */
    private static AbstractButton getButton(String buttonName, boolean isRadio){
        AbstractButton button = isRadio ? new JRadioButton(buttonName) : new JButton(buttonName);
        Font font = new Font("宋体", Font.BOLD + Font.ITALIC, 12);  //加粗 + 斜体
        button.setFont(font);
        button.setFocusPainted(false);   //防止文字周围边框
        return button;
    }

    /**
     * xml转义
     * @param text
     * @return
     */
    private static String toXmlEscape(String text){
        return text.replace("\"", "\\\"");
    }

    /**
     * xml格式化
     * @param text
     * @param isPretty
     * @return
     * @throws Exception
     */
    private static String toXmlPretty(String text, boolean isPretty) throws Exception{
        String docStr = "";
        text = text.replace("\\\"", "\""); //转义还原
        if (isPretty){
            Document document = DocumentHelper.parseText(text);
            document.normalize();
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding(StandardCharsets.UTF_8.name());
            format.setNewLineAfterDeclaration(false);
            format.setIndent(true);  //缩进
            format.setExpandEmptyElements(true); //保留空标签
            StringWriter stringWriter = new StringWriter();
            XMLWriter xmlWriter = new XMLWriter(stringWriter, format);
            xmlWriter.write(document);
            docStr = stringWriter.toString();
            xmlWriter.close();
            stringWriter.close();
        }else{
            //替换所有特殊字符：空格、制表符、换行符等
            docStr = text.trim().replaceAll(">\\s+<", "><");
        }
        return docStr;
    }

    /**
     * json转义
     * @param text
     * @return
     */
    private static String toJsonEscape(String text){
        return text.replace("\"", "\\\"");
    }

    /**
     * json格式化
     * @param text
     * @param isPretty
     * @return
     */
    private static String toJsonPretty(String text, boolean isPretty){
        if (Objects.nonNull(text) && text.trim().length() > 0){
            text = text.replace("\\\"", "\""); //转义还原
            JSONObject jsonObject = JSON.parseObject(text);
            return JSON.toJSONString(jsonObject, isPretty);
        }
        return "";
    }

    /**
     * 截图
     * @return
     */
    private static void takeScreen(){
        try {
            Robot robot = new Robot();
            Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage image = robot.createScreenCapture(rectangle);
            String number = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
            File file = new File(number + ".png");
            ImageIO.write(image, "png", file);
            JOptionPane.showMessageDialog(null, "已保存至：" + file.getAbsolutePath(), "截图完成", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "截图失败!", "错误", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * 时钟
     * @param buttomPanel
     */
    private static void addClock(JPanel buttomPanel){
        buttomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        JLabel timeLabel = new JLabel("", SwingConstants.CENTER);
        timeLabel.setFont(new Font("宋体", Font.ITALIC, 12));
        buttomPanel.add(timeLabel, BorderLayout.EAST);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss E")); //E表示周
                String chnMonth = new ChineseDate(new Date()).toString().split(" ")[1]; //获取农历月份
                timeLabel.setText(format.concat(" ").concat(chnMonth));
            }
        }, 0, 1_000);
    }
}

