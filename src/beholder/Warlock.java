package beholder;

import beholder.PlayerCharacter;
import beholder.PlayerCharacter.*;

import java.sql.*;

public class Warlock extends PlayerCharacter {
    public Warlock(String name) {
        super(name);
        
        this.powerSource = PowerSource.ARCANE;

        // armor proficiencies
        ArmorType[] ats = new ArmorType[] {
            ArmorType.CLOTH, ArmorType.LEATHER
        };
        this.addArmorProficiencies(ats);

        // weapon proficiencies
        // simple melee, simple ranged, military melee, military ranged
        WeaponType[] wts = new WeaponType[] {
            WeaponType.CLUB, WeaponType.DAGGER, WeaponType.JAVELIN,
            WeaponType.MACE, WeaponType.SICKLE, WeaponType.SPEAR,
            WeaponType.GREATCLUB, WeaponType.MORNINGSTAR, WeaponType.QUARTERSTAFF,
            WeaponType.SCYTHE, WeaponType.HANDCROSSBOW, WeaponType.SLING, WeaponType.CROSSBOW
        };
        this.addWeaponProficiencies(wts);

        // implement proficiencies
        ImplementType[] its = new ImplementType[] {
            ImplementType.ROD, ImplementType.WAND
        };
        this.addImplementProficiencies(its);

        // defense buffs
        this.will++;
        this.reflex++;

        // set hit points (at this point the PC should be level 1)
        this.hpPerLevel = 5;
        this.maxHealth = 12 + this.constitution + (this.level-1)*this.hpPerLevel;
        this.numHealingSurges = 6 + this.constitutionMod;

        // trained skills
        this.numSkillTrainsLeft += 4;
    }

    public ResultSet getAvailableAtWillPowers() {
        return this.getAvailableAtWillPowers("Warlock");
    }

    public ResultSet getAvailableDailyPowers() {
        return this.getAvailableDailyPowers("Warlock");
    }

    public ResultSet getAvailableEncounterPowers() {
        return this.getAvailableEncounterPowers("Warlock");
    }

    public ResultSet getAvailableUtilityPowers() {
        return this.getAvailableUtilityPowers("Warlock");
    }

}
