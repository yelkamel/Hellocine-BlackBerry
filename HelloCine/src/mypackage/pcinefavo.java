package mypackage;

import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.location.AddressInfo;
import javax.microedition.location.Landmark;
import javax.microedition.location.QualifiedCoordinates;
import net.rim.blackberry.api.invoke.Invoke;
import net.rim.blackberry.api.invoke.MapsArguments;
import net.rim.blackberry.api.invoke.MessageArguments;
import net.rim.blackberry.api.invoke.PhoneArguments;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.image.ImageFactory;

public class pcinefavo extends MainScreen 
{
	RichTextField[] lesfilm;
	LabelField[] lescine;
	
	String num = "0148339695"; 
	String adr = "youcef@gmail.go";
	String loca = "14 rue des ecoles";
	

	// Pour utilise Maps 
	public void invokeBlackBerryMaps(double lat, double lng, AddressInfo addressInfo, String address)
	{
	    Landmark []landmarks = new Landmark[1];
	    QualifiedCoordinates coordinates = new QualifiedCoordinates(lat, lng, 0, 0, 0);
	    landmarks[0] = new Landmark(address,address,coordinates,addressInfo);
	    MapsArguments mapsArgs = new MapsArguments(landmarks);
	    Invoke.invokeApplication(Invoke.APP_TYPE_MAPS,mapsArgs);
	}
	
	
	int i = 0;
	int y = 0;
	
	public pcinefavo(int numero) 
	{
	    final String listcine[];	   
	    listcine = new String[14];
	    Font maPolice = null;
		// Fond d'ecran
		Background maCouleur = BackgroundFactory
				.createSolidBackground(Color.BLACK);
		Bitmap fbbitmap = Bitmap.getBitmapResource("font.png");
		VerticalFieldManager monEcran = (VerticalFieldManager) getMainManager();
		Background fb = BackgroundFactory.createBitmapBackground(fbbitmap);
		monEcran.setBackground(maCouleur);
		monEcran.setBackground(fb);

		// TITRE
		BitmapField monTitre = new BitmapField(new Bitmap(0, 0), FIELD_HCENTER);
		EncodedImage Titre = EncodedImage.getEncodedImageResource("Tcine.png");
		monTitre.setImage(Titre);
		add(monTitre);
	

		lesfilm = new RichTextField[14];
		lescine = new LabelField[14];
		RichTextField[] ouvert;
		ouvert = new RichTextField[14];
	    LabelField[] address; 
		address = new LabelField[14];
		LabelField[] tel; 
		tel = new LabelField[14];
		LabelField[] email; 
		email = new LabelField[14];		
		
		
		// the Police
		try 
		{
			FontFamily typeCaracteres = FontFamily.forName("Arial");
			maPolice = typeCaracteres.getFont(Font.BOLD, 45);
		} 
		catch (ClassNotFoundException e)
		{
			System.out.println(e.getMessage());
		}
		
		
		//chargement list de film		
		String url = "http://hellocine.3ie.fr/Webservices/get_cinemas";		
		String m_xml="";
		HttpPost _httpPost=new HttpPost(url);
		m_xml=_httpPost.executeMethodGET();
		
		if (!m_xml.equals(""))
		{
			info.writeTextFile("file:///SDCard/hellocine.data", m_xml);
		}
		final lec lecture = new lec("file:///SDCard/hellocine.data");
		
		
		String a = "";   
	  
		for ( ; y < listcine.length; y++)
    	{	
			
			 a = lecture.readApiString("cinema_name", y);
			 if (a == null || a.equals(""))
			 {				 
				 break;
			 }
			
			 listcine[y] = a;
		}
		

		add(new LabelField(""));
		String favfile = info.readTextFile("file:///SDCard/hellofavo" + info.ID + ".data");
		
		for (; i < listcine.length; i++)
		{
			
			if (favfile == null || favfile.equals(""))
			{
				Dialog.alert("Vous n'avez aucun film en favorie !");
				break;
			}
				
					
			if (favfile.indexOf(listcine[i]) > 0)
			{
				//favfile.substring(listcine[i].length() + 1);
			final int n = i;
			lescine[i] = new LabelField(listcine[i]+" :" , Field.FOCUSABLE| Field.HIGHLIGHT_SELECT)
			{
                protected boolean navigationClick(int status,int time)
                {
					if (info.connect)
					{
						info.set_IDcine(n);
						info.set_cine(listcine[n]);						
						if (info.film.equals("NULL")) 
						{
							UiApplication.getUiApplication().pushScreen(new pfilm(0));							
						} 
						else
						{
							UiApplication.getUiApplication().pushScreen(new pdetailfilm());
							
						}
					}
					else
					{
						UiApplication.getUiApplication().pushScreen(new pconnexion());					
					}
					return true;
                }
			};
			
			
			lescine[i].setFont(maPolice);
			add(lescine[i]);		
			
			// les infos avec le clique
			loca = lecture.readApiString("address", i);
			address[i] = new LabelField("Addresse : " + loca , Field.FOCUSABLE| Field.HIGHLIGHT_SELECT)
			{
                protected boolean navigationClick(int status,int time)
                {
                	float longitude = Float.parseFloat(lecture.readApiString("longitude", i));
                	float latitude = Float.parseFloat(lecture.readApiString("latitude", i));
                	invokeBlackBerryMaps(longitude,latitude, null, loca);
                    return true;
                }
       
            };
            add(address[i]);
           
            num = lecture.readApiString("phone", i);
        	tel[i] = new LabelField("Téléphone : "+ num, Field.FOCUSABLE| Field.HIGHLIGHT_SELECT)
			{
				protected boolean navigationClick(int status, int time) 
				{					
					PhoneArguments callArgs = new PhoneArguments(PhoneArguments.ARG_CALL, num);
					Invoke.invokeApplication(Invoke.APP_TYPE_PHONE, callArgs);
					return true;
				}

            };            
            add(tel[i]);  
            
            adr = lecture.readApiString("mail", i);
        	email[i] = new LabelField("Email : " + adr,Field.FOCUSABLE| Field.HIGHLIGHT_SELECT)
			{
                protected boolean navigationClick(int status,int time)
                {
                	 MessageArguments mArgs = new MessageArguments(MessageArguments.ARG_NEW,adr,"contact for cinema", "");
                	 extracted(mArgs);
                     //Browser.getDefaultSession().displayPage("wwww");
               	                   
                    return true;
                }

				private void extracted(MessageArguments mArgs) {
					Invoke.invokeApplication(Invoke.APP_TYPE_MESSAGES, mArgs);
				}
       
            };
            add(email[i]);     
			ouvert[i] = new RichTextField(lecture.readApiString("hours", i));
			add(ouvert[i]);
			
			add(new LabelField(""));
			}
		}

		// Bouton
		ButtonField bautres = new ButtonField("Supprimer les favoris", ButtonField.FIELD_RIGHT);
		bautres.setImage(ImageFactory.createImage(EncodedImage.getEncodedImageResource("stop.png")));

		bautres.setChangeListener(new FieldChangeListener() 
		{
			public void fieldChanged(Field field, int context)
			{
				try {
					FileConnection fconn = (FileConnection)Connector.open("file:///SDCard/hellofavo" + info.ID +".data");
					fconn.delete();
					fconn.close();
				} catch (IOException e) {				
					e.printStackTrace();
				}
				info.set_film("NULL");
				info.set_cine("NULL");
				UiApplication.getUiApplication().pushScreen(new MyScreen());
			}
				
		});
		
			add(bautres);
		
		
		
	}

}
