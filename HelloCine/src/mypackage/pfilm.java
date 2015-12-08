package mypackage;


import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.image.ImageFactory;

public class pfilm extends MainScreen
{
	RadioButtonGroup monGroupe = new RadioButtonGroup();
	RadioButtonField[] lesfilm;
	ObjectChoiceField maListe[];
	RichTextField[] listcine;
	static int nb;
	public pfilm(int numero)
	{
		final String listfilm[];
		listfilm = new String[9];
		final String listduree[];
		listduree = new String[9];

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
		EncodedImage Titre = EncodedImage.getEncodedImageResource("Tfilm.png");
		monTitre.setImage(Titre);
		add(monTitre);

		
		//chargement list de film		
		String url = "http://hellocine.3ie.fr/Webservices/get_films";		
		String m_xml="";
		HttpPost _httpPost=new HttpPost(url);
		m_xml=_httpPost.executeMethodGET();
		
		if (!m_xml.equals(""))
		{
			info.writeTextFile("file:///SDCard/hellofilm.data", m_xml);
		}
		lec lecture = new lec("file:///SDCard/hellofilm.data");
		
	
		String a = "ok";
	    nb = numero;
	    int y = 0;
		for ( ; y < listfilm.length; y++)
    	{			 
			 a = lecture.readApiString("film_name", nb + y);
			 if (a == null || a.equals(""))
			 {				 
				 break;
			 }
			 listduree[y] = lecture.readApiString("duration", nb + y);
			 listfilm[y] = a;
		}
		
		nb = nb + y;
			
		
		// LIST graphique
		lesfilm = new RadioButtonField[listfilm.length];		
		for (int i = 0;  i < y ; i++) 
		{
			lesfilm[i] = new RadioButtonField(" " + listfilm[i] + "\nfilm de"
					+ listduree[i], monGroupe, false);
			add(lesfilm[i]);
		}
			

		// Bouton
		ButtonField bfilm = new ButtonField("Suivant", ButtonField.FIELD_RIGHT);
		bfilm.setImage(ImageFactory.createImage(EncodedImage
				.getEncodedImageResource("ok.png")));

		bfilm.setChangeListener(new FieldChangeListener()
		{
			public void fieldChanged(Field field, int context) 
			{
				
				if (info.connect) 
				{
					
					for (int id = 0; id < listfilm.length; id++) 
					{
						if (lesfilm[id].isSelected())
						{
							info.set_IDfilm(id);
							info.set_film(listfilm[id]);
							info.set_duree(listduree[id]);	
						}
						
					}
					if (info.IDfilm != -1)
					{
						UiApplication.getUiApplication().pushScreen(new pdetailfilm());
					}
	
				} 
				else
				{
					UiApplication.getUiApplication().pushScreen(new pconnexion());
					
				}

			}
		});

		add(bfilm);
		// Bouton
				ButtonField baut = new ButtonField("voir les autres films", ButtonField.FIELD_RIGHT);
				baut.setImage(ImageFactory.createImage(EncodedImage
						.getEncodedImageResource("autres.png")));

				baut.setChangeListener(new FieldChangeListener()
				{
					public void fieldChanged(Field field, int context) 
					{
							UiApplication.getUiApplication().pushScreen(new pfilm(nb));
					}
				});
				if (y == listfilm.length)
					add(baut);
	}

}
