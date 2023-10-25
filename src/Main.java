import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            setDefaultSize(24);
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        // 创建主窗口
        JFrame mainFrame = new JFrame("经典软件体系结构教学软件");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(600, 400);
        mainFrame.setLayout(new FlowLayout());

        // 创建四个按钮
        JButton programButton = new JButton("主程序-子程序");
        JButton objectButton = new JButton("面向对象");
        JButton eventButton = new JButton("事件系统");
        JButton filterButton = new JButton("管道-过滤器");

        // 添加按钮点击事件监听器
        programButton.addActionListener(e -> {
            Demo1 demo = new Demo1();
            HandleExperiment handler = new HandleExperiment("主程序-子程序");
            JFrame frame = openFrameAndReturn(handler);
            JButton btn1 = new JButton(" 调用 kwic.input(inputPath) ");
            btn1.addActionListener(e1 -> demo.input(handler.inputPath));
            JButton btn2 = new JButton(" 调用 kwic.shift() ");
            btn2.addActionListener(e2 -> demo.shift());
            JButton btn3 = new JButton("调用 kwic.alphabetizer() ");
            btn3.addActionListener(e3 -> demo.alphabetizer());
            JButton btn4 = new JButton("调用 kwic.output(outputPath);");
            btn4.addActionListener(e4 -> {
                demo.output(handler.outputPath);
                displayFileContent(handler.outputPath);
            });
            frame.add(btn1);
            frame.add(btn2);
            frame.add(btn3);
            frame.add(btn4);
        });

        objectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Demo2 demo = new Demo2();
                HandleExperiment handler = new HandleExperiment("面向对象");
                JFrame frame = openFrameAndReturn(handler);
                JButton btn1 = new JButton("<html> 调用 <br>Input input = new Input();<br>input.input(inputPath); </html>");
                AtomicReference<Demo2.Input> input = null;
                btn1.addActionListener(e1 -> {
                    input.set(new Demo2.Input());
                    input.get().input(handler.inputPath);
                } );
                JButton btn2 = new JButton("<html> 调用 <br>Shift shift = new Shift(input.getLineTxt());<br>shift.shift(); </html>");
                AtomicReference<Demo2.Shift> shift = null;
                btn2.addActionListener(e2 -> {
                    shift.set(new Demo2.Shift(input.get().getLineTxt()));
                    shift.get().shift();
                });
                JButton btn3 = new JButton("<html> 调用 <br>Alphabetizer alphabetizer = new Alphabetizer(shift.getKwicList());<br>alphabetizer.sort(); </html>");
                AtomicReference<Demo2.Alphabetizer> alphabetizer = null;
                btn3.addActionListener(e3 ->{
                    alphabetizer.set(new Demo2.Alphabetizer(shift.get().getKwicList()));
                    alphabetizer.get().sort();
                });
                JButton btn4 = new JButton("<html> 调用 <br>Output output = new Output(alphabetizer.getKwicList());<br>output.output(outputPath); </html>");
                AtomicReference<Demo2.Output> output = null;
                btn4.addActionListener(e4 -> {
                    output.set(new Demo2.Output(alphabetizer.get().getKwicList()));
                    output.get().output(handler.outputPath);
                    displayFileContent(handler.outputPath);
                });
                frame.add(btn1);
                frame.add(btn2);
                frame.add(btn3);
                frame.add(btn4);
            }
        });

        eventButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HandleExperiment handler = new HandleExperiment("事件系统");
                JFrame frame = openFrameAndReturn(handler);

                JButton btn1 = new JButton("<html> 调用 <br>KWICSubject kwicSubject = new KWICSubject();<br>" +
                        "Input input = new Input(inputPath);<br>" +
                        "Shift shift = new Shift(input.getLineTxt());<br>" +
                        "Alphabetizer alphabetizer = new Alphabetizer(shift.getKwicList());<br>" +
                        "Output output = new Output(alphabetizer.getKwicList(), outputPath);</html>");
                AtomicReference<Demo3.KWICSubject> kwicSubject = null;
                btn1.addActionListener(e1 -> {
                    kwicSubject.set(new Demo3.KWICSubject());
                    Demo3.Input input = new Demo3.Input(handler.inputPath);
                    Demo3.Shift shift = new Demo3.Shift(input.getLineTxt());
                    Demo3.Alphabetizer alphabetizer = new Demo3.Alphabetizer(shift.getKwicList());
                    Demo3.Output output = new Demo3.Output(alphabetizer.getKwicList(), handler.outputPath);

                    kwicSubject.get().addObserver(shift);
                    kwicSubject.get().addObserver(alphabetizer);
                    kwicSubject.get().addObserver(output);
                } );

                JButton btn2 = new JButton("<html> 调用 <br>kwicSubject.addObserver(input);<br>" +
                        "kwicSubject.addObserver(shift);<br>" +
                        "kwicSubject.addObserver(alphabetizer);<br>" +
                        "kwicSubject.addObserver(output); </html>");
                btn2.addActionListener(e2 -> {
                });

                JButton btn3 = new JButton("<html> 调用 <br>kwicSubject.startKWIC(); </html> ");
                btn3.addActionListener(e3 ->{
                    kwicSubject.get().startKWIC();
                    displayFileContent(handler.outputPath);
                });

                frame.add(btn1);
                frame.add(btn2);
                frame.add(btn3);
            }
        });

        filterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HandleExperiment handler = new HandleExperiment("管道-过滤器");
                JFrame frame = openFrameAndReturn(handler);

                JButton btn1 = new JButton("<html> 调用 <br>" +
                        "File inFile = new File(inputPath);<br>" +
                        "File outFile = new File(outputPath);<br>" +
                        "Pipe pipe1 = new Pipe();<br>" +
                        "Pipe pipe2 = new Pipe();<br>" +
                        "Pipe pipe3 = new Pipe();</html>");
                btn1.addActionListener(e1 -> {
                } );

                JButton btn2 = new JButton("<html> 调用 <br>" +
                        "Input input = new Input(inFile, pipe1);<br>" +
                        "Shift shift = new Shift(pipe1, pipe2);<br>" +
                        "Alphabetizer alphabetizer = new Alphabetizer(pipe2, pipe3);<br>" +
                        "Output output = new Output(outFile, pipe3);</html>");
                btn2.addActionListener(e2 -> {
                });

                JButton btn3 = new JButton("<html> 调用 <br>" +
                        "input.transform();<br>" +
                        "shift.transform();<br>" +
                        "alphabetizer.transform();<br>" +
                        "output.transform();</html> ");
                btn3.addActionListener(e3 ->{
                    File inFile = new File(handler.inputPath);
                    File outFile = new File(handler.outputPath);
                    Demo4.Pipe pipe1 = new Demo4.Pipe();
                    Demo4.Pipe pipe2 = new Demo4.Pipe();
                    Demo4.Pipe pipe3 = new Demo4.Pipe();
                    Demo4.Input input = new Demo4.Input(inFile, pipe1);
                    Demo4.Shift shift = new Demo4.Shift(pipe1, pipe2);
                    Demo4.Alphabetizer alphabetizer = new Demo4.Alphabetizer(pipe2, pipe3);
                    Demo4.Output output = new Demo4.Output(outFile, pipe3);
                    try {
                        input.transform();
                        shift.transform();
                        alphabetizer.transform();
                        output.transform();
                        displayFileContent(handler.outputPath);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                frame.add(btn1);
                frame.add(btn2);
                frame.add(btn3);
            }
        });

        // 将按钮添加到主窗口
        mainFrame.add(programButton);
        mainFrame.add(objectButton);
        mainFrame.add(eventButton);
        mainFrame.add(filterButton);

        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);
    }


    static JFrame openFrameAndReturn(HandleExperiment handler) {
        JFrame mainFrame = new JFrame(handler.title);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);


        // 创建文件选择按钮
        JButton chooseExistingFileButton = new JButton("选择已存在的文件");
        JButton chooseNewFileButton = new JButton("选择新建文件路径");

        // 创建标签用于显示文件路径
        JLabel existingFilePathLabel = new JLabel("已选择的文件路径: ");
        JLabel newFilePathLabel = new JLabel("新文件将保存到路径: ");

        // 创建文件选择器
        JFileChooser fileChooser = new JFileChooser();

        chooseExistingFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();
                    existingFilePathLabel.setText("已选择的文件路径: " + filePath);
                    handler.inputPath = filePath;
                }
            }
        });

        chooseNewFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnValue = fileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();
                    newFilePathLabel.setText("新文件将保存到路径: " + filePath);
                    handler.outputPath = filePath;
                }
            }
        });


        JLabel titleLabel = new JLabel(handler.title);
        titleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 60));
        JLabel subtitleLabel = new JLabel("请按顺序点击下面按钮");
        subtitleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(titleLabel);
        verticalBox.add(subtitleLabel);
        verticalBox.add(chooseExistingFileButton);
        verticalBox.add(existingFilePathLabel);
        verticalBox.add(chooseNewFileButton);
        verticalBox.add(newFilePathLabel);
        verticalBox.setBorder(new EmptyBorder(5, 5, 50, 5));
        subtitleLabel.setBorder(new EmptyBorder(5, 5, 20, 5));
        mainFrame.add(verticalBox);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        handler.setFrame(mainFrame);
        return mainFrame;
    }


    private static void displayFileContent(String filePath) {
        JFrame fileContentFrame = new JFrame("文件内容");
        fileContentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fileContentFrame.setSize(600, 400);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false); // 使文本区域只读

        JScrollPane scrollPane = new JScrollPane(textArea);
        fileContentFrame.add(scrollPane);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n"); // 添加文件内容到文本区域
            }
            reader.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "无法读取文件内容", "错误", JOptionPane.ERROR_MESSAGE);
        }

        fileContentFrame.setVisible(true);
    }


    public static void setDefaultSize(int size) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }


        Set keySet = UIManager.getLookAndFeelDefaults().keySet();
        Object[] keys = keySet.toArray(new Object[keySet.size()]);
        for (Object key : keys) {
            if (key != null && key.toString().toLowerCase().contains("font")) {
                System.out.println(key);
                Font font = UIManager.getDefaults().getFont(key);
                if (font != null) {
                    font = font.deriveFont((float) size);
                    UIManager.put(key, font);
                }
            }
        }
    }
}


class HandleExperiment {
    String title;
    String inputPath;
    String outputPath;
    private Frame frame;


    HandleExperiment(String title) {
        this.title = title;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }
}