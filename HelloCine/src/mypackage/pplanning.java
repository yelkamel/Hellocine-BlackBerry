package mypackage;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;

public class pplanning extends MainScreen
{
	LabelField[] salle = null;
	LabelField[] nb_place = null;
	LabelField[] sceance = null;

	public pplanning()
	{
		nb_place = new LabelField[20];
		sceance = new LabelField[20]; 
		salle = new LabelField[20]; 
    	// Fond d'ecran
    	Background maCouleur = BackgroundFactory.createSolidBackground(Color.BLACK);    	
        Bitmap fbbitmap = Bitmap.getBitmapResource("font.png");
        VerticalFieldManager monEcran = (VerticalFieldManager)getMainManager();
        Background fb = BackgroundFactory.createBitmapBackground(fbbitmap);
        monEcran.setBackground(maCouleur);
        monEcran.setBackground(fb);

       // TITRE
        BitmapField monTitre = new BitmapField(new Bitmap (0,0), FIELD_HCENTER);
        EncodedImage Titre = EncodedImage.getEncodedImageResource("Tplanning.png");        
        monTitre.setImage(Titre);
        add(monTitre);
             
        RichTextField name = new RichTextField("Bonjour " + info.ID, FIELD_HCENTER);
        add(name);
           	
		//chargement list de film		
		String url = "http://hellocine.3ie.fr/Webservices/get_planning/" + info.ID_user + "/" + info.token;		
		String m_xml="";
		HttpPost _httpPost=new HttpPost(url);
		m_xml=_httpPost.executeMethodGET();
		
		
		        
		if (!m_xml.equals("") || m_xml == null)
		{
			info.writeTextFile("file:///SDCard/helloresa.data", m_xml);
		}
		
		final lec lecture = new lec("file:///SDCard/helloresa.data");

		
		int y = 0;
		String b = "";
	
		
		for (; y < sceance.length ; y++)
		{		
	
				add(new LabelField(""));
				b = lecture.readApiString("room_name", y);			
				if (b == null || b.equals(""))
				{
					break;
				}
				salle[y]= new LabelField("à la : " + b); 			
				
				if (y > 0 && !salle[y - 1].toString().equals(salle[y].toString()))
				{
					add(salle[y]);
				}
				if (y == 0)
					add(salle[y]);
					
			sceance[y] = new LabelField(lec.convertTimestamp(lecture.readApiString("start_hour", y), 0)+" "+lec.convertTimestamp(lecture.readApiString("end_hour", y), 1));
			add(sceance[y]);
			
			
			int a = Integer.valueOf(lecture.readApiString("nb_places_max", y)).intValue() - Integer.valueOf(lecture.readApiString("nb_places_reservees", y)).intValue();;			
		    nb_place[y] = new LabelField("il reste : " + a +" place"); 
			add(nb_place[y]);
		
		}

		// the Police
		try {
			FontFamily typeCaracteres = FontFamily.forName("Arial");
			Font maPolice = typeCaracteres.getFont(Font.BOLD, 45);
			Font maPolice2 = typeCaracteres.getFont(Font.BOLD, 35);
			name.setFont(maPolice);
			for (int x = 0; x < y; x++)
				salle[x].setFont(maPolice2);
		}
		catch (ClassNotFoundException e) 
		{
			System.out.println(e.getMessage());
		}
	 
		 RichTextField name2 = new RichTextField("Merci d'avoir reserve le film avec Hellocine");
	        add(name2);
	}
}