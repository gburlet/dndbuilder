#!/usr/local/bin/python

import os
import csv
import sqlite3 as sqlite

class DataParser:

    def __init__(self, db_path):
        try:
            self._con = sqlite.connect(db_path)
        except sqlite.Error, e:
            print "Error %s:" % e.args[0]

    def __del__(self):
        if self._con:
            self._con.close()

    def populate_atwillpowers(self, atwillpowers_path):
        cur = self._con.cursor()

        # create database table
        try:
            cur.execute("DROP TABLE AtWillPowers")
        except sqlite.OperationalError:
            pass

        fields = ("Power TEXT", "Book TEXT", "Class TEXT", "Level INT", "Versus TEXT", "WeaponMultiplier INT", "Dice TEXT",
                  "WeaponMultiplierLvl21 INT", "DamageTypes TEXT", "Extra TEXT", "Melee TEXT")
        sql = "CREATE TABLE AtWillPowers(%s)" % ', '.join(fields)
        cur.execute(sql)

        with open(atwillpowers_path, 'rU') as f:
            lines = csv.reader(f, dialect=csv.excel)
            for i, l in enumerate(lines):
                # skip header
                if i == 0:
                    continue

                l = map(DataParser._sanitize, l)
                sql = "INSERT INTO AtWillPowers VALUES(%s)" % ', '.join(['?' for f in fields])

                # data sanitization
                dmg_types = l[8]
                if l[9]:
                    dmg_types += ', %s' % l[9]

                values = (l[0], l[1], l[2], l[3], l[4], l[5], l[6], l[7], dmg_types, l[10], l[11])
                cur.execute(sql, values)

        self._con.commit()

    def populate_dailypowers(self, dailypowers_path):
        cur = self._con.cursor()

        # create database table
        try:
            cur.execute("DROP TABLE DailyPowers")
        except sqlite.OperationalError:
            pass

        fields = ("Power TEXT", "Book TEXT", "Class TEXT", "Level INT")
        sql = "CREATE TABLE DailyPowers(%s)" % ', '.join(fields)
        cur.execute(sql)

        with open(dailypowers_path, 'rU') as f:
            lines = csv.reader(f, dialect=csv.excel)
            for i, l in enumerate(lines):
                # skip header
                if i == 0:
                    continue

                l = map(DataParser._sanitize, l)
                sql = "INSERT INTO DailyPowers VALUES(%s)" % ', '.join(['?' for f in fields])

                values = l[:len(fields)]
                cur.execute(sql, values)

        self._con.commit()

    def populate_encounterpowers(self, encounterpowers_path):
        cur = self._con.cursor()

        # create database table
        try:
            cur.execute("DROP TABLE EncounterPowers")
        except sqlite.OperationalError:
            pass

        fields = ("Power TEXT", "Book TEXT", "Class TEXT", "Level INT")
        sql = "CREATE TABLE EncounterPowers(%s)" % ', '.join(fields)
        cur.execute(sql)

        with open(encounterpowers_path, 'rU') as f:
            lines = csv.reader(f, dialect=csv.excel)
            for i, l in enumerate(lines):
                # skip header
                if i == 0:
                    continue

                l = map(DataParser._sanitize, l)
                sql = "INSERT INTO EncounterPowers VALUES(%s)" % ', '.join(['?' for f in fields])

                values = l[:len(fields)]
                cur.execute(sql, values)

        self._con.commit()

    def populate_utilitypowers(self, utilitypowers_path):
        cur = self._con.cursor()

        # create database table
        try:
            cur.execute("DROP TABLE UtilityPowers")
        except sqlite.OperationalError:
            pass

        fields = ("Power TEXT", "Book TEXT", "Class TEXT", "Level INT")
        sql = "CREATE TABLE UtilityPowers(%s)" % ', '.join(fields)
        cur.execute(sql)

        with open(utilitypowers_path, 'rU') as f:
            lines = csv.reader(f, dialect=csv.excel)
            for i, l in enumerate(lines):
                # skip header
                if i == 0:
                    continue

                l = map(DataParser._sanitize, l)
                sql = "INSERT INTO UtilityPowers VALUES(%s)" % ', '.join(['?' for f in fields])

                values = l[:len(fields)]
                cur.execute(sql, values)

        self._con.commit()

    def populate_armor(self, armor_path):
        cur = self._con.cursor()

        # create database table
        try:
            cur.execute("DROP TABLE Armor")
        except sqlite.OperationalError:
            pass

        fields = ("Armor TEXT", "Bonus INT", "SkillCheck INT", "Speed INT", "Type TEXT", "Level INT", "Quality INT", "Category TEXT", 
                  "Fortitude INT", "Reflex INT", "Will INT", "DamageReduction INT", "Book TEXT")
        sql = "CREATE TABLE Armor(%s)" % ', '.join(fields)
        cur.execute(sql)

        with open(armor_path, 'rU') as f:
            lines = csv.reader(f, dialect=csv.excel)
            for i, l in enumerate(lines):
                # skip header
                if i == 0:
                    continue

                l = map(DataParser._sanitize, l)
                sql = "INSERT INTO Armor VALUES(%s)" % ', '.join(['?' for f in fields])

                values = (l[0], l[2], l[3], l[4], l[5], l[6], l[7], l[8], l[9], l[10], l[11], l[12], l[1])
                cur.execute(sql, values)

        self._con.commit()
    
    def populate_magicarmor(self, magicarmor_path):
        cur = self._con.cursor()

        # create database table
        try:
            cur.execute("DROP TABLE MagicArmor")
        except sqlite.OperationalError:
            pass

        fields = ("Armor TEXT", "Level INT", "Bonus INT", "Book TEXT", "Any INT", "Cloth INT", "Leather INT", "Hide INT",
                  "Chain INT", "Scale INT", "Plate INT", "Acrobatics INT", "Arcana INT", "Athletics INT", 
                  "Bluff INT", "Diplomacy INT", "Dungeoneering INT", "Endurance INT", "Heal INT", "History INT",
                  "Insight INT", "Intimidate INT", "Nature INT", "Perception INT", "Religion INT", "Stealth INT",
                  "Streetwise INT", "Thievery INT")
        sql = "CREATE TABLE MagicArmor(%s)" % ', '.join(fields)
        cur.execute(sql)

        with open(magicarmor_path, 'rU') as f:
            lines = csv.reader(f, dialect=csv.excel)
            for i, l in enumerate(lines):
                # skip header
                if i == 0:
                    continue

                l = map(DataParser._sanitize, l)
                sql = "INSERT INTO MagicArmor VALUES(%s)" % ', '.join(['?' for f in fields])

                values = l[:len(fields)]
                cur.execute(sql, values)

        self._con.commit()

    def populate_weapons(self, weapons_path):
        cur = self._con.cursor()

        # create database table
        try:
            cur.execute("DROP TABLE Weapons")
        except sqlite.OperationalError:
            pass

        fields = ("Weapon TEXT", "WeaponGroup TEXT", "ProficiencyBonus INT DEFAULT '0'", "Dice INT DEFAULT '1'", 
                  "Damage INT DEFAULT '0'", "ShortRange INT", "LongRange INT", "Properties TEXT", 
                  "Category TEXT", "Hands INT", "Melee INT", "Book TEXT")
        sql = "CREATE TABLE Weapons(%s)" % ', '.join(fields)
        cur.execute(sql)

        with open(weapons_path, 'rU') as f:
            lines = csv.reader(f, dialect=csv.excel)
            for i, l in enumerate(lines):
                # skip header
                if i == 0:
                    continue

                l = map(DataParser._sanitize, l)
                sql = "INSERT INTO Weapons VALUES(%s)" % ', '.join(['?' for f in fields])

                # data sanitization
                wgroup = l[2]
                if l[3]:
                    wgroup += ', %s' % l[3]
                wproperty = l[9]
                if l[10]:
                    wproperty += ', %s' % l[10]

                values = (l[0], wgroup, l[4], l[5], l[6], l[7], l[8], wproperty, l[11], l[12], l[13], l[1])
                cur.execute(sql, values)

        self._con.commit()

    def populate_magicweapons(self, magicweapons_path):
        cur = self._con.cursor()

        # create database table
        try:
            cur.execute("DROP TABLE MagicWeapons")
        except sqlite.OperationalError:
            pass

        fields = ("Weapon TEXT", "Level INT", "Book TEXT", "Bonus INT", "Any INT", "Melee INT", "Ranged INT", "Thrown INT", "Axe INT",
                  "Box INT", "Crossbow INT", "Flail INT", "Hammer INT", "HeavyBlade INT", "LightBlade INT", "Mace INT", "Pick INT",
                  "Polearm INT", "Sling INT", "Spear INT", "Staff INT", "HandCrossbow INT", "Dagger INT", "Greatsword INT", "Longsword INT",
                  "Sickle INT", "SpikedGauntlet INT", "Scourge INT", "TripleHeadedFlail INT", "Whip INT")
        sql = "CREATE TABLE MagicWeapons(%s)" % ', '.join(fields)
        cur.execute(sql)

        with open(magicweapons_path, 'rU') as f:
            lines = csv.reader(f, dialect=csv.excel)
            for i, l in enumerate(lines):
                # skip header
                if i == 0:
                    continue

                l = map(DataParser._sanitize, l)
                sql = "INSERT INTO MagicWeapons VALUES(%s)" % ', '.join(['?' for f in fields])

                values = l[:len(fields)]
                cur.execute(sql, values)

        self._con.commit()

    def populate_equipment(self, equipment_path):
        cur = self._con.cursor()

        # create database table
        try:
            cur.execute("DROP TABLE Equipment")
        except sqlite.OperationalError:
            pass

        fields = ("Equipment TEXT", "Gold INT", "Silver INT", "Copper INT", "Weight INT")
        sql = "CREATE TABLE Equipment(%s)" % ', '.join(fields)
        cur.execute(sql)

        with open(equipment_path, 'rU') as f:
            lines = csv.reader(f, dialect=csv.excel)
            for i, l in enumerate(lines):
                # skip header
                if i == 0:
                    continue

                l = map(DataParser._sanitize, l)
                sql = "INSERT INTO Equipment VALUES(%s)" % ', '.join(['?' for f in fields])

                values = l[:len(fields)]
                cur.execute(sql, values)

        self._con.commit()

    def populate_magicitems(self, magicitems_path):
        cur = self._con.cursor()

        # create database table
        try:
            cur.execute("DROP TABLE MagicItems")
        except sqlite.OperationalError:
            pass

        fields = ("Slot TEXT", "Item TEXT", "Level INT", "Book TEXT", "Enhancement INT", "Acrobatics INT", "Arcana INT", 
                  "Athletics INT", "Bluff INT", "Diplomacy INT", "Dungeoneering INT", "Endurance INT", "Heal INT", "History INT",
                  "Insight INT", "Intimidate INT", "Nature INT", "Perception INT", "Religion INT", "Stealth INT",
                  "Streetwise INT", "Thievery INT", "Speed INT", "Initiative INT", "Surges INT", "SurgeValue INT", 
                  "Fortitude INT", "Reflex INT", "Will INT", "ArmorClass INT", "Strength INT", "Dexterity INT", 
                  "Wisdom INT", "Ranged INT", "Melee INT", "Weapon INT")
        sql = "CREATE TABLE MagicItems(%s)" % ', '.join(fields)
        cur.execute(sql)

        with open(magicitems_path, 'rU') as f:
            lines = csv.reader(f, dialect=csv.excel)
            for i, l in enumerate(lines):
                # skip header
                if i == 0:
                    continue

                l = map(DataParser._sanitize, l)
                sql = "INSERT INTO MagicItems VALUES(%s)" % ', '.join(['?' for f in fields])

                values = l[:len(fields)]
                cur.execute(sql, values)

        self._con.commit()

    @staticmethod
    def _sanitize(x):
        '''
        Sanitize data fields for missing references in excel data files
        '''
        x = x.strip()
        if x == '#REF!':
            return ''
        elif x == 'TRUE':
            return '1'
        elif x == 'FALSE':
            return '0'
        else:
            return x
     
if __name__ == '__main__':
    data_root = '/Users/gburlet/Projects/dndbuilder/data'

    db_path = os.path.join(data_root, 'beholder.db')
    dp = DataParser(db_path)

    # Powers, feats, and rituals
    atwillpowers_path = os.path.join(data_root, 'atwillpowers.csv')
    dailypowers_path = os.path.join(data_root, 'dailypowers.csv')
    encounterpowers_path = os.path.join(data_root, 'encounterpowers.csv')
    utilitypowers_path = os.path.join(data_root, 'utilitypowers.csv')
    feats_path = os.path.join(data_root, 'feats.csv')
    rituals_path = os.path.join(data_root, 'rituals.csv')

    dp.populate_atwillpowers(atwillpowers_path)
    dp.populate_dailypowers(dailypowers_path)
    dp.populate_encounterpowers(encounterpowers_path)
    dp.populate_utilitypowers(utilitypowers_path)

    # Armor
    armor_path = os.path.join(data_root, 'armor.csv')
    magicarmor_path = os.path.join(data_root, 'magicarmor.csv')

    dp.populate_armor(armor_path)
    dp.populate_magicarmor(magicarmor_path)

    # Weapons
    weapons_path = os.path.join(data_root, 'weapons.csv')
    magicweapons_path = os.path.join(data_root, 'magicweapons.csv')
    implement_path = os.path.join(data_root, 'implements.csv')

    dp.populate_weapons(weapons_path)
    dp.populate_magicweapons(magicweapons_path)

    # Equipment
    equipment_path = os.path.join(data_root, 'equipment.csv')
    magicitems_path = os.path.join(data_root, 'magicitems.csv')

    dp.populate_equipment(equipment_path)
    dp.populate_magicitems(magicitems_path)
