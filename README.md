MagicCompass
============

Allows players to set the location their compass points to in minecraft

# Building
1. First, in the root directory of the project, add a folder called `lib`
1. Then, in the `lib` folder, add `bukkit-shaded.jar`.
1. Once `bukkit-shaded.jar` has been added, you can simply run `mvn clean install` to build the jar file. It will be located at `target/MagicCompass.jar`.

# Getting bukkit-shaded.jar
**This guide is only relevant to those who want to build this project!**
Here is how I get my bukkit-shaded.jar. There might be better ways, and do share if you do know a better way.

1. Grab spigot's [build tools](https://hub.spigotmc.org/jenkins/job/BuildTools/)
1. Place the `BuildTools.jar` in a directory of your choosing.
1. Make sure git and maven are available from the command line. Getting this working depends on what system you are using. It should be automatic with a unix system, as long as you have them installed. For windows, make sure the proper bin folders for maven and git are added to your PATH.
1. If you are on a unix system, you should be able to just navigate to the directory where `BuildTools.jar` is contained. On a windows system, you will need to navigate to the folder where `BuildTools.jar` is located using the git shell. If you installed git with the right features enabled, you'll be able to navigate to the directory in explorer, right click in an empty space, and select 'Git Bash Here'.
1. Execute `java -jar BuildTools.jar`. It will download everything needed.
1. You will find that there is now a `Bukkit` folder. Navigate to that folder using your command line/terminal.
1. Execute `mvn clean install`.
1. You will find that there is now a `target` folder. Navigate to that folder (you no longer need a command line/terminal at this point).
1. There will be two jar files. The only difference between the two files is that one ends with '-shaded' in the filename. Rename the shaded file to `bukkit-shaded.jar`
1. Copy/move `bukkit-shaded.jar` from `{BuildTools.jar directory}/Bukkit/target/` to `{MagicCompass repository root}/lib/bukkit-shaded.jar`
1. You are now ready to build! Continue the steps in the Building section of this document.
