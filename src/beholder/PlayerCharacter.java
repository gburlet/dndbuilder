package beholder;

/*
 * Steps for character creation:
 * 1. Choose Class. Your class represents your training or profession, and it is 
 *    the most important part of your character’s capabilities.
 * 2. Choose Race. Decide the race of your character. Your choice of race offers 
 *    several racial advantages to your character.
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
import java.util.Arrays;
import java.lang.Integer;
import java.sql.*;
import beholder.Die;
import beholder.AtWillPower;
import beholder.EncounterPower;
import beholder.DailyPower;
import beholder.UtilityPower;
import beholder.Feat;

/*
 * This class represents a player character (PC)
 * and defines all of the attributes of a character.
 */
public class PlayerCharacter {

    protected String name;
    protected Race race;
    protected PowerSource powerSource;

    protected int experience;
    protected int level;

    protected int maxHealth;
    protected int hpPerLevel;
    protected int health;
    protected int bloodied;
    protected int healingSurge;
    protected int numHealingSurges;

    protected int height;         // inches
    protected int weight;         // lbs
    protected int speed;          // squares
    protected Vector<Language> languages;
    protected Vision vision;
    protected Size size;
    protected Vector<WeaponType> weaponProficiencies;
    protected Vector<ImplementType> implementProficiencies;
    protected Vector<ArmorType> armorProficiencies;
    
    // Ability Scores
    protected int strength, passiveStrength, strengthMod;
    protected int constitution, passiveConstitution, constitutionMod;
    protected int dexterity, passiveDexterity, dexterityMod;
    protected int intelligence, passiveIntelligence, intelligenceMod;
    protected int wisdom, passiveWisdom, wisdomMod;
    protected int charisma, passiveCharisma, charismaMod;

    // Skill Values
    protected int[] skills;

    protected Vector<AtWillPower> atwillpowers;
    protected Vector<EncounterPower> encounterpowers;
    protected Vector<DailyPower> dailypowers;
    protected Vector<UtilityPower> utilitypowers;

    protected Vector<Feat> feats;
    
    // PC maitenance
    protected int numSkillTrainsLeft;
    protected int numLanguagesLeft;
    protected int abilityPointsLeft;
    protected int numFeatsLeft;
    protected int numAtWillPowersLeft;
    protected int numEncounterPowersLeft;
    protected int numDailyPowersLeft;
    protected int numUtilityPowersLeft;

    // defenses 
    protected int armorClass;
    protected int fortitude;
    protected int reflex;
    protected int will;

    // resistances
    protected int acidResistance;
    protected int coldResistance;
    protected int fireResistance;
    protected int forceResistance;
    protected int lightningResistance;
    protected int necroticResistance;
    protected int poisonResistance;
    protected int psychicResistance;
    protected int radiantResistance;
    protected int thunderResistance;

    // Backpack
    protected int encumbrance;
    protected int goldPieces;
    protected int silverPieces;
    protected int copperPieces;

    // database connection
    protected Connection db;

    public enum Race {
        DRAGONBORN, DWARF, ELADRIN, ELF,
        HALFELF, HALFLING, HUMAN, TIEFLING
    }

    public enum PowerSource {
        ARCANE, DIVINE, MARTIAL, OTHER
    }

    public enum Skill {
        ACROBATICS(0), ARCANA(1), ATHLETICS(2), BLUFF(3),
        DIPLOMACY(4), DUNGEONEERING(5), ENDURANCE(6),
        HEAL(7), HISTORY(8), INSIGHT(9), INTIMIDATE(10),
        NATURE(11), PERCEPTION(12), RELIGION(13), STEALTH(14),
        STREETWISE(15), THIEVERY(16);

        private int index;

        private Skill(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }
    }

    public enum Vision {
        NORMAL, LOWLIGHT
    }

    public enum Language {
        COMMON, ARGON, DRACONIC, DWARVEN, ELVEN,
        DEEPSPEECH, GIANT, GOBLIN, PRIMORDIAL,
        QUORI, RIEDRAN
    }

    public enum Size {
        SMALL, MEDIUM, LARGE
    }

    /*
     * Weapon groups defined on pp. 216
     */
    public enum WeaponGroup {
        AXE, BOW, CROSSBOW, FLAIL, HAMMER, 
        HEAVYBLADE, LIGHTBLADE, MACE, PICK,
        POLEARM, SLING, SPEAR, STAFF, UNARMED
    }

    public enum WeaponType {
        UNARMED, CLUB, DAGGER, JAVELIN, MACE, SICKLE,
        SPEAR, GREATCLUB, MORNINGSTAR, QUARTERSTAFF, 
        SCYTHE, BATTLEAXE, FLAIL, HANDAXE, LONGSWORD,
        SCIMITAR, SHORTSWORD, THROWINGHAMMER, WARHAMMER,
        WARPICK, FALCHION, GLAIVE, GREATAXE, GREATSWORD,
        HALBERD, HEAVYFLAIL, LONGSPEAR, MAUL, BASTARDSWORD,
        KATAR, RAPIER, SPIKEDCHAIN, HANDCROSSBOW, SLING,
        CROSSBOW, LONGBOW, SHORTBOW, SHURIKEN
    }

    public enum ImplementType {
        ORB, STAFF, WAND, ROD, HOLYSYMBOL
    }

    public enum ArmorGroup {
        LIGHT, HEAVY
    }

    public enum ArmorType {
        CLOTH, LEATHER, HIDE, CHAINMAIL, SCALE, PLATE,
        LIGHTSHIELD, HEAVYSHIELD
    }

    /*
     * Constructs a blank player character (PC)
     */
    public PlayerCharacter(String name) {
        this.name = name;

        this.numLanguagesLeft = 1;
        this.languages = new Vector<Language>(this.numLanguagesLeft);
        this.addLanguage(Language.COMMON);

        this.weaponProficiencies = new Vector<WeaponType>();
        this.implementProficiencies = new Vector<ImplementType>();
        this.armorProficiencies = new Vector<ArmorType>();
        this.skills = new int[17];

        this.atwillpowers = new Vector<AtWillPower>();
        this.encounterpowers = new Vector<EncounterPower>();
        this.dailypowers = new Vector<DailyPower>();
        this.utilitypowers = new Vector<UtilityPower>();

        this.feats = new Vector<Feat>();

        // become level one
        this.levelUp();

        this.abilityPointsLeft = 32;

        // starting gold
        this.goldPieces = 100;

        this.db = null;

        try {
            Class.forName("org.sqlite.JDBC");
            this.db = DriverManager.getConnection("jdbc:sqlite:data/beholder.db");
            this.db.setAutoCommit(false);
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
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

    public void addWeaponProficiencies(WeaponType[] wts) {
        for (int i = 0; i < wts.length; i++) {
            if (!this.weaponProficiencies.contains(wts[i])) {
                this.weaponProficiencies.add(wts[i]);
            }
        }
    }

    public void addImplementProficiencies(ImplementType[] its) {
        for (int i = 0; i < its.length; i++) {
            if (!this.implementProficiencies.contains(its[i])) {
                this.implementProficiencies.add(its[i]);
            }
        }
    }

    public void addArmorProficiencies(ArmorType[] ats) {
        for (int i = 0; i < ats.length; i++) {
            if (!this.armorProficiencies.contains(ats[i])) {
                this.armorProficiencies.add(ats[i]);
            }
        }
    }

    public void setEncumbrance(int e) {
        this.encumbrance = e;

        if (this.race == Race.DWARF) {
            // TODO: Encumbered Speed
        }
    }

    public void setRace(Race r) {
        switch (r) {
            case DRAGONBORN:
                this.size = Size.MEDIUM;
                this.speed = 6;
                this.vision = Vision.NORMAL;
                this.addLanguage(Language.DRACONIC);

                // ability buffs
                this.passiveStrength = this.strength = 2;
                this.passiveCharisma = this.charisma = 2;

                // skill bonuses
                this.skills[Skill.HISTORY.getIndex()] += 2;
                this.skills[Skill.INTIMIDATE.getIndex()] += 2;

                break;
            case DWARF:
                this.size = Size.MEDIUM;
                this.speed = 5;
                this.vision = Vision.LOWLIGHT;
                this.addLanguage(Language.DWARVEN);

                // ability buffs
                this.passiveConstitution = this.constitution = 2;
                this.passiveWisdom = this.wisdom = 2;

                // skill bonuses
                this.skills[Skill.DUNGEONEERING.getIndex()] += 2;
                this.skills[Skill.ENDURANCE.getIndex()] += 2;

                break;
            case ELADRIN:
                this.size = Size.MEDIUM;
                this.speed = 6;
                this.vision = Vision.LOWLIGHT;
                this.addLanguage(Language.ELVEN);

                // ability buffs
                this.passiveDexterity = this.dexterity = 2;
                this.passiveIntelligence = this.intelligence = 2;

                // skill bonuses
                this.skills[Skill.ARCANA.getIndex()] += 2;
                this.skills[Skill.HISTORY.getIndex()] += 2;

                // weapon proficiencies
                this.addWeaponProficiencies(new WeaponType[] {WeaponType.LONGSWORD});

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
                this.passiveDexterity = this.dexterity = 2;
                this.passiveWisdom = this.wisdom = 2;

                // skill bonuses
                this.skills[Skill.NATURE.getIndex()] += 2;
                this.skills[Skill.PERCEPTION.getIndex()] += 2;

                // weapon proficiencies
                WeaponType[] wts = {WeaponType.LONGBOW, WeaponType.SHORTBOW};
                this.addWeaponProficiencies(wts);

                break;
            case HALFELF:
                this.size = Size.MEDIUM;
                this.speed = 6;
                this.vision = Vision.LOWLIGHT;
                this.addLanguage(Language.ELVEN);
                this.numLanguagesLeft++;

                // ability buffs
                this.passiveConstitution = this.constitution = 2;
                this.passiveCharisma = this.charisma =2;

                // skill bonuses
                this.skills[Skill.DIPLOMACY.getIndex()] += 2;
                this.skills[Skill.INSIGHT.getIndex()] += 2;

                // extra encounter power that is an at-will power from another class
                this.numEncounterPowersLeft++;

                break;
            case HALFLING:
                this.size = Size.SMALL;
                this.speed = 6;
                this.vision = Vision.NORMAL;
                this.numLanguagesLeft++;

                // ability buffs
                this.passiveDexterity = this.dexterity = 2;
                this.passiveCharisma = this.charisma = 2;

                // skill bonuses
                this.skills[Skill.ACROBATICS.getIndex()] += 2;
                this.skills[Skill.THIEVERY.getIndex()] += 2;

                break;
            case HUMAN:
                this.size = Size.MEDIUM;
                this.speed = 6;
                this.vision = Vision.NORMAL;
                this.numLanguagesLeft++;

                // TODO ability buffs: +2 to one ability score

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
                this.passiveIntelligence = this.intelligence = 2;
                this.passiveCharisma = this.charisma = 2;

                // skill bonuses
                this.skills[Skill.BLUFF.getIndex()] += 2;
                this.skills[Skill.STEALTH.getIndex()] += 2;

                this.fireResistance += 5;
                break;
        }

        this.race = r;
    }

    public void setStrength(int s) {
        if (s < 8) {
            s = 8;
        }

        int currentCost = this.calcScoreCost(this.strength);
        int cost = this.calcScoreCost(s);
        if (cost > this.abilityPointsLeft) {
            // calculate max ability score possible
            s = this.calcMaxAbilityScore();
            cost = this.calcScoreCost(s);
        }

        this.abilityPointsLeft -= (cost - currentCost);
        this.strength = s + this.passiveStrength;
        this.strengthMod = this.calcAbilityModifier(this.strength);
    }

    public void setConstitution(int c) {
        if (c < 8) {
            c = 8;
        }

        int currentCost = this.calcScoreCost(this.constitution);

        int cost = this.calcScoreCost(c);
        if (cost > this.abilityPointsLeft) {
            // calculate max ability score possible
            c = this.calcMaxAbilityScore();
            cost = this.calcScoreCost(c);
        }

        this.abilityPointsLeft -= (cost - currentCost);
        this.constitution = c + this.passiveConstitution;
        this.constitutionMod = this.calcAbilityModifier(this.constitution);
    }

    public void setDexterity(int d) {
        if (d < 8) {
            d = 8;
        }

        int currentCost = this.calcScoreCost(this.dexterity);

        int cost = this.calcScoreCost(d);
        if (cost > this.abilityPointsLeft) {
            // calculate max ability score possible
            d = this.calcMaxAbilityScore();
            cost = this.calcScoreCost(d);
        }

        this.abilityPointsLeft -= (cost - currentCost);
        this.dexterity = d + this.passiveDexterity;
        this.dexterityMod = this.calcAbilityModifier(this.dexterity);
    }

    public void setIntelligence(int i) {
        if (i < 8) {
            i = 8;
        }

        int currentCost = this.calcScoreCost(this.intelligence);

        int cost = this.calcScoreCost(i);
        if (cost > this.abilityPointsLeft) {
            // calculate max ability score possible
            i = this.calcMaxAbilityScore();
            cost = this.calcScoreCost(i);
        }

        this.abilityPointsLeft -= (cost - currentCost);
        this.intelligence = i + this.passiveIntelligence;
        this.intelligenceMod = this.calcAbilityModifier(this.intelligence);
    }

    public void setWisdom(int w) {
        if (w < 8) {
            w = 8;
        }

        int currentCost = this.calcScoreCost(this.wisdom);

        int cost = this.calcScoreCost(w);
        if (cost > this.abilityPointsLeft) {
            // calculate max ability score possible
            w = this.calcMaxAbilityScore();
            cost = this.calcScoreCost(w);
        }

        this.abilityPointsLeft -= (cost - currentCost);
        this.wisdom = w + this.passiveWisdom;
        this.wisdomMod = this.calcAbilityModifier(this.wisdom);
    }

    public void setCharisma(int c) {
        if (c < 8) {
            c = 8;
        }

        int currentCost = this.calcScoreCost(this.charisma);

        int cost = this.calcScoreCost(c);
        if (cost > this.abilityPointsLeft) {
            // calculate max ability score possible
            c = this.calcMaxAbilityScore();
            cost = this.calcScoreCost(c);
        }

        this.abilityPointsLeft -= (cost - currentCost);
        this.charisma = c + this.passiveCharisma;
        this.charismaMod = this.calcAbilityModifier(this.charisma);
    }

    /*
     * Helper function to calculate an ability's modifier
     */
    private int calcAbilityModifier(int ability) {
        return (int)(ability-10)/2;
    }

    /*
     * Helper function that calculates the cost of raising an ability score
     */
    private static int calcScoreCost(int score) {
        int cost = 0;
        if (score >= 8 && score < 14) {
            cost = score - 8;
        }
        else if (score >= 14 && score < 17) {
            cost = 5 + (score-13)*2;
        }
        else if (score == 17) {
            cost = 14;
        }
        else if (score == 18) {
            cost = 18;
        }
        /*
        else if (score > 18) {
            // assign cost using the exponential regression line
            cost = (int)Math.exp(0.2602*(score-6.829));
        }
        */

        return cost;
    }

    /*
     * Helper function that calculates a maximum ability score within cost
     */
    private int calcMaxAbilityScore() {
        int maxAbilityScore = 8;
        int cost = 0;
        /*
         * Incrementally raise ability score until it violates the number of
         * points left to spend or reaches the max ability score for level one.
         */
        while (cost <= this.abilityPointsLeft && maxAbilityScore <= 18) {
            maxAbilityScore++;
            cost = this.calcScoreCost(maxAbilityScore);
        }

        // at this point cost > number of points left to spend, go back one iteration
        return --maxAbilityScore;
    }

    public void rollAbilityScores() {
        // pick up 6-sided die
        Die d6 = new Die(6);

        int[] abilityScores = new int[6];
        for (int i = 0; i < 6; i++) {
            // roll 4d6
            int[] rolls = d6.roll(4);
            Arrays.sort(rolls);
            // sum highest three rolls
            abilityScores[i] = rolls[3] + rolls[2] + rolls[1];
        }

        this.strength = abilityScores[0];
        this.constitution = abilityScores[1];
        this.dexterity = abilityScores[2];
        this.intelligence = abilityScores[3];
        this.wisdom = abilityScores[4];
        this.charisma = abilityScores[5];
    }

    public void trainSkill(Skill s) {
        if (this.numSkillTrainsLeft > 0) {
            this.skills[s.getIndex()] += 5;
            this.numSkillTrainsLeft--;
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
                    this.abilityPointsLeft += 2;
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
                    this.abilityPointsLeft += 2;
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

    public Vector<AtWillPower> getAvailableAtWillPowers(String className) {
        Vector<AtWillPower> awps = null;

        try {
            Statement stmt = this.db.createStatement();
            String query = "SELECT ROWID, * FROM AtWillPowers WHERE Book='PHB' AND Level <= " + this.level + " AND Class = '" + className + "';";
            ResultSet res = stmt.executeQuery(query);

            awps = new Vector<AtWillPower>(2);
            for (int i = 0; res.next(); i++) {
                int id = res.getInt("ROWID");
                String power = res.getString("Power");
                String book = res.getString("Book");
                String pcClass = res.getString("Class");
                int level = res.getInt("Level");
                String versus = res.getString("Versus");
                int wm = res.getInt("WeaponMultiplier");
                String dice = res.getString("Dice");
                int wm21 = res.getInt("WeaponMultiplierLvl21");
                String dts = res.getString("DamageTypes");
                String extra = res.getString("Extra");
                int m = res.getInt("Melee");
                boolean melee = false;
                if (res.getInt("Melee") == 1) {
                    melee = true;
                }

                awps.add(new AtWillPower(id, power, book, pcClass, level, versus, wm, dice, wm21, dts, extra, melee));
            }
            stmt.close();
            res.close();
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return awps;
    }

    public boolean addAtWillPower(AtWillPower awp) {
        if (this.numAtWillPowersLeft > 0) {
            // create struct of at-will power info for easy display
            this.atwillpowers.add(awp);
            return true;
        }
        else {
            return false;
        }
    }

    public Vector<EncounterPower> getAvailableEncounterPowers(String className) {
        Vector<EncounterPower> eps = null;

        try {
            Statement stmt = this.db.createStatement();
            String query = "SELECT ROWID, * FROM EncounterPowers WHERE Book='PHB' AND Level <= " + this.level + " AND Class = '" + className + "';";
            ResultSet res = stmt.executeQuery(query);

            eps = new Vector<EncounterPower>(2);
            for (int i = 0; res.next(); i++) {
                int id = res.getInt("ROWID");
                String power = res.getString("Power");
                String book = res.getString("Book");
                String pcClass = res.getString("Class");
                int level = res.getInt("Level");

                eps.add(new EncounterPower(id, power, book, pcClass, level));
            }
            stmt.close();
            res.close();
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return eps;
    }

    public boolean addEncounterPower(EncounterPower ep) {
        if (this.numEncounterPowersLeft > 0) {
            this.encounterpowers.add(ep);
            return true;
        }
        else {
            return false;
        }
    }

    public Vector<DailyPower> getAvailableDailyPowers(String className) {
        Vector<DailyPower> dps = null;

        try {
            Statement stmt = this.db.createStatement();
            String query = "SELECT ROWID, * FROM DailyPowers WHERE Book='PHB' AND Level <= " + this.level + " AND Class = '" + className + "';";
            ResultSet res = stmt.executeQuery(query);

            dps = new Vector<DailyPower>(2);
            for (int i = 0; res.next(); i++) {
                int id = res.getInt("ROWID");
                String power = res.getString("Power");
                String book = res.getString("Book");
                String pcClass = res.getString("Class");
                int level = res.getInt("Level");

                dps.add(new DailyPower(id, power, book, pcClass, level));
            }
            stmt.close();
            res.close();
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return dps;
    }

    public boolean addDailyPower(DailyPower dp) {
        if (this.numDailyPowersLeft > 0) {
            this.dailypowers.add(dp);
            return true;
        }
        else {
            return false;
        }
    }

    public Vector<UtilityPower> getAvailableUtilityPowers(String className) {
        Vector<UtilityPower> ups = null;

        try {
            Statement stmt = this.db.createStatement();
            String query = "SELECT ROWID, * FROM UtilityPowers WHERE Book='PHB' AND Level <= " + this.level + " AND Class = '" + className + "';";
            ResultSet res = stmt.executeQuery(query);

            ups = new Vector<UtilityPower>(2);
            while (res.next()) {
                int id = res.getInt("ROWID");
                String power = res.getString("Power");
                String book = res.getString("Book");
                String pcClass = res.getString("Class");
                int level = res.getInt("Level");

                ups.add(new UtilityPower(id, power, book, pcClass, level));
            }
            stmt.close();
            res.close();
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return ups;
    }

    public boolean addUtilityPower(UtilityPower up) {
        if (this.numUtilityPowersLeft > 0) {
            this.utilitypowers.add(up);
            return true;
        }
        else {
            return false;
        }
    }

    public Vector<Feat> getAvailableFeats(String className) {
        Vector<Feat> fs = null;

        try {
            Statement stmt = this.db.createStatement();
            String query = "SELECT ROWID, * FROM Feats WHERE Book='PHB' AND (Level<=1 OR Level='') AND (Class='Rogue' OR Class='');";
            ResultSet res = stmt.executeQuery(query);

            fs = new Vector<Feat>(2);
            while (res.next()) {
                int id = res.getInt("ROWID");
                String feat = res.getString("Feat");
                String description = res.getString("Description");
                String book = res.getString("Book");
                int strength = res.getInt("Strength");
                int constitution = res.getInt("Constitution");
                int dexterity = res.getInt("Dexterity");
                int intelligence = res.getInt("Intelligence");
                int wisdom = res.getInt("Wisdom");
                int charisma = res.getInt("Charisma");
                int acrobatics = res.getInt("Acrobatics");
                String races = res.getString("Races");
                String pcClass = res.getString("Class");
                int level = res.getInt("Level");
                int arcana = res.getInt("Arcana");
                int athletics = res.getInt("Athletics");
                int bluff = res.getInt("Bluff");
                int diplomacy = res.getInt("Diplomacy");
                int dungeoneering = res.getInt("Dungeoneering");
                int endurance = res.getInt("Endurance");
                int heal = res.getInt("Heal");
                int history = res.getInt("History");
                int insight = res.getInt("Insight");
                int intimidate = res.getInt("Intimidate");
                int nature = res.getInt("Nature");
                int perception = res.getInt("Perception");
                int religion = res.getInt("Religion");
                int stealth = res.getInt("Stealth");
                int streetwise = res.getInt("Streetwise");
                int thievery = res.getInt("Thievery");
                int initiative = res.getInt("Initiative");
                int speed = res.getInt("Speed");
                int surgeValue = res.getInt("SurgeValue");
                int armorClass = res.getInt("ArmorClass");
                int fortitude = res.getInt("Fortitude");
                int reflex = res.getInt("Reflex");
                int will = res.getInt("Will");

                fs.add(new Feat(id, feat, description, book, strength, constitution, dexterity, intelligence, wisdom, charisma,
                  races, pcClass, level, acrobatics, arcana, athletics, bluff, diplomacy, dungeoneering, endurance, heal, 
                  history, insight, intimidate, nature, perception, religion, stealth, streetwise, thievery, initiative, speed, 
                  surgeValue, armorClass, fortitude, reflex, will));

            }
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return fs;
    }

    public boolean addFeat(Feat f) {
        if (this.numFeatsLeft > 0) {
            this.feats.add(f);
            return true;
        }
        else {
            return false;
        }
    }

    /*
     * Overload toString method to print character information
     */
    @Override
    public String toString() {
        String charStr = this.name + "\n---------------\n";
        charStr += this.race + " " + this.getClass().getSimpleName() + "\n\n";
        charStr += "Strength: " + this.strength + " (" + this.strengthMod + ")\n";
        charStr += "Constitution: " + this.constitution + " (" + this.constitutionMod + ")\n";
        charStr += "Dexterity: " + this.dexterity + " (" + this.dexterityMod + ")\n";
        charStr += "Intelligence: " + this.intelligence + " (" + this.intelligenceMod + ")\n";
        charStr += "Wisdom: " + this.wisdom + " (" + this.wisdomMod + ")\n";
        charStr += "Charisma: " + this.charisma + " (" + this.charismaMod + ")\n";
        
        charStr += "\nSkills:";
        for (Skill s : Skill.values()) {
            charStr += "\n" + s + ": " + this.skills[s.getIndex()];
        }

        charStr += "\nAt-will Powers:";
        for (AtWillPower awp: this.atwillpowers) {
            charStr += "\n" + awp;
        }

        charStr += "\nEncounter Powers:";
        for (EncounterPower ep: this.encounterpowers) {
            charStr += "\n" + ep;
        }

        charStr += "\nDaily Powers:";
        for (DailyPower dp: this.dailypowers) {
            charStr += "\n" + dp;
        }

        charStr += "\nUtility Powers:";
        for (UtilityPower up: this.utilitypowers) {
            charStr += "\n" + up;
        }

        charStr += "\nFeats:";
        for (Feat f: this.feats) {
            charStr += "\n" + f;
        }

        return charStr;
    }
}
