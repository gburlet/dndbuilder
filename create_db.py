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

                values = l
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

    # Armor
    armor_path = os.path.join(data_root, 'armor.csv')
    magicarmor_path = os.path.join(data_root, 'magicarmor.csv')

    # Weapons
    weapons_path = os.path.join(data_root, 'weapons.csv')
    magicweapons_path = os.path.join(data_root, 'magicweapons.csv')
    implement_path = os.path.join(data_root, 'implements.csv')

    dp.populate_weapons(weapons_path)
    dp.populate_magicweapons(magicweapons_path)

    # Equipment
    equipment_path = os.path.join(data_root, 'equipment.csv')
    magicitems_path = os.path.join(data_root, 'magicitems.csv')
