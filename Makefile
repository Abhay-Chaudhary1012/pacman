SRC_DIR  = src
BIN_DIR  = bin
JAR_NAME = PacMan.jar
MANIFEST = MANIFEST.MF
JAVAC    = javac
JAR      = jar
JAVA     = java

SOURCES  = $(SRC_DIR)/App.java $(SRC_DIR)/PacMan.java

.PHONY: all build jar run clean

all: build jar

build:
	@echo "Compiling sources..."
	@mkdir -p $(BIN_DIR)
	$(JAVAC) -d $(BIN_DIR) $(SOURCES)
	@echo "Copying assets..."
	@cp -r $(SRC_DIR)/assets $(BIN_DIR)/assets 2>/dev/null || true

jar: build
	@echo "Packaging $(JAR_NAME)..."
	$(JAR) cfm $(JAR_NAME) $(MANIFEST) -C $(BIN_DIR) .
	@echo "Done! Run with: java -jar $(JAR_NAME)"

run: jar
	$(JAVA) -jar $(JAR_NAME)

clean:
	@echo "Cleaning..."
	@rm -rf $(BIN_DIR)
	@rm -f $(JAR_NAME)
