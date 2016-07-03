package com.lagopusempire.MagicCompass.zorascommandsystem;

/**
 * This is a class that holds various important things for a command, such as the name of the command executed, the executor itself, and the args.
 * This class's intended use is to have it's data plugged into custom comamnd executors, which may need more or less data than this class (and thus the command system itself) can offer.
 * @author Zora
 *
 * @param <T> The comamnd executor type
 */
public class CommandPackage<T>
{
	private final T command;
	private final String cmdName;
	private final String[] preArgs;
	private final String[] args;

	private final boolean isNull;
	
	/**
	 * Constructor. This constructs an empty command package. This means that the command was not found, so the command system has no info to return. {@link #isNull()} will return true.
	 */
	CommandPackage()
	{
		this.command = null;
		this.preArgs = null;
		this.args = null;
		this.cmdName = null;
		
		this.isNull = true;
	}
	
	/**
	 * Constructor. This constructs a command package with all the data the command system has to offer. {@link #isNull()} will return false.
	 * @param command The command to be executed.
	 * @param cmdName The name of the the command (the first 'arg') that the user used to reference the command.
	 * @param preArgs The args that the command system filled in for the '*' symbols specified in the command format when the command was registered.
	 * @param args The args that came after the command.
	 */
	CommandPackage(T command, String cmdName, String[] preArgs, String[] args)
	{
		this.command = command;
		this.preArgs = preArgs;
		this.args = args;
		this.cmdName = cmdName;
		
		this.isNull = false;
	}
	
	/**
	 * @return Gets the command executor
	 */
	public T getCommand()
	{
		return command;
	}
	
	/**
	 * @return the name of the command the user used to reference the command.
	 */
	public String getCmdName()
	{
		return cmdName;
	}
	
	/**
	 * @return The list of values the command system replaced the asterisks (*) with
	 */
	public String[] getPreArgs()
	{
		return preArgs;
	}
	
	/**
	 * @return The args that came after the command
	 */
	public String[] getArgs()
	{
		return args;
	}
	
	/**
	 * @return True if the user's command didn't find any commands in the command system, False if something was found. If This returns true, every other getter method will return null.
	 */
	public boolean isNull()
	{
		return isNull;
	}
}
