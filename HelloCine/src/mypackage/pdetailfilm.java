package mypackage;

import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;


public class pdetailfilm extends MainScreen {

	LabelField[] salle = null;
	LabelField[] nb_place = null;
	LabelField[] sceance = null;
	String[] tab = null;
	String[] sceanceID = null;
	lec lecture = null;

	public pdetailfilm()
	{
		sceance = new LabelField[100];
		tab = new String[sceance.length];
		nb_place = new LabelField[sceance.length];
		salle = new LabelField[sceance.length];
		sceanceID = new String[sceance.length];
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
		EncodedImage Titre = EncodedImage
				.getEncodedImageResource("logoHellocine.png");
		monTitre.setImage(Titre);
		add(monTitre);

		int y = 0;

		RichTextField name = new RichTextField("présente le film : \n"
				+ info.film);
		add(name);

		// chargement list de film
		String url = "http://hellocine.3ie.fr/Webservices/get_film/"
				+ info.IDfilm;
		String m_xml = "";
		HttpPost _httpPost = new HttpPost(url);
		m_xml = _httpPost.executeMethodGET();

		if (!m_xml.equals("") || m_xml == null)
		{
			try {
				FileConnection fconn = (FileConnection)Connector.open("file:///SDCard/hellod.data");
				fconn.delete();
				fconn.close();
			} catch (IOException e) {				
				e.printStackTrace();
			} 
			
			info.writeTextFile("file:///SDCard/hellod.data", m_xml);
		}

		lecture = new lec("file:///SDCard/hellod.data");
		
		if (lecture == null)
			Dialog.alert("erreur : le document est null.");

		// check info film ID
		RichTextField duree = new RichTextField("un film de " + info.duree
				+ "\n");
		add(duree);
		String b = "";
		
		for (; y < sceance.length; y++)
		{
			add(new LabelField(""));
			b = lecture.readApiString("room_name", y);
			if (b == null || b.equals("")) {
				break;
			}
			salle[y] = new LabelField("à la : " + b);
			if (y > 0 && !salle[y - 1].toString().equals(salle[y].toString()))
			{
				add(salle[y]);
			}
			if (y == 0)
				add(salle[y]);
			sceanceID[y] = lecture.readApiString("session_id", y);

			HorizontalFieldManager hfm = new HorizontalFieldManager();
			add(hfm);
			
			  tab[y] ="" + new Integer(Integer.valueOf(lecture.readApiString("nb_places_max", y)).intValue()
						- Integer.valueOf(lecture.readApiString("nb_places_reservees", y)).intValue());
			sceance[y] = new LabelField(lec.convertTimestamp(lecture.readApiString("start_hour", y), 0)	+ lec.convertTimestamp(lecture.readApiString("end_hour", y), 1)+"   ");
			hfm.add(sceance[y]);

			final int sc = y;
			
			  ButtonField bresa = new ButtonField(" Réserver");			
			  bresa.setChangeListener(new FieldChangeListener()
			  {
				  public  void fieldChanged(Field field, int context)
			  {
				  info.set_sceanceID(sceanceID[sc]);
				  info.set_sceance(sceance[sc].toString());
				  info.set_nb_place(nb_place[sc].toString());
				  info.set_nb_place_int(Integer.parseInt(tab[sc]));
				  info.set_cine(salle[sc].toString().substring(15));
				  UiApplication.getUiApplication().pushScreen(new presa());			  
			  }});
			  
			  
			  hfm.add(bresa);		
			
			nb_place[y] = new LabelField("il reste " + tab[y] + " place");
			add(nb_place[y]); 

		}

		// the Police
		try
		{
			FontFamily typeCaracteres = FontFamily.forName("Arial");
			Font maPolice = typeCaracteres.getFont(Font.BOLD, 45);
			Font maPolice2 = typeCaracteres.getFont(Font.BOLD, 35);
			name.setFont(maPolice);
			for (int x = 0; x < y; x++)
				salle[x].setFont(maPolice2);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

	}

}
