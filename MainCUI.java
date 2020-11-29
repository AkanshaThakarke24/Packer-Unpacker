import java.io.*;
import java.util.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;

class MainCUI
{
	public static void main(String arg[])
	{
		Scanner sobj = new Scanner(System.in);

		while(true)
		{
			System.out.println("//////////////////////////////////////////////////");
			System.out.println("Enter your choice");
			System.out.println("1 : Packing");
			System.out.println("2 : Unpacking");
			System.out.println("3 : Exit");
			System.out.println("//////////////////////////////////////////////////");			

			String Dir,Filename;
			int choice = 0;
			choice = sobj.nextInt();

			switch(choice)
			{
				case 1:
					System.out.println("Enter Directory name");
					Dir = sobj.next();

					System.out.println("Enter the file name for packing");
					Filename = sobj.next();

					Packer pobj = new Packer(Dir,Filename);
					System.out.println("Packing Completed");

				break;

				case 2:
					System.out.println("Enter source file name : ");
					Filename = sobj.next();
					
					Unpacker upobj = new Unpacker(Filename);
					System.out.println("Unpacking Completed");
					break;

				case 3:
					System.out.println("Thank you for using Akankshs's Packer-Unpacker");
					System.exit(0);
					break;

				default:
					System.out.println("Wrong choice");
					break;
			}
		}

	}
}

//////////////////////  CLASS PACKER  ////////////////////////////

class Packer
{
//	Object for file writing
	public FileOutputStream outstream = null;

//	parametrised constructor
	public Packer(String FolderName, String FileName)
	{
		try
		{
			System.out.println("Packing Files......................");
//			Create new file for packing
			File outfile = new File(FileName);
			outstream = new FileOutputStream(FileName);

//			Set the current working directory for folder traversal
//			System.setProperty("user.dir",FolderName);
			
			TravelDirectory(FolderName);
		}
		catch(Exception obj)
		{
			System.out.println(obj);
		}
	}

	public void TravelDirectory(String path)
	{
		File directoryPath = new File(path);

//		Get all file names from directory
		File arr[] = directoryPath.listFiles();

		int totalPackedFiles = 0;

		for(File filename : arr)
		{
//			System.out.println(filename.getAbsolutePath());
			
			if(filename.getName().endsWith(".txt"))
			{
				totalPackedFiles++;
				PackFile(filename.getAbsolutePath());	
			}		
		}
		System.out.println("Total files Packed : "+totalPackedFiles);
	}

	public void PackFile(String FilePath)
	{
		
		System.out.println("File Packed : "+ FilePath);
		
		byte Header[] = new byte[100];
		byte Buffer[] = new byte[1024];
		int length = 0;

		FileInputStream istream = null;

		File fobj = new File(FilePath);

		String temp = FilePath+" "+fobj.length();
		
//		Create header of 100 bytes
		for(int i = temp.length(); i< 100; i++)
		{
			temp = temp + " ";
		}		

		Header = temp.getBytes();
		try
		{
//			open the file for reading
			istream = new FileInputStream(FilePath);

			outstream.write(Header,0,Header.length);
			while((length = istream.read(Buffer)) > 0)
			{
				outstream.write(Buffer,0,length);
			}

			istream.close();
		}
		catch(Exception obj)
		{}
//		System.out.println("Header : "+temp.length());

	}
}

//////////////////////  CLASS UNPACKER  ////////////////////////////

class Unpacker {
	
	public FileOutputStream outstream = null;
	
	public Unpacker(String src) {
		unpackFile(src);
	}
	
	public void unpackFile(String FilePath)
	{
		try 
		{
			System.out.println("Extracting Files....................");
			FileInputStream instream = new FileInputStream(FilePath);
			byte Header[] = new byte[100];
			int length = 0;
			int cnt = 0;
			while((length = instream.read(Header,0,100))>0)
			{
//				Header : hp/home/desktop/demo/hello.txt 10
				String str = new String(Header);
				
//				getting filename from its path 
				
				String ext = str.substring(str.lastIndexOf("/"));
//				ext = /hello.txt 10

				ext = ext.substring(1);
//				ext = hello.txt 10				
				
				String words[] = ext.split("\\s");
//				words = {hello.txt, 10}
				String name = words[0];
				int size = Integer.parseInt(words[1]);
				
				byte arr[] = new byte[size];
				instream.read(arr,0,size);
				
				FileOutputStream fout = new FileOutputStream(name);
				fout.write(arr,0,size);
				System.out.println("File Unpacked : "+name);
				cnt++;
			}
			System.out.println("Total Files Unpacked : "+cnt);
		}
		catch(Exception obj)
		{}
	}
}












