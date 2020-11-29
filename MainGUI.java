import java.lang .*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;


class MainGUI
{
	public static void main(String args[])
	{
		
		MainWindow m = new MainWindow();
	}
}

class MainWindow
{
	public MainWindow()
	{
		JFrame f = new JFrame("Packer-Unpacker");

		JButton pack = new JButton("PACK");
		pack.setBounds(50,100,100,30);
		JButton unpack = new JButton("UNPACK");
		unpack.setBounds(200,100,100,30);
		JButton exit = new JButton("EXIT");
		exit.setBounds(350,100,100,30);

		f.add(pack);
		f.add(unpack);
		f.add(exit);
		
		
		f.setSize(500,300);
		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent eobj){
				f.setVisible(false);
				PackWindow pobj = new PackWindow();
				
			}
		});
		
		unpack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent eobj){
				f.setVisible(false);
				UnpackWindow upobj = new UnpackWindow();
			}
		});

		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent eobj){
				f.setVisible(false);	
				ExitWindow exobj = new ExitWindow();
				
			}
		});
	}
}

class PackWindow //implements ActionListener
{
	public PackWindow()
	{
		JFrame f = new JFrame("Packer");
		
		JButton b = new JButton("submit");
		b.setBounds(200,185,100,30);

		JLabel file = new JLabel("File Name ");
		file.setBounds(40,95,200,100);

		JTextField filet = new JTextField();
		filet.setBounds(170,130,170,30);

		JLabel dir = new JLabel("Directory Name");
		dir.setBounds(40,45,200,100);

		JTextField dirt = new JTextField();
		dirt.setBounds(170,80,170,30);

		f.add(b);
		f.add(file);
		f.add(filet);
		f.add(dir);
		f.add(dirt);
		f.setSize(500,300);
		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent eobj){
//				System.out.println("Directory : "+dirt.getText());
//				System.out.println("File : "+filet.getText());
				Packer pobj = new Packer(dirt.getText(),filet.getText());
				f.setVisible(false);
			}
		});
		MainWindow m = new MainWindow();
	}
}

class UnpackWindow
{
	public UnpackWindow()
	{
		JFrame f = new JFrame("Unpacker");

		JButton b = new JButton("submit");
		b.setBounds(200,185,100,30);

		JLabel file = new JLabel("File Name ");
		file.setBounds(40,45,200,100);

		JTextField filet = new JTextField();
		filet.setBounds(170,80,170,30);

		f.add(b);
		f.add(file);
		f.add(filet);
		
		f.setSize(500,300);
		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent eobj){
				
//				System.out.println("File : "+filet.getText());
				Unpacker upobj = new Unpacker(filet.getText());
				f.setVisible(false);
			}
		});
		MainWindow m = new MainWindow();
	}
}

class ExitWindow
{
	public ExitWindow()
	{
		JFrame f = new JFrame("Packer-Unpacker");

		JLabel exit = new JLabel("Thank you for using Akanksha's Packer-Unpacker");
		exit.setBounds(40,45,450,100);

		
		f.add(exit);
			
		f.setSize(500,300);
		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
