package beholder;

import beholder.PlayerCharacter;
import beholder.PlayerCharacter.*;

public class Wizard extends PlayerCharacter {
    public Wizard(String name) {
        super(name);

        this.powerSource = PowerSource.ARCANE;

        // armor proficiencies
        ArmorType[] ats = new ArmorType[] {ArmorType.CLOTH};
        this.addArmorProficiencies(ats);

        // weapon proficiencies
        // simple melee, simple ranged, military melee, military ranged
        WeaponType[] wts = new WeaponType[] {
            WeaponType.DAGGER, WeaponType.QUARTERSTAFF,
        };
        this.addWeaponProficiencies(wts);

        // implement proficiencies
        ImplementType[] its = new ImplementType[] {
            ImplementType.ORB, ImplementType.STAFF, ImplementType.WAND
        };
        this.addImplementProficiencies(its);

        // defense buffs
        this.will += 2;

        // set hit points (at this point the PC should be level 1)
        this.hpPerLevel = 4;
        this.maxHealth = 10 + this.constitution + (this.level-1)*this.hpPerLevel;
        this.numHealingSurges = 6 + this.constitutionMod;

        // trained skills
        this.arcana += 5;
        this.numSkillTrainsLeft += 3;
    }
}
