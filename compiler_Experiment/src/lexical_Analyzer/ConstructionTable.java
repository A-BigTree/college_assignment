/**
 * ==================================================
 * Project: compiler_Experiment
 * Package: lexical_Analyzer
 * =====================================================
 * Title: ConstructionTable.java
 * Created: [2022/12/26 13:59] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/12/26, created by Shuxin-Wang.
 * 2.
 */

package lexical_Analyzer;

import lexical_Analyzer.character.*;

import java.util.ArrayList;
import java.util.HashMap;

public class ConstructionTable {
    //记录输入符号
    private static final ArrayList<InputCharacter> characters = new ArrayList<>();
    //状态转换表
    private static final ArrayList<HashMap<InputCharacter, Integer>> constructionTable = new ArrayList<>();

    /*
     * 初始化输入符号表
     */
    private static void initInputCharacter() {
        characters.add(new Letter());
        characters.add(new Digit());
        characters.add(new Underline());
        characters.add(new Point());
        characters.add(new Equal());
        characters.add(new Plus());
        characters.add(new Minus());
        characters.add(new Multiple());
        characters.add(new Division());
        characters.add(new Semicolon());
        characters.add(new Space());
    }

    /*
     * 初始化状态转换表
     */
    private static void initConstructionTable() {
        //状态0:
        HashMap<InputCharacter, Integer> I0 = new HashMap<>();
        I0.put(characters.get(0), 1);
        I0.put(characters.get(1), 2);
        I0.put(characters.get(2), -3);
        I0.put(characters.get(3), -3);
        I0.put(characters.get(4), 3);
        I0.put(characters.get(5), 4);
        I0.put(characters.get(6), 5);
        I0.put(characters.get(7), 6);
        I0.put(characters.get(8), 7);
        I0.put(characters.get(9), 9);
        I0.put(characters.get(10), -2);
        constructionTable.add(I0);
        //状态1:
        HashMap<InputCharacter, Integer> I1 = new HashMap<>();
        I1.put(characters.get(0), 1);
        I1.put(characters.get(1), 1);
        I1.put(characters.get(2), 1);
        I1.put(characters.get(3), -1);
        I1.put(characters.get(4), -1);
        I1.put(characters.get(5), -1);
        I1.put(characters.get(6), -1);
        I1.put(characters.get(7), -1);
        I1.put(characters.get(8), -1);
        I1.put(characters.get(9), -1);
        I1.put(characters.get(10), -1);
        constructionTable.add(I1);
        //状态2:
        HashMap<InputCharacter, Integer> I2 = new HashMap<>();
        I2.put(characters.get(0), -1);
        I2.put(characters.get(1), 2);
        I2.put(characters.get(2), -1);
        I2.put(characters.get(3), 8);
        I2.put(characters.get(4), -1);
        I2.put(characters.get(5), -1);
        I2.put(characters.get(6), -1);
        I2.put(characters.get(7), -1);
        I2.put(characters.get(8), -1);
        I2.put(characters.get(9), -1);
        I2.put(characters.get(10), -1);
        constructionTable.add(I2);
        //状态3:
        HashMap<InputCharacter, Integer> I3 = new HashMap<>();
        for (InputCharacter character : characters) {
            I3.put(character, -1);
        }
        constructionTable.add(I3);
        //状态4:
        HashMap<InputCharacter, Integer> I4 = new HashMap<>();
        for (InputCharacter character : characters) {
            I4.put(character, -1);
        }
        constructionTable.add(I4);
        //状态5:
        HashMap<InputCharacter, Integer> I5 = new HashMap<>();
        for (InputCharacter character : characters) {
            I5.put(character, -1);
        }
        constructionTable.add(I5);
        //状态6:
        HashMap<InputCharacter, Integer> I6 = new HashMap<>();
        for (InputCharacter character : characters) {
            I6.put(character, -1);
        }
        constructionTable.add(I6);
        //状态7:
        HashMap<InputCharacter, Integer> I7 = new HashMap<>();
        for (InputCharacter character : characters) {
            I7.put(character, -1);
        }
        constructionTable.add(I7);
        //状态8:
        HashMap<InputCharacter, Integer> I8 = new HashMap<>();
        I8.put(characters.get(0), -1);
        I8.put(characters.get(1), 8);
        I8.put(characters.get(2), -1);
        I8.put(characters.get(3), -1);
        I8.put(characters.get(4), -1);
        I8.put(characters.get(5), -1);
        I8.put(characters.get(6), -1);
        I8.put(characters.get(7), -1);
        I8.put(characters.get(8), -1);
        I8.put(characters.get(9), -1);
        I8.put(characters.get(10), -1);
        constructionTable.add(I8);
        //状态9:
        HashMap<InputCharacter, Integer> I9 = new HashMap<>();
        for (InputCharacter character : characters) {
            I9.put(character, -1);
        }
        constructionTable.add(I9);
    }

    /*
     * 初始化运行动作
     */
    public static void init() {
        initInputCharacter();
        initConstructionTable();
    }

    /*
     * 根据状态与输入符号确定下一动作状态
     */
    public static int move(int state, char c) throws Exception {
        InputCharacter character = null;
        for (InputCharacter inputCharacter : characters) {
            character = inputCharacter;
            if(character.isCharacter(c)){
                break;
            }else {
                character = null;
            }
        }
        if(character == null){
            throw new Exception("No Input Character value: " + c);
        }
        return constructionTable.get(state).get(character);
    }
}
