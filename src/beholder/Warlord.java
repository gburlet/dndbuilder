package beholder;

import beholder.PlayerCharacter;
import beholder.PlayerCharacter.*;
import beholder.AtWillPower;
import beholder.EncounterPower;
import beholder.DailyPower;
import beholder.UtilityPower;

import java.util.Vector;
import java.sql.*;

public class Warlord extends PlayerCharacter {
    public Warlord(String name) {
        super(name);

        this.powerSource = PowerSource.MARTIAL;

        // armor proficiencies
        ArmorType[] ats = new ArmorType[] {
            ArmorType.CLOTH, ArmorType.LEATHER, ArmorType.HIDE,
            ArmorType.CHAINMAIL, ArmorType.LIGHTSHIELD
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
            WeaponType.GREATSWORD, WeaponType.HALBERD, WeaponType.HEAVYFLAIL, WeaponType.LONGSPEAR, WeaponType.MAUL
        };
        this.addWeaponProficiencies(wts);

        // defense buffs
        this.fortitude++;
        this.will++;

        // set hit points (at this point the PC should be level 1)
        this.hpPerLevel = 5;
        this.maxHealth = 12 + this.constitution + (this.level-1)*this.hpPerLevel;
        this.numHealingSurges = 7 + this.constitutionMod;

        // trained skills
        this.numSkillTrainsLeft += 4;
    }

    public Vector<AtWillPower> getAvailableAtWillPowers() {
        return this.getAvailableAtWillPowers("Warlord");
    }

    public Vector<EncounterPower> getAvailableEncounterPowers() {
        return this.getAvailableEncounterPowers("Warlord");
    }

    public Vector<DailyPower> getAvailableDailyPowers() {
        return this.getAvailableDailyPowers("Warlord");
    }

    public Vector<UtilityPower> getAvailableUtilityPowers() {
        return this.getAvailableUtilityPowers("Warlord");
    }
}
