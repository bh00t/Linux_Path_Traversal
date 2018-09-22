package playmentApp;

import java.util.Set;
import java.util.TreeMap;

public class Directory{
	
	Directory parentDirectory;
	String name;
	TreeMap<String,Directory> childDirectory = new TreeMap<String,Directory>();
	
	Directory(String name)
	{
		this.name = name;
		parentDirectory = null;
		childDirectory = new TreeMap<String,Directory>();
		
	}
	
	String getName()
	{
		return this.name;
	}
	
	Directory getChildDir(String name)
	{
		return this.childDirectory.get(name);
	}
	
	boolean addChildDir(String name)
	{
		if(this.childDirectory.containsKey(name))
			return false;
		else
		{
			Directory dir = new Directory(name);
			//Connecting parent directory
			dir.parentDirectory = this;
			
			this.childDirectory.put(name, dir);
			return true;
		}
	}
	
	boolean removeChildDir(String name)
	{
		if(this.childDirectory.remove(name) == null)
			return false;
		else
			return true;
	}
	
	Directory getParentDir()
	{
		return this.parentDirectory;
	}
	
	Directory getRootDir()
	{
		Directory dir = this;
		
		//Root's parent will be null
		while(dir.parentDirectory != null)
		{
			dir = dir.parentDirectory;
		}
		return dir;
	}
	
	Set<String> getChildList()
	{
		return this.childDirectory.keySet();
	}
}
