import lexical_Analyzer.ConstructionTable;

public class Main {
    public static void main(String[] args) {
        ConstructionTable.init();
        System.out.println(ConstructionTable.move(0, '1'));
    }
}