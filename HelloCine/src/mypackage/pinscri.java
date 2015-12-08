package mypackage;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.image.ImageFactory;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.UiApplication;

public class pinscri extends MainScreen {

	// msg d'erreur
	RichTextField msg = new RichTextField("");

	boolean co = false;
	EditField id = new EditField("Identifant :\n", "", 32, EditField.FILTER_FILENAME);
	PasswordEditField mdp = new PasswordEditField("Mot de passe :\n", "");
	EditField addr = new EditField("Adresse Email :\n", "", 32,	EditField.FILTER_EMAIL);
	PasswordEditField mdp2 = new PasswordEditField("Répéter le mot de passe :\n", "");

	public pinscri() {
		// Fond d'ecran
		Background maCouleur = BackgroundFactory.createSolidBackground(Color.BLACK);
		Bitmap fbbitmap = Bitmap.getBitmapResource("font.png");
		VerticalFieldManager monEcran = (VerticalFieldManager) getMainManager();
		Background fb = BackgroundFactory.createBitmapBackground(fbbitmap);
		monEcran.setBackground(maCouleur);
		monEcran.setBackground(fb);

		// TITRE
		BitmapField monTitre = new BitmapField(new Bitmap(0, 0), FIELD_HCENTER);
		EncodedImage Titre = EncodedImage.getEncodedImageResource("connexion.png");
		monTitre.setImage(Titre);
		add(monTitre);



		// AbsoluteFieldManager monManager = new AbsoluteFieldManager() ;
		mdp.setMaxSize(32);

		// Bouton
		ButtonField binscri = new ButtonField("Valider", FIELD_HCENTER);
		binscri.setImage(ImageFactory.createImage(EncodedImage
				.getEncodedImageResource("ok.png")));

		binscri.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				
				String url = "http://hellocine.3ie.fr/Webservices/register/"+ id.toString() + "/" + mdp.toString();		
				String m_xml="";
				HttpPost _httpPost=new HttpPost(url);
				m_xml=_httpPost.executeMethodGET();
				
				if (m_xml.indexOf("success") > 0 && mdp2.getText().equals(mdp.getText()))
				{
					info.set_token(m_xml.substring(m_xml.indexOf("TA[") + 3, m_xml.indexOf("]]></token>")));
					info.set_ID_user(m_xml.substring(m_xml.indexOf("<id>") + 4, m_xml.indexOf("</id>")));
					info.ID = id.toString();
					info.MDP = mdp.toString();
					info.set_connect(true);
					// getMainManager().deleteAll();
					UiApplication.getUiApplication().pushScreen(new MyScreen());
				} else 
				{
					msg.setText("mot de passe ou identifiant " + id.toString() + " incorrect\n");
				}
			}
		});
		
		
		// the Police
		try 
		{
			FontFamily typeCaracteres = FontFamily.forName("Arial");
			Font maPolice = typeCaracteres.getFont(Font.BOLD, 40);
			mdp.setFont(maPolice);
			id.setFont(maPolice);
			msg.setFont(maPolice);
			mdp2.setFont(maPolice);
			addr.setFont(maPolice);
		} 
		catch (ClassNotFoundException e)
		{
			System.out.println(e.getMessage());
		}


		add(msg);
		add(id);
		add(mdp);
		add(mdp2);
		add(addr);
		add(binscri);

	}

}
