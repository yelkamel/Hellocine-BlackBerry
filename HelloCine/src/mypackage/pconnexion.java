package mypackage;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.container.HorizontalFieldManager;
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

public class pconnexion extends MainScreen 
{

	// msg d'erreur
	RichTextField msg = new RichTextField("");

	boolean co = false;
	EditField id = new EditField("Identifant :\n", "", 32,EditField.FIELD_HCENTER);
	PasswordEditField mdp = new PasswordEditField("Mot de passe :\n", "");

	public class Bouton extends ButtonField
	{

		public Bouton(String nom)
		{
			super(nom);
		}

		public int getPreferredWidth() 
		{
			return (Display.getWidth() / 2 - 35);
		}

	}

	public pconnexion()
	{
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
				.getEncodedImageResource("connexion.png");
		monTitre.setImage(Titre);
		add(monTitre);
		mdp.setMaxSize(32);

	
		HorizontalFieldManager cont = new HorizontalFieldManager(FIELD_HCENTER);
		// Bouton
		Bouton bcine = new Bouton("Connexion");
		bcine.setImage(ImageFactory.createImage(EncodedImage
				.getEncodedImageResource("ok.png")));
		bcine.setLabelLeft();
		// Bouton inscription
		Bouton binscri = new Bouton("Inscription");
		binscri.setImage(ImageFactory.createImage(EncodedImage.getEncodedImageResource("inscription.png")));

		bcine.setChangeListener(new FieldChangeListener()
		{
			public void fieldChanged(Field field, int context)
			{
				String url = "http://hellocine.3ie.fr/Webservices/connection/"+ id.toString() + "/" + mdp.toString();		
				String m_xml="";
				HttpPost _httpPost=new HttpPost(url);
				m_xml=_httpPost.executeMethodGET();
				
				if (m_xml.indexOf("success") > 0) 
				{			
					info.set_token(m_xml.substring(m_xml.indexOf("TA[") + 3, m_xml.indexOf("]]></token>")));
					info.set_ID_user(m_xml.substring(m_xml.indexOf("<id>") + 4, m_xml.indexOf("</id>")));
					info.ID = id.toString();
					info.MDP = mdp.toString();					
					info.set_connect(true);					
					UiApplication.getUiApplication().pushScreen(new MyScreen());					
				}
				else 
				{
					Dialog.alert(" Attention ! mot de passe ou identifiant incorrect ");
					msg.setText(" Aucun utilisateur connu a ce nom ou mot de passe invalide\n");
				
				}

			}
		});

		binscri.setChangeListener(new FieldChangeListener()
		{
			public void fieldChanged(Field field, int context)
			{
				UiApplication.getUiApplication().pushScreen(new pinscri());
			}
		});
		
		// the Police
		try 
		{
			FontFamily typeCaracteres = FontFamily.forName("Arial");
			Font maPolice = typeCaracteres.getFont(Font.BOLD, 40);
			mdp.setFont(maPolice);
			id.setFont(maPolice);
		//	msg.setFont(maPolice);
		} 
		catch (ClassNotFoundException e)
		{
			System.out.println(e.getMessage());
		}

		add(msg);
		add(id);
		add(mdp);		
		add(cont);

		cont.add(binscri);
		cont.add(bcine);

	}
}
