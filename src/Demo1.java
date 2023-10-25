import java.io.*;
import java.util.*;

public class Demo1 {
    private ArrayList<String> kwicList = new ArrayList<String>();
    private ArrayList<String> lineTxt = new ArrayList<String>();
    private BufferedReader inputFile;
    private BufferedWriter outputFile;

    public static void main(String[] args) {
        String inputPath = args.length == 2 ? args[0] : Demo1.class.getClassLoader().getResource("input.txt").getPath();
        String outputPath = args.length == 2 ? args[1] : new File(inputPath).getParent() + "/output1.txt";

        Demo1 kwic = new Demo1();
        kwic.input(inputPath);
        kwic.shift();
        kwic.alphabetizer();
        kwic.output(outputPath);
    }


    public void input(String fileName) {
        try {
            inputFile = new BufferedReader(new FileReader(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String line;
        try {
            while ((line = inputFile.readLine()) != null) {
                lineTxt.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void output(String filename) {
        Iterator<String> it = kwicList.iterator();
        try {
            outputFile = new BufferedWriter(new FileWriter(filename));
            while (it.hasNext()) {
                outputFile.write(it.next() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputFile != null) {
                    outputFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void shift() {
        //获取每个单词，存入tokens
        Iterator<String> it = lineTxt.iterator();
        while (it.hasNext()) {
            StringTokenizer token = new StringTokenizer(it.next());
            ArrayList<String> tokens = new ArrayList<>();
            while (token.hasMoreTokens()) {
                tokens.add(token.nextToken());
            }


//            System.out.println(tokens);
            //切割各个单词，不断改变起始值和利用loop实现位移。
            for (int i = 0; i < tokens.size(); i++) {
                StringBuffer lineBuffer = new StringBuffer();
                int index = i;
                for (int f = 0; f < tokens.size(); f++) {
                    //从头继续位移
                    if (index >= tokens.size())
                        index = 0;
                    //存入StringBuffer
                    lineBuffer.append(tokens.get(index));
                    lineBuffer.append(" ");
                    index++;
                }
                String tmp = lineBuffer.toString();
                kwicList.add(tmp);
            }
        }

    }


    public void alphabetizer() {
        Collections.sort(this.kwicList, new AlphabetizerComparator());
    }

    private class AlphabetizerComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            if (o1 == null && o2 == null) {
                throw new NullPointerException();
            }
            int compareValue = 0;
            char o1c = o1.toLowerCase().charAt(0); //忽略大小写
            char o2c = o2.toLowerCase().charAt(0); //忽略大小写
            compareValue = o1c - o2c;
            return compareValue;

        }

    }
}
