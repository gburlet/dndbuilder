package beholder;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import beholder.PlayerCharacter;
import beholder.PlayerCharacter.*;

@RunWith(JUnit4.class)
public class PlayerCharacterTest {

    @Test
    public void testConstructors() {
        // instantiate blank character
        PlayerCharacter pc = new PlayerCharacter();
        pc.setName("Burt Ward");
        pc.setRace(Race.HALFLING);
        pc.setClass(PCClass.ROGUE);

        pc.setStrength(16);
        pc.setConstitution(10);
        pc.setDexterity(15);
        pc.setIntelligence(11);
        pc.setWisdom(10);
        pc.setCharisma(13);
        
        // pc.rollAbilityScores();

        System.out.println(pc);
    }
}
