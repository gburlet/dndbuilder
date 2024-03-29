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

public class Rogue extends PlayerCharacter {
    public Rogue(String name) {
        super(name);

        this.powerSource = PowerSource.MARTIAL;

        // armor proficiencies
        ArmorType[] ats = new ArmorType[] {
            ArmorType.CLOTH, ArmorType.LEATHER
        };
        this.addArmorProficiencies(ats);

        // weapon proficiencies
        // simple melee, simple ranged, military melee, military ranged
        WeaponType[] wts = new WeaponType[] {
            WeaponType.DAGGER, WeaponType.HANDCROSSBOW, WeaponType.SHURIKEN, 
            WeaponType.SLING, WeaponType.SHORTSWORD
        };
        this.addWeaponProficiencies(wts);

        // defense buffs
        this.reflex += 2;

        // set hit points (at this point the PC should be level 1)
        this.hpPerLevel = 5;
        this.maxHealth = 12 + this.constitution + (this.level-1)*this.hpPerLevel;
        this.numHealingSurges = 6 + this.constitutionMod;

        // trained skills
        this.numSkillTrainsLeft += 6;
        this.trainSkill(Skill.STEALTH);
        this.trainSkill(Skill.THIEVERY);
    }

    public Vector<AtWillPower> getAvailableAtWillPowers() {
        return this.getAvailableAtWillPowers("Rogue");
    }

    public Vector<EncounterPower> getAvailableEncounterPowers() {
        return this.getAvailableEncounterPowers("Rogue");
    }

    public Vector<DailyPower> getAvailableDailyPowers() {
        return this.getAvailableDailyPowers("Rogue");
    }

    public Vector<UtilityPower> getAvailableUtilityPowers() {
        return this.getAvailableUtilityPowers("Rogue");
    }

    public Vector<Feat> getAvailableFeats() {
        return this.getAvailableFeats("Rogue");
    }
}
