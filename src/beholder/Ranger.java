package beholder;

import beholder.PlayerCharacter;
import beholder.PlayerCharacter.*;

import java.sql.*;

public class Ranger extends PlayerCharacter {
    public Ranger(String name) {
        super(name);

        this.powerSource = PowerSource.MARTIAL;

        // armor proficiencies
        ArmorType[] ats = new ArmorType[] {
            ArmorType.CLOTH, ArmorType.LEATHER, ArmorType.HIDE,
        };
        this.addArmorProficiencies(ats);

        // weapon proficiencies
        // simple melee, simple ranged, military melee, military ranged
        WeaponType[] wts = new WeaponType[] {
            WeaponType.CLUB, WeaponType.DAGGER, WeaponType.JAVELIN,
            WeaponType.MACE, WeaponType.SICKLE, WeaponType.SPEAR,
            WeaponType.GREATCLUB, WeaponType.MORNINGSTAR, WeaponType.QUARTERSTAFF,WeaponType.SCYTHE, 
            WeaponType.HANDCROSSBOW, WeaponType.SLING, WeaponType.CROSSBOW,
            WeaponType.BATTLEAXE, WeaponType.FLAIL, WeaponType.HANDAXE, WeaponType.LONGSWORD,
            WeaponType.SCIMITAR, WeaponType.SHORTSWORD, WeaponType.THROWINGHAMMER, WeaponType.WARHAMMER,
            WeaponType.WARPICK, WeaponType.FALCHION, WeaponType.GLAIVE, WeaponType.GREATAXE, 
            WeaponType.GREATSWORD, WeaponType.HALBERD, WeaponType.HEAVYFLAIL, WeaponType.LONGSPEAR, WeaponType.MAUL,
            WeaponType.LONGBOW, WeaponType.SHORTBOW    
        };
        this.addWeaponProficiencies(wts);

        // defense buffs
        this.fortitude++;
        this.reflex++;

        // set hit points (at this point the PC should be level 1)
        this.hpPerLevel = 5;
        this.maxHealth = 12 + this.constitution + (this.level-1)*this.hpPerLevel;
        this.numHealingSurges = 6 + this.constitutionMod;

        // trained skills
        this.numSkillTrainsLeft += 5;
    }

    public ResultSet getAvailableAtWillPowers() {
        return this.getAvailableAtWillPowers("Ranger");
    }

    public ResultSet getAvailableDailyPowers() {
        return this.getAvailableDailyPowers("Ranger");
    }

    public ResultSet getAvailableEncounterPowers() {
        return this.getAvailableEncounterPowers("Ranger");
    }

    public ResultSet getAvailableUtilityPowers() {
        return this.getAvailableUtilityPowers("Ranger");
    }

}
