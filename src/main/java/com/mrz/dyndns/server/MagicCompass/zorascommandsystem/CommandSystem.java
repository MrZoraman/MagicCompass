package com.mrz.dyndns.server.MagicCompass.zorascommandsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommandSystem<T>
{
	/**
	 * Constructor for the command system
	 */
	public CommandSystem()
	{
		commands = new HashMap<String[], T>();
	}

	private final Map<String[], T> commands;

	/**
	 * Registers a command. Make sure your cmdString is not null and not empty!
	 * 
	 * @param cmdString
	 *            The format for the command to be referenced with by the user.
	 *            For example, if I wanted to bind a command executor to the
	 *            command "help", I would put "help" in the cmdString argument.
	 *            If I wanted to bind another command executor to
	 *            "help something", then I could type that in, and the system
	 *            would be able to distinguish between if the user wanted the
	 *            general help, or the help for 'something'. Maybe 'something'
	 *            goes under more than one name. Then you can put 'help
	 *            {something|stuff}', and it would point both 'help something'
	 *            and 'help stuff' to the same command. Maybe you're one of
	 *            those people who likes to have the page numbers before the
	 *            arguments. The command system supports wildcards, so you could
	 *            register 'help * {something|stuff}', and whatever the user put
	 *            in place of the asterisk will be added to the preArgs
	 *            parameter that will be explained later on.
	 * @param cmd The command executor
	 * @return A set of the first word in the commands that were registered. For
	 *            for instance, if you registered '{a|b|c|d} some * stuff' to a 
	 *            command, then a set containing 'a', 'b', 'c', 'd' would be
	 *            returned.
	 */
	public Set<String> registerCommand(final String cmdString, final T cmd)
	{
		Set<String> registeredCommandNames = new HashSet<String>();

		// make sure the cmdString isn't empty ("")
		if (cmdString.length() <= 0)
		{
			throw new IllegalArgumentException("cmdString cannot be empty!");
		}

		String[][] possibilities = splitCmdString(cmdString);
		for (int ii = 0; ii < possibilities.length; ii++)
		{
			commands.put(possibilities[ii], cmd);
			registeredCommandNames.add(possibilities[ii][0]);
		}

		return registeredCommandNames;
	}

	/**
	 * Gets and parses command info given a string
	 * @param cmdMessage The string that the user has inputted.
	 * @return A {@link zorascommandsystem.CommandPackage CommandPackage} containing
	 *            various info about the command, including the executor itself, ready 
	 *            to be run.
	 */
	public CommandPackage<T> makeCommandPackage(String cmdMessage)
	{
		String[] parts = cmdMessage.split(" ");
		// List<String> preArgs = new ArrayList<String>();

		// Not an empty command
		if (parts.length > 0)
		{
			int bestMatchAmount = 0;
			String[] bestMatchString = null;

			for (String[] possibility : commands.keySet())
			{
				int matches = getMatchingArgs(possibility, parts);
				if (matches > bestMatchAmount)
				{
					bestMatchAmount = matches;
					bestMatchString = possibility;
				}
			}

			if (bestMatchAmount > 0)
			{
				List<String> preArgs = new ArrayList<String>();
				List<String> args = new ArrayList<String>();
				for (int ii = 0; ii < parts.length; ii++)
				{
					if (ii < bestMatchString.length)
					{
						if (bestMatchString[ii].equals("*"))
						{
							preArgs.add(parts[ii]);
						}
					}
					else
					{
						args.add(parts[ii]);
					}
				}

				CommandPackage<T> pack = new CommandPackage<T>(commands.get(bestMatchString), bestMatchString[0], preArgs.toArray(new String[preArgs.size()]), args.toArray(new String[args.size()]));

				return pack;

				// this.executeCommand(commands.get(bestMatchString),
				// preArgs.toArray(new String[preArgs.size()]), args.toArray(new
				// String[args.size()]));

				// return
				// commands.get(bestMatchString).onCommand(preArgs.toArray(new
				// String[preArgs.size()]), args.toArray(new
				// String[args.size()]));
			}
			else
			{
				// command not found!
				// System.out.println("command not found!");
			}
		}
		else
		{
			// command cannot be empty!
			// Log.error("command cannot be empty!!!");
		}

		return new CommandPackage<T>();
	}

	/**
	 * Parses a command into various valid possibilities as specified by the
	 * command
	 * 
	 * @param cmdString
	 *            The command to parse
	 * @return An array of arrays of valid arguments
	 */
	private static String[][] splitCmdString(String cmdString)
	{
		String[] cmdStringParts = safeSplit(cmdString);

		// A list of all the different options that came out of cmdStringParts
		Set<List<String>> cmdStringVersions = new HashSet<List<String>>();
		populateVersionList(cmdStringVersions, new ArrayList<String>(), 0, cmdStringParts);

		String[][] cmdVersions = new String[cmdStringVersions.size()][];

		// Convert Set<List<String>> to String[][]
		int index = 0;
		Iterator<List<String>> it = cmdStringVersions.iterator();
		while (it.hasNext())
		{
			List<String> version = it.next();
			String[] versionArray = version.toArray(new String[version.size()]);
			cmdVersions[index] = versionArray;
			index++;
		}

		return cmdVersions;
	}

	/**
	 * Finds the amount of arguments that match between two arrays of arguments
	 * 
	 * @param known
	 *            The known correct sequence of arguments
	 * @param toVerify
	 *            The arguments being verified against the known args
	 * @return The amount of args that matched in a row, or 0 if it is not a
	 *         match
	 */
	private static int getMatchingArgs(String[] known, String[] toVerify)
	{
		if (toVerify.length < known.length)
		{
			return 0;
		}

		int matchingArgs = 0;
		for (int ii = 0; ii < known.length; ii++)
		{
			if (toVerify.length > ii)
			{
				if (known[ii].equals("*") || known[ii].equalsIgnoreCase(toVerify[ii]))
				{
					matchingArgs++;
				}
				else
				{
					return 0;
				}
			}
			else
			{
				// We're done here
				break;
			}
		}

		return matchingArgs;
	}

	/**
	 * Populates a set of lists of strings full of the potential command
	 * possibilities of a given set of command arguments. For example, if you
	 * cmd arguments is ["a", "{b|c}", "d"] You will get "a b d" and "a c d".
	 * 
	 * @param versionList
	 *            Each version of a command is represented as a list of strings.
	 *            This is a set of all of the versions that it found.
	 * @param workingVersion
	 *            The method needs to be supplied an empty arraylist to start
	 *            building and expanding off of as it finds new versions to add
	 *            to the versionList
	 * @param index
	 *            The index to start searching at. You will probably want to set
	 *            this to 0.
	 * @param cmdStringParts
	 *            The array of strings that is being searched for the various
	 *            valid command possibilities (versions)
	 */
	private static void populateVersionList(Set<List<String>> versionList, List<String> workingVersion, int index, String[] cmdStringParts)
	{
		// Are we at the end?
		if (index == cmdStringParts.length)
		{
			// Yes we are. Add the result to the list.
			versionList.add(workingVersion);
			return;
		}

		String part = cmdStringParts[index];
		index++;

		// Is it a variant?
		if (part.startsWith("{"))
		{
			String[] variants = parseArgumentOptions(part);
			// For each variant, branch off
			for (String variant : variants)
			{
				List<String> newWorkingVersion = new ArrayList<String>(workingVersion);
				newWorkingVersion.add(variant);
				populateVersionList(versionList, newWorkingVersion, index, cmdStringParts);
			}
		}
		else
		{
			workingVersion.add(part);
			populateVersionList(versionList, workingVersion, index, cmdStringParts);
		}
	}

	/**
	 * Parses a variant into an array of possible options. For instance, {a|b|cd
	 * ef g} would be parsed to an array of [a, b, cd ef g].
	 * 
	 * @param variant
	 *            The string to parse
	 * @return An array of possible options
	 */
	private static String[] parseArgumentOptions(String variant)
	{
		// Will be converted to an array and returned at the end
		List<String> options = new ArrayList<String>();

		// This is one of the options that is added to the options list when it
		// is completed.
		StringBuilder wordInTheMaking = new StringBuilder();

		char[] chars = variant.toCharArray();
		int layer = 0;
		for (int ii = 0; ii < chars.length; ii++)
		{
			char c = chars[ii];
			switch (c)
			{
			case '{':
				if (layer != 0)
				{
					wordInTheMaking.append(c);
				}
				layer++;
				break;
			case '}':
				layer--;
				if (layer == 0)
				{
					ii = chars.length;
					options.add(wordInTheMaking.toString());
				}
				else
				{
					wordInTheMaking.append(c);
				}
				break;
			case '|':
				if (layer == 1)
				{
					// Let's not add an empty string
					if (wordInTheMaking.length() > 0)
					{
						options.add(wordInTheMaking.toString());
					}
					// clear the buffer
					wordInTheMaking.setLength(0);
				}
				else
				{
					wordInTheMaking.append(c);
				}
				break;
			default:
				wordInTheMaking.append(c);
			}
		}

		return options.toArray(new String[options.size()]);
	}

	/**
	 * This will split a command string int o an array, but will maintain the
	 * integrity of variants (they look like {a|b|c})
	 * 
	 * @param cmdString
	 * @return
	 */
	private static String[] safeSplit(String cmdString)
	{
		// This will be converted into an array and returned in the end
		ArrayList<String> parts = new ArrayList<String>();

		StringBuilder partBuilder = new StringBuilder();

		char[] chars = cmdString.toCharArray();

		boolean goingThroughVariant = false;
		for (int ii = 0; ii < chars.length; ii++)
		{
			char c = chars[ii];
			switch (c)
			{
			case '{':
				goingThroughVariant = true;
				partBuilder.append(c);
				break;
			case '}':
				partBuilder.append(c);
				goingThroughVariant = false;
				break;

			case ' ':
				if (goingThroughVariant == false)
				{
					// Make sure there is something to add to the part list
					if (partBuilder.length() > 0)
					{
						parts.add(partBuilder.toString());
					}
					// empty partBuilder
					partBuilder.setLength(0);
					break;
				}
				// we are going though a variant so we're going to fall though
				// and append it to the part builder
			default:
				partBuilder.append(c);
			}
		}

		// We have some stragglers
		if (partBuilder.length() > 0)
		{
			parts.add(partBuilder.toString());
		}

		return parts.toArray(new String[parts.size()]);
	}
}
