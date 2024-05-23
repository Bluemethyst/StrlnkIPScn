# StrlnkIPScn

A small, simple, powerful, fast, cross-platform CLI program to scan all devices on your local network. First made for my Starlink network so i can see when my server changes its address.

## Installation
### You will need Java 21 or higher to run this program!
Download the [latest release](https://github.com/Bluemethyst/StrlnkIPScn/releases/latest) from the releases page and run the jar file with `java -jar StrlnkIPScn_1-0-0.jar <Arguments>`.

## CLI Arguments
| Argument | Description              | Default Value | Input Type |
|----------|--------------------------|---------------|------------|
| -sl      | Lowest subnet to scan    | 1             | Integer    |
| -sh      | Highest subnet to scan   | 1             | Integer    |
| -rl      | Lowest ip range to scan  | 1             | Integer    |
| -rh      | Highest ip range to scan | 254           | Integer    |

## How to add to PATH
Want to add this program to PATH, so you can use it from anywhere at any time?
### Windows
1. Create a batch file named `strlnkipscn.bat` in the same directory as your jar file with the following content:
```bat
@echo off
java -jar %~dp0\StrlnkIPScn_1-0-0.jar %*
```
2. Add the directory containing your jar file and the batch file to the PATH environment variable. You can do this by:  
   -  Right-click on Computer.
   -  Click on Properties.
   -  Click on Advanced system settings.
   -  Click on Environment Variables.
   -  Under System Variables, find PATH, and click on it.
   -  In the Edit windows, modify PATH by adding the directory of your jar file at the end, preceded with a semicolon (;).
### Linux
1. Create a bash script named strlnkipscn in the same directory as your jar file with the following content:
```bash
#!/bin/bash
java -jar $(dirname "$0")/StrlnkIPScn_1-0-0.jar "$@"
```
2. Make the script executable by running chmod +x strlnkipscn in the terminal.  
3. Add the directory containing your jar file and the bash script to the PATH environment variable by adding the following line to your ~/.bashrc or ~/.bash_profile file:
```bash
export PATH=$PATH:/path/to/your/jar/directory
```
4. Run source ~/.bashrc or source ~/.bash_profile to apply the changes.

#### Now, you should be able to run your program just by typing strlnkipscn in the console