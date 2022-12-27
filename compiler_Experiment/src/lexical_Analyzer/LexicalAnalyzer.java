/**
 * ==================================================
 * Project: compiler_Experiment
 * Package: lexical_Analyzer
 * =====================================================
 * Title: LexicalAnalyzer.java
 * Created: [2022/12/26 15:27] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/12/26, created by Shuxin-Wang.
 * 2.
 */

package lexical_Analyzer;


import java.io.*;
import java.util.ArrayList;

public class LexicalAnalyzer {
    private static FileReader fileReader;
    private static FileWriter fileWriter;

    private static ArrayList<Token> tokens = new ArrayList<>();

    public static void init(String inFile, String outFile) {
        ConstructionTable.init();
        try {
            fileReader = new FileReader(inFile);
        } catch (FileNotFoundException e) {
            System.out.println(inFile + " Not Found.");
            return;
        }
        try {
            fileWriter = new FileWriter(outFile, false);
            fileWriter.write("");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            fileWriter = new FileWriter(outFile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void analysisLexical(String text) {
        System.out.println("\nLexical Analyzer:\n");
        System.out.println(text + "\n");
        //字符预处理
        String context = text + " ";
        //字符指针
        int forward = 0;
        //当前状态
        int state = 0;
        //当前目标串
        Token token = new Token();
        while (forward < context.length()) {
            int temp;
            try {
                temp = ConstructionTable.move(state, context.charAt(forward));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error:" + forward + ":\n" + context.substring(forward));
                return;
            }
            if (temp == -1) {
                switch (state) {
                    case 1:
                        token.setState(AcceptState.ID);
                        break;
                    case 2:
                        token.setState(AcceptState.INTEGER);
                        break;
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 10:
                    case 11:
                        token.setState(AcceptState.OPERATOR);
                        break;
                    case 8:
                        token.setState(AcceptState.FLOAT);
                        break;
                    case 9:
                        token.setState(AcceptState.SEPARATOR);
                        break;
                }
                System.out.println(token);
                tokens.add(token);
                try {
                    fileWriter.write(token + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                state = 0;
                token = new Token();
            } else if (temp == -2) {
                forward++;
            } else if (temp == -3) {
                System.out.println("Error:" + forward + ":\n" + context.substring(forward));
                return;
            } else {
                token.addChar(context.charAt(forward));
                state = temp;
                forward++;
            }
        }
        if (fileWriter != null) {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String readFile() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            int data;
            while ((data = fileReader.read()) != -1) {
                //回车转为空格
                if (data == 10 || data == 13) {
                    stringBuilder.append(' ');
                } else {
                    stringBuilder.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

    public static void analysisLexical() {
        analysisLexical(readFile());
    }

    public static ArrayList<Token> getTokens() {
        return tokens;
    }
}
