@echo off

set /p commit=Commit message:

git add .

git commit -m "%commit%"

git push origin main

echo Press any key to exit...