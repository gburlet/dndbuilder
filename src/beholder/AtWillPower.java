package beholder;

/*
 * Custom struct of At-will powers allows homebrew powers to be made.
 */
public class AtWillPower {

    public int id;
    public String power;
    public String book;
    public String pcClass;
    public int level;
    public String versus;
    public int weaponMultiplier;
    public String dice;
    public int weaponMultiplierLvl21;
    public String damageTypes; 
    public String extra;
    public boolean melee;

    public AtWillPower (int id, String power, String book, String pcClass, int level, String versus,
                        int weaponMultiplier, String dice, int weaponMultiplierLvl21, String damageTypes, 
                        String extra, boolean melee) {
        this.id = id;
        this.power = power;
        this.book = book;
        this.pcClass = pcClass;
        this.level = level;
        this.versus = versus;
        this.weaponMultiplier = weaponMultiplier;
        this.dice = dice;
        this.weaponMultiplierLvl21 = weaponMultiplierLvl21;
        this.damageTypes = damageTypes;
        this.extra = extra;
        this.melee = melee;
    }

    @Override
    public String toString() {
        String roll = Integer.toString(this.weaponMultiplier);
        if (dice == "w") {
            roll += "[W]";
        }
        else {
            roll += "d" + dice;
        }

        String awp = "(" + book + ") " + this.power + ", " + roll + " versus: " + this.versus;
        return awp;
    }
}
