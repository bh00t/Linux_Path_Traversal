package playmentApp;

import java.util.Set;

abstract  class Operations {
	
	static Directory command(String input, Directory currentDir)
	{
		//Removing unnecessary white spaces
		input = input.replaceAll("\\s+", " ");
		
		//Intializing input String array
		String[] inArray = input.trim().split(" ");
		//System.out.println("input ->"+input.replaceAll("\\s+", " "));
		
		//length of input string array
		int len = inArray.length;
		//System.out.println("length"+len);
		String cmd = inArray[0];
				
		switch(cmd)
		{
		//linux simply go to nextline when blank command is given
			case "" :
				return currentDir;
		
		//current directory command
			case "cd" :
			{
				if(len > 2)
				{
					System.out.println("ERR: too many arguments");
					return currentDir;
				}
				else
					return cd(inArray[1],currentDir);
			}
				
				
		//list command	
			case "ls" :
			{
				if(len == 1)
					ls("",currentDir);
				
				else
				{
					for(int i=1;i<len;i++)
					{
						System.out.println("listing ->"+currentDir.getName());
						ls(inArray[i],currentDir);
					}
				}
				
				return currentDir;
			}
		
		//
			case "mkdir" :
			{
				//path parameter is not given
				if(len == 1)
				{
					System.out.println("ERR: mkdir: missing operand");
					return currentDir;
				}
				else
				{
					for(int i=1;i<len;i++)
					{
					
							currentDir = mkdir(inArray[i],currentDir);
					}
					
					return currentDir;
				}
			}
			
			case "pwd" :
				{
					System.out.println(pwd(currentDir));
					return currentDir;
				}
				
			
			case "rm" :
				{
					Directory root = new Directory("/");
					System.gc();
					System.out.println("SUCC: CLEARED: RESET TO ROOT");
					return root;
				}
				
			case "session" :
				//session clear command doesn't need path parameter	
				if(len > 1 && len < 3 &&inArray[1].equals("clear"))
				{
					//Createing new Root Dir
					Directory root = new Directory("/");
					System.gc();
					System.out.println("SUCC: CLEARED: RESET TO ROOT");
					return root;
				}
				else
				{
					System.out.println("ERR: CANNOT RECOGNIZE INPUT1");
					return currentDir;
				}
				
				default: 
				{
					System.out.println("ERR: CANNOT RECOGNIZE INPUT1");
					return currentDir;
				}
		}
	}
	private static boolean validatePath(String[] inputArray)
	{
		//String
		return false;
	}
	
	
	private static Directory cd(String path,Directory currentDir)
	{
		Directory dir = traverse(path,currentDir,0);
		
		if(dir == null)
			return currentDir;
		else
		{
			System.out.println("SUCC: REACHED");
			return dir;
		}
	}
	//list command
	private static void ls(String path,Directory currentDir)
	{
		Directory dir = traverse(path,currentDir,0);
		System.out.println(dir.getName());
		if(dir != null)
		{
			Set<String> childName = dir.getChildList();
			String list = "";
			for(String name : childName)
			{
				list = list.concat(name+" ");
			}
			System.out.println("DIRS: " +list.trim());
		}
	}
	
	//make directory command
	private static Directory mkdir(String path,Directory currentDir)
	{
		String[] dirName = path.split("/");
		int length = dirName.length;
		
		Directory dir = currentDir;
			
		switch(dirName[0])
		{
		//blank directory name error
			case "":
			case "..":
			{
				System.out.println("ERR: INVALID PATH");
				return dir;
			}
			
			default :
			{	
				//child directory exist in current directory
				if(dir.addChildDir(dirName[0]) == false)
				{
					// last directory name given in path parameter is already exist
					if(length == 1)
					{
						System.out.println("ERR: DIRECTORY ALREADY EXISTS");
						return dir;
					}

					else
					{
						Directory childDir = dir.getChildDir(dirName[0]);
						int startPos = path.indexOf(dirName[0]);
						startPos+= dirName[0].length() + 1;
						
						String childPath = path.substring(startPos, path.length());
						//System.out.println(path+" s "+startPos+" cp "+childPath);
						childDir = mkdir(childPath,childDir);
						
						dir.updateChildDir(dirName[0], childDir);
						
						return dir;
					}
				}
				// last directory name given in path parameter is not exist
				//adding child directory
				else if(length == 1)
				{
					Directory newChild = new Directory(dirName[0]);
					dir.addChildDir(dirName[0]);
					System.out.println("SUCC: CREATED");
					return dir;
				}
				//directory name given in path parameter does not exist
				else
				{
					System.out.println("ERR: INVALID PATH");
					return dir;
				}
			}
		}				
	}
	
	private static String pwd(Directory currentDir)
	{
		String absolutePath = currentDir.getName();
		System.out.println("pwd1 ->" + currentDir.getName());
		while(currentDir.getName().equals("/") == false)
			{
				currentDir = currentDir.getParentDir();
				System.out.println("pwd ->" + currentDir.getName());
				absolutePath = currentDir.getName().concat(absolutePath);
			}
			
		return "PATH: "+absolutePath;
	}
	
//	private static Directory rm(String path,Directory currentDir)
//	{
//		Directory dir = traverse(path,currentDir,1);
//		
//		if()
//		return null;
//	}
	
	//remove current dir pattern from path parameter
	private static String removeCurrentDir(String path)
	{
		while(path.indexOf("/./") != -1)
			path = path.replaceAll("/./", "/");
		
		return path;
	}
	
	private static Directory traverse(String path,Directory currentDir,int pos)
	{
		path = removeCurrentDir(path);
		
		//path startwith "/" then moving cursor to root 
		if(path.startsWith("/"))
		{
			currentDir = currentDir.getRootDir();
			path = path.substring(1, path.length());
			System.out.println("pathRootT->"+path);
		}
		
		//splitting path in parts "/<path>/<path>/<path>"
		String[] dirName = path.split("/");
		int length = dirName.length;
		
		Directory dir = currentDir;
		System.out.println("Traversing on Path -> "+path);
		for(int i = 0;i< length-pos; i++)
		{
			System.out.println("Enter dirName ->"+dirName[i]+" "+dir.getName());
			if(dirName[i].equals(""))
			{
				break;
			}
			else if(dirName[i].equals(".."))
			{
				System.out.println("getting parent of "+dir.getName()+" - "+dir.getParentDir());
				if(dir.getParentDir() != null)
				{
					System.out.println("condtion");
					dir = dir.getParentDir();
				}			
			}
			else if(dirName[i].equals(".") == false)
			{
				//checking if child exist or not 
				Directory childDir = dir.getChildDir(dirName[i]);
				System.out.println("jhes"+dirName[i]);
				
				if(childDir == null)
				{
					System.out.println("ERR: INVALID PATH "+dirName[i]);
					
					return null;
				}
				//else if(length - i == 1 || childDir != null)
				else
				{
					dir = childDir;
				}
			}
			System.out.println("Exit dirName ->"+dirName[i]+" "+dir.getName()+"  Child List"+dir.getChildList());
		}
		return dir;
	}

}
