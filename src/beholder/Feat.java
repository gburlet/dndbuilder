package beholder;

/*
 * Custom struct of Feat allows homebrew Feats to be made
 */
public class Feat {

    public int id;
    public String feat;
    public String description;
    public String book;
    public String races;
    public String pcClass;
    public int level;
    public int strength, constitution, dexterity, intelligence, wisdom, charisma;
    public int acrobatics, arcana, athletics, bluff, diplomacy, dungeoneering, endurance, heal, history, insight, intimidate, nature, perception, religion, stealth, streetwise, thievery;
    public int initiative, speed, surgeValue, armorClass;
    public int fortitude, reflex, will;
        
    public Feat (int id, String feat, String description, String book, int strength, int constitution, int dexterity, int intelligence, int wisdom, int charisma,
                 String races, String pcClass, int level,
                 int acrobatics, int arcana, int athletics, int bluff, int diplomacy, int dungeoneering, int endurance, int heal, int history, int insight,
                 int intimidate, int nature, int perception, int religion, int stealth, int streetwise, int thievery, int initiative, int speed, int surgeValue,
                 int armorClass, int fortitude, int reflex, int will) {
        this.id = id;
        this.feat = feat;
        this.description = description;
        this.book = book;
        this.strength = strength;
        this.constitution = constitution;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
        this.races = races;
        this.pcClass = pcClass;
        this.level = level;
        this.acrobatics = acrobatics;
        this.arcana = arcana;
        this.athletics = athletics;
        this.bluff = bluff;
        this.diplomacy = diplomacy;
        this.dungeoneering = dungeoneering;
        this.endurance = endurance;
        this.heal = heal;
        this.history = history;
        this.insight = insight;
        this.intimidate = intimidate;
        this.nature = nature;
        this.perception = perception;
        this.religion = religion;
        this.stealth = stealth;
        this.streetwise = streetwise;
        this.thievery = thievery;
        this.initiative = initiative;
        this.speed = speed;
        this.surgeValue = surgeValue;
        this.armorClass = armorClass;
        this.fortitude = fortitude;
        this.reflex = reflex;
        this.will = will;
    }

    @Override
    public String toString() {
        String feat = "(" + book + ") " + this.feat + ": " + this.description;
        return feat;
    }
}
