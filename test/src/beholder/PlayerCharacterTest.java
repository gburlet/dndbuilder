package beholder;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import beholder.PlayerCharacter;
import beholder.PlayerCharacter.*;
import beholder.Cleric;
import beholder.Fighter;
import beholder.Paladin;
import beholder.Ranger;
import beholder.Rogue;
import beholder.Warlock;
import beholder.Warlord;
import beholder.Wizard;

@RunWith(JUnit4.class)
public class PlayerCharacterTest {

    @Test
    public void testCleric() {
        Cleric c = new Cleric("My Cleric");
    }

    @Test
    public void testFighter() {
        Fighter f = new Fighter("My Fighter");
    }

    @Test
    public void testPaladin() {
        Paladin p = new Paladin("My Paladin");
    }

    @Test
    public void testRanger() {
        Ranger r = new Ranger("My Ranger");
    }

    @Test
    public void testRogue() {
        Rogue r = new Rogue("My Rogue");

        r.setRace(Race.HALFLING);

        r.setStrength(14);
        r.setConstitution(11);
        r.setDexterity(18);
        r.setIntelligence(10);
        r.setWisdom(10);
        r.setCharisma(8);

        r.trainSkill(Skill.ACROBATICS);
        r.trainSkill(Skill.ATHLETICS);
        r.trainSkill(Skill.DUNGEONEERING);
        r.trainSkill(Skill.PERCEPTION);

        System.out.println(r);
    }

    @Test
    public void testWarlock() {
        Warlock w = new Warlock("My Warlock");
    }

    @Test
    public void testWarlord() {
        Warlord w = new Warlord("My Warlord");
    }

    @Test
    public void testWizard() {
        Wizard w = new Wizard("My Wizard");
    }
}
