@echo off
REM To run on Windows, use:
REM scripts\regexTextReplacementInFiles.bat sample_dir "\w*(lan)\w+" "<-replaced->" "*.txt"

java -cp target/classes interview.exercise.RegexTextReplacementInFiles %*
