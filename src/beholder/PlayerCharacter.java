package beholder;

/*
 * Steps for character creation:
 * 1. Choose Race. Decide the race of your character. Your choice of race offers 
 *    several racial advantages to your character.
 * 2. Choose Class. Your class represents your training or profession, and it is 
 *    the most important part of your character’s capabilities.
 * 3. Determine Ability Scores. Generate your ability scores. Your ability scores 
 *    describe the fundamental strengths of your body and mind. Your race adjusts 
 *    the scores you generate, and different classes rely on different ability scores. 
 * 4. Choose Skills. Skills measure your ability to perform tasks such as jumping 
 *    across chasms, hiding from observers, and identifying monsters.
 * 5. Select Feats. Feats are natural advantages or special training you possess. 
 * 6. Choose Powers. Each character class offers a different selection of powers 
 *    to choose from.
 * 7. Choose Equipment. Pick your character’s armor, weapons, implements, and 
 *    basic adventuring gear. At higher levels, you’ll be able to find and create 
 *    magic items.
 * 8. Fill in the Numbers. Calculate your hit points, Armor Class and other defenses, 
 *    initiative, attack bonuses, damage bonuses, and skill check bonuses.
 * 9. Roleplaying Character Details. Flesh out your character with details about your 
 *    personality, appearance, and beliefs.
 */

import java.util.Vector;

/*
 * This class represents a player character (PC)
 * and defines all of the attributes of a character.
 */
public class PlayerCharacter {

    private String name;
    private Race race;

    private int experience;
    private int level;
    private int health;
    private int bloodied;
    private int healingSurge;
    private int numHealingSurges;

    private int height;         // inches
    private int weight;         // lbs
    private int speed;          // squares
    private Vector<Language> languages;
    private Vision vision;
    private Size size;
    private Vector<WeaponType> weaponProficiencies;
    
    // Ability Scores
    private int strength;
    private int strengthMod;
    private int constitution;
    private int constitutionMod;
    private int dexterity;
    private int dexterityMod;
    private int intelligence;
    private int intelligenceMod;
    private int wisdom;
    private int wisdomMod;
    private int charisma;
    private int charismaMod;

    // Skill Values
    private int acrobatics;
    private int arcana;
    private int athletics;
    private int bluff;
    private int diplomacy;
    private int dungeoneering;
    private int endurance;
    private int heal;
    private int history;
    private int insight;
    private int intimidate;
    private int nature;
    private int perception;
    private int religion;
    private int stealth;
    private int streetwise;
    private int thievery;

    // PC maitenance
    private int numSkillTrainsLeft;
    private int numLanguagesLeft;
    private int numAbilitiesLeft;
    private int numFeatsLeft;
    private int numAtWillPowersLeft;
    private int numEncounterPowersLeft;
    private int numDailyPowersLeft;
    private int numUtilityPowersLeft;

    // defenses 
    private int armorClass;
    private int fortitude;
    private int reflex;
    private int will;

    // resistances
    private int fireResistance;

    // Backpack
    private int encumbrance;
    private int goldPieces;
    private int silverPieces;
    private int copperPieces;

    private enum Race {
        DRAGONBORN, DWARF, ELADRIN, ELF,
        HALFELF, HALFLING, HUMAN, TIEFLING
    }

    private enum Class {
        CLERIC, FIGHTER, PALADIN, RANGER, 
        ROGUE, WARLOCK, WARLORD, WIZARD
    }

    private enum Skill {
        ACROBATICS, ARCANA, ATHLETICS, BLUFF,
        DIPLOMACY, DUNGEONEERING, ENDURANCE,
        HEAL, HISTORY, INSIGHT, INTIMIDATE,
        NATURE, PERCEPTION, RELIGION, STEALTH,
        STREETWISE, THIEVERY
    }

    private enum Vision {
        NORMAL, LOWLIGHT
    }

    private enum Language {
        COMMON, ARGON, DRACONIC, DWARVEN, ELVEN,
        DEEPSPEECH, GIANT, GOBLIN, PRIMORDIAL,
        QUORI, RIEDRAN
    }

    private enum Size {
        SMALL, MEDIUM, LARGE
    }

    /*
     * Weapon groups defined on pp. 216
     */
    private enum WeaponGroup {
        AXE, BOW, CROSSBOW, FLAIL, HAMMER, 
        HEAVYBLADE, LIGHTBLADE, MACE, PICK,
        POLEARM, SLING, SPEAR, STAFF, UNARMED
    }

    private enum WeaponType {
        UNARMED, CLUB, DAGGER, JAVELIN, MACE, SICKLE,
        SPEAR, GREATCLUB, MORNINGSTAR, QUATERSTAFF, 
        SCYTHE, BATTLEAXE, FLAIL, HANDAXE, LONGSWORD,
        SCIMITAR, SHORTSWORD, THROWINGHAMMER, WARHAMMER,
        WARPICK, FALCHION, GLAIVE, GREATAXE, GREATSWORD,
        HALBERD, HEAVYFLAIL, LONGSPEAR, MAUL, BASTARDSWORD,
        KATAR, RAPIER, SPIKEDCHAIN, HANDCROSSBOW, SLING,
        CROSSBOW, LONGBOW, SHORTBOW, SHURIKEN
    }

    /*
     * Constructs a blank player character (PC)
     */
    public PlayerCharacter() {
        this.name = "";

        this.numLanguagesLeft = 1;
        this.addLanguage(Language.COMMON);

        // become level one
        this.levelUp();

        // starting gold
        this.goldPieces = 100;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(int hp) {
        this.health = hp;

        // TODO (DRAGONBORN) Dragonborn Fury: When you’re bloodied, 
        // you gain a +1 racial bonus to attack rolls.
    }

    /*
     * Use a healing surge
     */
    public void useHealingSurge() {
        if (this.numHealingSurges > 0) {
            int toHeal = this.healingSurge;

            if (this.race == Race.DRAGONBORN) {
                // TODO: Draconic heritage: your healing surge value is equal
                // to one-quarter of your maximum hit points + your
                // Constitution modifier.
            }

            this.health += toHeal;
            // TODO: cap health at maximum

            this.numHealingSurges--;
        }
    }

    public void addLanguage(Language lang) {
        if (numLanguagesLeft > 0) {
            if (!this.languages.contains(lang)) {
                this.languages.add(lang);
                this.numLanguagesLeft--;
            }
        }
    }

    public void addWeaponProficiency(WeaponType wt) {
        if (!this.weaponProficiencies.contains(wt)) {
            this.weaponProficiencies.add(wt);
        }
    }

    public void setEncumbrance(int e) {
        this.encumbrance = e;

        if (this.race == Race.DWARF) {
            // TODO: Encumbered Speed
        }
    }

    public void setRace(Race race) {
        switch (race) {
            case DRAGONBORN:
                this.size = Size.MEDIUM;
                this.speed = 6;
                this.vision = Vision.NORMAL;
                this.addLanguage(Language.DRACONIC);

                // ability buffs
                this.strength += 2;
                this.charisma += 2;

                // skill bonuses
                this.history += 2;
                this.intimidate += 2;

                break;
            case DWARF:
                this.size = Size.MEDIUM;
                this.speed = 5;
                this.vision = Vision.LOWLIGHT;
                this.addLanguage(Language.DWARVEN);

                // ability buffs
                this.constitution += 2;
                this.wisdom += 2;

                // skill bonuses
                this.dungeoneering += 2;
                this.endurance += 2;

                break;
            case ELADRIN:
                this.size = Size.MEDIUM;
                this.speed = 6;
                this.vision = Vision.LOWLIGHT;
                this.addLanguage(Language.ELVEN);

                // ability buffs
                this.dexterity += 2;
                this.intelligence += 2;

                // skill bonuses
                this.arcana += 2;
                this.history += 2;

                // weapon proficiencies
                this.addWeaponProficiency(WeaponType.LONGSWORD);

                // eladrin education: extra skill
                this.numSkillTrainsLeft++;

                // defense buff
                this.will++;

                break;
            case ELF:
                this.size = Size.MEDIUM;
                this.speed = 7;
                this.vision = Vision.LOWLIGHT;
                this.addLanguage(Language.ELVEN);

                // ability buffs
                this.dexterity += 2;
                this.wisdom += 2;

                // skill bonuses
                this.nature += 2;
                this.perception += 2;

                // weapon proficiencies
                this.addWeaponProficiency(WeaponType.LONGBOW);
                this.addWeaponProficiency(WeaponType.SHORTBOW);

                break;
            case HALFELF:
                this.size = Size.MEDIUM;
                this.speed = 6;
                this.vision = Vision.LOWLIGHT;
                this.addLanguage(Language.ELVEN);
                this.numLanguagesLeft++;

                // ability buffs
                this.constitution += 2;
                this.charisma += 2;

                // skill bonuses
                this.diplomacy += 2;
                this.insight += 2;

                // extra encounter power that is an at-will power from another class
                this.numEncounterPowersLeft++;

                break;
            case HALFLING:
                this.size = Size.SMALL;
                this.speed = 6;
                this.vision = Vision.NORMAL;
                this.numLanguagesLeft++;

                // ability buffs
                this.dexterity += 2;
                this.charisma += 2;

                // skill bonuses
                this.acrobatics += 2;
                this.thievery += 2;

                break;
            case HUMAN:
                this.size = Size.MEDIUM;
                this.speed = 6;
                this.vision = Vision.NORMAL;
                this.numLanguagesLeft++;

                // ability buffs: +2 to one ability score
                this.numAbilitiesLeft += 2;

                // skill bonuses: training in one additional skill
                this.numSkillTrainsLeft++;

                // defense buff
                this.fortitude++;
                this.reflex++;
                this.will++;

                break;
            case TIEFLING:
                this.size = Size.MEDIUM;
                this.speed = 6;
                this.vision = Vision.LOWLIGHT;
                this.numLanguagesLeft++;

                // ability buffs
                this.intelligence += 2;
                this.charisma += 2;

                // skill bonuses
                this.bluff += 2;
                this.stealth += 2;

                this.fireResistance += 5;
                break;
        }
    }

    public void levelUp() {
        if (this.level < 30) {
            this.level++;
            
            switch (this.level) {
                case 1: // 0 XP
                    this.numFeatsLeft++;
                    this.numAtWillPowersLeft += 2;
                    this.numEncounterPowersLeft++;
                    this.numDailyPowersLeft++;
                    break;
                case 2: // 1,000 XP
                    this.numUtilityPowersLeft++;
                    this.numFeatsLeft++;
                    break;
                case 3: // 2,250 XP
                    this.numEncounterPowersLeft++;
                    break;
                case 4: // 3,750 XP
                    this.numFeatsLeft++;
                    this.numAbilitiesLeft += 2;
                    break;
                case 5: // 5,500 XP
                    this.numDailyPowersLeft++;
                    break;
                case 6: // 7,500 XP
                    this.numUtilityPowersLeft++;
                    this.numFeatsLeft++;
                    break;
                case 7: // 10,000 XP
                    this.numEncounterPowersLeft++;
                    break;
                case 8: // 13,000 XP
                    this.numFeatsLeft++;
                    this.numAbilitiesLeft += 2;
                    break;
                case 9: // 16,500 XP
                    this.numDailyPowersLeft++;
                    break;
                case 10: // 20,500 XP
                    this.numFeatsLeft++;
                    this.numUtilityPowersLeft++;
                    break;
                case 11: // 26,000 XP
                    this.strength++;
                    this.constitution++;
                    this.dexterity++;
                    this.intelligence++;
                    this.wisdom++;
                    this.charisma++;

                    this.numFeatsLeft++;
                    break;
                case 12:
                    break;
                case 13:
                    break;
                case 14:
                    break;
                case 15:
                    break;
                case 16:
                    break;
                case 17:
                    break;
                case 18:
                    break;
                case 19:
                    break;
                case 20:
                    break;
                case 21:
                    break;
                case 22:
                    break;
                case 23:
                    break;
                case 24:
                    break;
                case 25:
                    break;
                case 26:
                    break;
                case 27:
                    break;
                case 28:
                    break;
                case 29:
                    break;
                case 30:
                    break;
            }

            if (this.race == Race.TIEFLING && this.level % 2 == 0) {
                // increase fire resistance
                this.fireResistance++;
            }
        }
    }
}
