CC=javac
CLASSPATH=/usr/share/java/junit.jar
PACKAGE=beholder
SRC=src/$(PACKAGE)/{PlayerCharacter.java,Cleric.java,Fighter.java,Paladin.java,Ranger.java,Rogue.java,Warlock.java,Warlord.java,Wizard.java,Die.java}
TEST_SRC=test/src/$(PACKAGE)/PlayerCharacterTest.java
BUILD_PATH=bin
TEST_BUILD_PATH=test/bin
TRANSLATE_PATH=translation
TRANSLATOR=../j2objc/dist/j2objc

pc: $(SRC)
	$(CC) $< -d $(BUILD_PATH)

test: $(TEST_SRC)
	$(CC) $< -sourcepath src -d $(TEST_BUILD_PATH) -cp $(CLASSPATH)

runtests: test
	java -cp $(CLASSPATH):$(TEST_BUILD_PATH) org.junit.runner.JUnitCore $(PACKAGE).PlayerCharacterTest

translate:
	mkdir -p $(TRANSLATE_PATH)
	$(TRANSLATOR) -d $(TRANSLATE_PATH) $(SRC)

clean:
	rm -rf $(BUILD_PATH) $(TEST_BUILD_PATH) $(TRANSLATE_PATH)

$(SRC): | $(BUILD_PATH)

$(BUILD_PATH):
	mkdir $(BUILD_PATH)

$(TEST_SRC): | $(TEST_BUILD_PATH)

$(TEST_BUILD_PATH):
	mkdir $(TEST_BUILD_PATH)
