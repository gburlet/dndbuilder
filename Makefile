CC=javac
PACKAGE=beholder
SRC=src/$(PACKAGE)/{PlayerCharacter.java,Cleric.java,Fighter.java,Paladin.java,Ranger.java,Rogue.java,Warlock.java,Warlord.java,Wizard.java,Die.java}
TEST_SRC=test/src/$(PACKAGE)/PlayerCharacterTest.java
BUILD_PATH=bin
TEST_BUILD_PATH=test/bin
TRANSLATE_PATH=translation
TRANSLATOR=../j2objc/dist/j2objc
DATA_PATH=data
CLASSPATH=/Library/Java/Extensions/junit4.10/junit-4.10.jar:$(TEST_BUILD_PATH)

pc: $(SRC)
	$(CC) $< -d $(BUILD_PATH)

test: $(TEST_SRC)
	$(CC) $< -sourcepath src -d $(TEST_BUILD_PATH) -cp $(CLASSPATH)

runtests: test
	java -cp $(CLASSPATH) org.junit.runner.JUnitCore $(PACKAGE).PlayerCharacterTest

translate:
	mkdir -p $(TRANSLATE_PATH)
	$(TRANSLATOR) -d $(TRANSLATE_PATH) $(SRC)

database:
	touch $(DATA_PATH)/beholder.db
	python create_db.py

clean:
	rm -rf $(BUILD_PATH) $(TEST_BUILD_PATH) $(TRANSLATE_PATH)

$(SRC): | $(BUILD_PATH)

$(BUILD_PATH):
	mkdir $(BUILD_PATH)

$(TEST_SRC): | $(TEST_BUILD_PATH)

$(TEST_BUILD_PATH):
	mkdir $(TEST_BUILD_PATH)
