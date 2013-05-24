package beholder;

import beholder.PlayerCharacter;
import beholder.PlayerCharacter.*;

import java.sql.*;

public class Fighter extends PlayerCharacter {
    public Fighter(String name) {
        super(name);

        this.powerSource = PowerSource.MARTIAL;

        // armor proficiencies
        ArmorType[] ats = new ArmorType[] {
            ArmorType.CLOTH, ArmorType.LEATHER, ArmorType.HIDE,
            ArmorType.CHAINMAIL, ArmorType.SCALE, 
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
            WeaponType.GREATSWORD, WeaponType.HALBERD, WeaponType.HEAVYFLAIL, WeaponType.LONGSPEAR, WeaponType.MAUL,
            WeaponType.LONGBOW, WeaponType.SHORTBOW
        };
        this.addWeaponProficiencies(wts);

        // defense buffs
        this.fortitude += 2;

        // set hit points (at this point the PC should be level 1)
        this.hpPerLevel = 6;
        this.maxHealth = 15 + this.constitution + (this.level-1)*this.hpPerLevel;
        this.numHealingSurges = 9 + this.constitutionMod;

        // trained skills
        this.numSkillTrainsLeft += 3;
    }

    public ResultSet getAvailableAtWillPowers() {
        return this.getAvailableAtWillPowers("Fighter");
    }

    public ResultSet getAvailableDailyPowers() {
        return this.getAvailableDailyPowers("Fighter");
    }

    public ResultSet getAvailableEncounterPowers() {
        return this.getAvailableEncounterPowers("Fighter");
    }

    public ResultSet getAvailableUtilityPowers() {
        return this.getAvailableUtilityPowers("Fighter");
    }

}
