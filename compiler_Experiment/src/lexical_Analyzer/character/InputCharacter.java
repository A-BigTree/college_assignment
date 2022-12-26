/**
 * ==================================================
 * Project: compiler_Experiment
 * Package: lexical_Analyzer.character
 * =====================================================
 * Title: InputCharacter.java
 * Created: [2022/12/26 13:22] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/12/26, created by Shuxin-Wang.
 * 2.
 */

package lexical_Analyzer.character;

public abstract class InputCharacter {
    private String characterName;

    public InputCharacter(String characterName){
        this.characterName = characterName;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o==null ||getClass() != o.getClass()){
            return false;
        }
        InputCharacter character = (InputCharacter) o;
        return characterName.equals(character.characterName);
    }

    @Override
    public int hashCode(){
        return characterName.hashCode();
    }

    public abstract boolean isCharacter(char c);
}
