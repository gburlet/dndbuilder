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

public class Paladin extends PlayerCharacter {
    public Paladin(String name) {
        super(name);

        this.powerSource = PowerSource.DIVINE;

        // armor proficiencies
        ArmorType[] ats = new ArmorType[] {
            ArmorType.CLOTH, ArmorType.LEATHER, ArmorType.HIDE,
            ArmorType.CHAINMAIL, ArmorType.SCALE, ArmorType.PLATE,
            ArmorType.LIGHTSHIELD, ArmorType.HEAVYSHIELD
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

        // implement proficiencies
        ImplementType[] its = new ImplementType[] {ImplementType.HOLYSYMBOL};
        this.addImplementProficiencies(its);

        // defense buffs
        this.fortitude++;
        this.reflex++;
        this.will++;

        // set hit points (at this point the PC should be level 1)
        this.hpPerLevel = 6;
        this.maxHealth = 15 + this.constitution + (this.level-1)*this.hpPerLevel;
        this.numHealingSurges = 10 + this.constitutionMod;

        // trained skills
        this.numSkillTrainsLeft += 3;
    }

    public Vector<AtWillPower> getAvailableAtWillPowers() {
        return this.getAvailableAtWillPowers("Paladin");
    }

    public Vector<EncounterPower> getAvailableEncounterPowers() {
        return this.getAvailableEncounterPowers("Paladin");
    }

    public Vector<DailyPower> getAvailableDailyPowers() {
        return this.getAvailableDailyPowers("Paladin");
    }

    public Vector<UtilityPower> getAvailableUtilityPowers() {
        return this.getAvailableUtilityPowers("Paladin");
    }

    public Vector<Feat> getAvailableFeats() {
        return this.getAvailableFeats("Paladin");
    }
}
