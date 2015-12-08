package mypackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import net.rim.device.api.io.IOUtilities;


public class info 
{
		 
	 // USER info
	 static String token = "";
	 static String MDP = "";
	 static String ID = "";  // identifiant
	 static String ID_user = ""; // number id
	 static boolean connect = false;
	
	
	// UN FILM
	 static String film = "NULL";
	 static String cine = "NULL";
	 static int IDfilm  = -1;
	 static int IDcine = -1;
	 static int nb_placeint = -1;
	

	// les detaiL d'un film
	 public static String sceance = "";
	 public static String sceanceID = "";
	 public static String nb_place = "";
	 public static String n_salle = "";
	 public static String duree = "";

	public static void set_nb_place(String ok) 
	{
	 	nb_place = ok;
	}
	
	public static void set_sceanceID(String ok) 
	{
	 	sceanceID = ok;
	}
	public static void set_nb_place_int(int ok) 
	{
	 	nb_placeint = ok;
	}
	
	public static void set_sceance(String ok) 
	{
	 	sceance = ok;
	}
	
	public static void set_film(String ok) 
	{
		film = ok;
	}
	
	public static void set_cine(String ok) 
	{
		cine = ok;
	}
	
	public static void set_token(String ok) 
	{
		token = ok;
	}
	
	public static void set_duree(String ok)
	{
		duree = ok;
	}

	public static void set_IDfilm(int ok)
	{
		IDfilm = ok;
	}
	public static void set_IDcine(int ok)
	{
		IDcine = ok;
	}
	public static void set_ID_user(String ok)
	{
		ID_user = ok;
	}

	public static void set_connect(boolean ok)
	{
		connect = ok;
	}

	
	public static void writeTextFile(String fName, String text) 
	{
		DataOutputStream os = null;
		FileConnection fconn = null;
		try {
			fconn = (FileConnection) Connector
					.open(fName, Connector.READ_WRITE);
			if (!fconn.exists())
				fconn.create();

			os = fconn.openDataOutputStream();
			os.write(text.getBytes());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (null != os)
					os.close();
				if (null != fconn)
					fconn.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public static String readTextFile(String fName)
	{
		  String result = null;
		  FileConnection fconn = null;
		  DataInputStream is = null;
		  try {
		   fconn = (FileConnection) Connector.open(fName, Connector.READ_WRITE);
		   is = fconn.openDataInputStream();
		   byte[] data = IOUtilities.streamToBytes(is);
		   result = new String(data);
		  } catch (IOException e) {
		   System.out.println(e.getMessage());
		  } finally {
		   try {
		    if (null != is)
		     is.close();
		    if (null != fconn)
		     fconn.close();
		   } catch (IOException e) {
		    System.out.println(e.getMessage());
		   }
		  }
		  return result;
		 }
}
