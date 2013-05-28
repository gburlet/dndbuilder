package beholder;

import beholder.PlayerCharacter;
import beholder.PlayerCharacter.*;
import beholder.AtWillPower;
import beholder.EncounterPower;
import beholder.DailyPower;
import beholder.UtilityPower;
import beholder.Feat;

import java.util.Vector;
import java.sql.*;

public class Cleric extends PlayerCharacter {
    public Cleric (String name) {
        super(name);

        this.powerSource = PowerSource.DIVINE;

        // armor proficiencies
        ArmorType[] ats = new ArmorType[] {
            ArmorType.CLOTH, ArmorType.LEATHER,
            ArmorType.HIDE, ArmorType.CHAINMAIL
        };
        this.addArmorProficiencies(ats);

        // weapon proficiencies
        // simple melee, simple ranged
        WeaponType[] wts = new WeaponType[] {
            WeaponType.CLUB, WeaponType.DAGGER, WeaponType.JAVELIN,
            WeaponType.MACE, WeaponType.SICKLE, WeaponType.SPEAR,
            WeaponType.GREATCLUB, WeaponType.MORNINGSTAR, WeaponType.QUARTERSTAFF,
            WeaponType.SCYTHE, WeaponType.HANDCROSSBOW, WeaponType.SLING, WeaponType.CROSSBOW
        };
        this.addWeaponProficiencies(wts);

        // defense buffs
        this.will += 2;

        // set hit points (at this point the PC should be level 1)
        this.hpPerLevel = 5;
        this.maxHealth = 12 + this.constitution + (this.level-1)*this.hpPerLevel;
        this.numHealingSurges = 7 + this.constitutionMod;

        // trained skills
        this.numSkillTrainsLeft += 4;
        this.trainSkill(Skill.RELIGION);
    }

    public Vector<AtWillPower> getAvailableAtWillPowers() {
        return this.getAvailableAtWillPowers("Cleric");
    }

    public Vector<EncounterPower> getAvailableEncounterPowers() {
        return this.getAvailableEncounterPowers("Cleric");
    }

    public Vector<DailyPower> getAvailableDailyPowers() {
        return this.getAvailableDailyPowers("Cleric");
    }

    public Vector<UtilityPower> getAvailableUtilityPowers() {
        return this.getAvailableUtilityPowers("Cleric");
    }

    public Vector<Feat> getAvailableFeats() {
        return this.getAvailableFeats("Cleric");
    }
}
