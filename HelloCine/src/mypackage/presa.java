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

public class presa extends MainScreen
{

	public presa()
	{
		// Fond d'ecran
		Background maCouleur = BackgroundFactory.createSolidBackground(Color.BLACK);
		Bitmap fbbitmap = Bitmap.getBitmapResource("font.png");
		VerticalFieldManager monEcran = (VerticalFieldManager) getMainManager();
		Background fb = BackgroundFactory.createBitmapBackground(fbbitmap);
		monEcran.setBackground(maCouleur);
		monEcran.setBackground(fb);

		// TITRE
		BitmapField monTitre = new BitmapField(new Bitmap(0, 0), FIELD_HCENTER);
		EncodedImage Titre = EncodedImage.getEncodedImageResource("logoHellocine.png");
		monTitre.setImage(Titre);
		add(monTitre);

		RichTextField msg = new RichTextField("");

		String url = "http://hellocine.3ie.fr/Webservices/book_session/"+ info.sceanceID +"/" +info.ID_user +"/" + info.token;	
		String m_xml="";
		HttpPost _httpPost=new HttpPost(url);
		m_xml=_httpPost.executeMethodGET();
		
		if (m_xml.indexOf("success") > 0 && info.nb_placeint > 0)
		{
			msg.setText("vous avez réserver \n" + info.film + " " + info.sceance +" au " + info.cine + "\n");

		}
		else
		{
			msg.setText("Désoler il ne reste plus de place pour\n" + info.film + " " + info.sceance +"\n");
			Dialog.alert("Désoler il ne reste plus de place pour\n" + info.film + " " + info.sceance + "\n");
		}
		add(msg);

	
		// Bouton inscription
		ButtonField bfavo = new ButtonField("Ajouter le cinema au favori", FIELD_LEFT);
		bfavo.setImage(ImageFactory.createImage(EncodedImage
				.getEncodedImageResource("inscription.png")));

		bfavo.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				info.writeTextFile(	"file:///SDCard/hellofavo" + info.ID + ".data",	info.readTextFile("file:///SDCard/hellofavo" + info.ID + ".data") + info.cine + " ");
				info.set_cine("NULL");
				Dialog.alert("Cinema ajouter aux favoris");
			}
		});

		add(bfavo);

		// Bouton select film
		ButtonField bautre = new ButtonField("Réserver un autre film   ", ButtonField.FIELD_LEFT);
		bautre.setImage(ImageFactory.createImage(EncodedImage.getEncodedImageResource("question.png")));

		bautre.setChangeListener(new FieldChangeListener() 
		{
			public void fieldChanged(Field field, int context)
			{
				info.set_film("NULL");
				UiApplication.getUiApplication().pushScreen(new pfilm(0));				
			}
		});
		bautre.setLabelLeft();
		add(bautre);
		
		
		// Bouton select film
		ButtonField bautcine = new ButtonField("Voir les autres cinema  ", ButtonField.FIELD_LEFT);
		bautcine.setImage(ImageFactory.createImage(EncodedImage.getEncodedImageResource("question.png")));

		bautcine.setChangeListener(new FieldChangeListener() 
		{
			public void fieldChanged(Field field, int context)
			{
				info.set_cine("NULL");
				UiApplication.getUiApplication().pushScreen(new pcine(0));				
			}
		});
		bautcine.setLabelLeft();
		add(bautcine);

		// Bouton return menu
		ButtonField bretour = new ButtonField("Retour a l'acceil", ButtonField.FIELD_LEFT);
		bretour.setImage(ImageFactory.createImage(EncodedImage.getEncodedImageResource("retour.png")));

		bretour.setChangeListener(new FieldChangeListener()
		{
			public void fieldChanged(Field field, int context)
			{
				info.set_film("NULL");
				info.set_cine("NULL");
				UiApplication.getUiApplication().pushScreen(new MyScreen());
			}
		});

		add(bretour);
	}
}
