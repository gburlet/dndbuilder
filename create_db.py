#!/usr/local/bin/python

import csv
import sqlite3 as sqlite

class DataParser:

    def __init__(self, db_path, feats_path, powers_path):
        self._feats_path = feats_path
        self._powers_path = powers_path

        try:
            self._con = sqlite.connect(db_path)
        except sqlite.Error, e:
            print "Error %s:" % e.args[0]

    def __del__(self):
        if self._con:
            self._con.close()

    def populate_feats(self, feats_path):
        cur = self._con.cursor()

        # create database table
        try:
            cur.execute("DROP TABLE Feats")
        except sqlite.OperationalError:
            pass

        sql = "CREATE TABLE Feats(Name TEXT, Type TEXT, Prerequisites TEXT, Benefit TEXT, Source TEXT)"
        cur.execute(sql)

        with open(feats_path, 'rU') as ff:
            featlines = csv.reader(ff, dialect=csv.excel)
            for i, f in enumerate(featlines):
                # skip header
                if i == 0:
                    continue

                # feat name: 0
                # feat type (heroic, paragon, epic): 1
                # prerequisites: 2
                # benefit: 3
                # source (PHB, ...): 4
                sql = "INSERT INTO Feats VALUES(?, ?, ?, ?, ?)"
                cur.execute(sql, (f[0], f[1], f[2], f[3], f[4]))

        self._con.commit()

    def populate_powers(self, powers_path):
        cur = self._con.cursor()

        # create database table
        try:
            cur.execute("DROP TABLE Powers")
        except sqlite.OperationalError:
            pass
        sql = "CREATE TABLE Powers(Name TEXT, Type TEXT, Class TEXT, Level INT)"
        cur.execute(sql)

        with open(powers_path, 'rU') as pf:
            powerlines = csv.reader(pf, dialect=csv.excel)
            for i, p in enumerate(powerlines):
                # skip header
                if i == 0:
                    continue

                # power name: 0
                # power type: 1
                # class: 2
                # required level: 3
                sql = "INSERT INTO Powers VALUES(?, ?, ?, ?)"
                cur.execute(sql, (p[0], p[1], p[2], int(p[3])))

        self._con.commit()

if __name__ == '__main__':
    db_path = '/Users/gburlet/Projects/dndbuilder/data/beholder.db'
    feats_path = '/Users/gburlet/Projects/dndbuilder/data/feats.csv'
    powers_path = '/Users/gburlet/Projects/dndbuilder/data/powers.csv'

    dp = DataParser(db_path, feats_path, powers_path)
    dp.populate_feats(feats_path)
    dp.populate_powers(powers_path)
