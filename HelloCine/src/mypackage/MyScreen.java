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


/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */
public class MyScreen extends MainScreen 
{
   
    //Titre
    BitmapField monTitre;
    EncodedImage Titre;
    public static ButtonField bco;

    public void menuscreen()
    {
    	// Fond d'ecran
    	Background maCouleur = BackgroundFactory.createSolidBackground(Color.BLACK);    	
        Bitmap fbbitmap = Bitmap.getBitmapResource("font.png");
        VerticalFieldManager monEcran = (VerticalFieldManager)getMainManager();
        Background fb = BackgroundFactory.createBitmapBackground(fbbitmap);
        monEcran.setBackground(maCouleur);
        monEcran.setBackground(fb);
 
       

      
        if (info.connect)
        {
        	bco = new ButtonField("Ton Planning" + info.ID, ButtonField.FIELD_HCENTER);
        }
        else
        {
        	bco = new ButtonField("Connexion", ButtonField.FIELD_HCENTER);
        }
        
        ButtonField bfilm = new ButtonField("Film", ButtonField.FIELD_HCENTER);
        
        
        
        //image pour bouton       
        bfilm.setImage(ImageFactory.createImage(EncodedImage.getEncodedImageResource("film.png")));       
        bco.setImage(ImageFactory.createImage(EncodedImage.getEncodedImageResource("utili.png")));
        
       
        bfilm.setChangeListener(new FieldChangeListener() {
            public void fieldChanged(Field field, int context) 
            {  
                UiApplication.getUiApplication().pushScreen(new pfilm(0));       	
            	         	
            }});
        add(bfilm); 
        
        ButtonField bcine = new ButtonField("Cinema", ButtonField.FIELD_HCENTER);
        bcine.setImage(ImageFactory.createImage(EncodedImage.getEncodedImageResource("cine.png")));
        bcine.setChangeListener(new FieldChangeListener() {
            public void fieldChanged(Field field, int context) 
            {
            	UiApplication.getUiApplication().pushScreen(new pcine(0));
                        	
            }});
        add(bcine); 
        
        bco.setChangeListener(new FieldChangeListener() {
            public void fieldChanged(Field field, int context) 
            {
            	if (info.connect)
            		UiApplication.getUiApplication().pushScreen(new pplanning());
            	else
            		UiApplication.getUiApplication().pushScreen(new pconnexion());
             	          	
            }});       
        add(bco); 
        
        ButtonField bcinefavo = new ButtonField("Tes Cinemas Favoris", ButtonField.FIELD_HCENTER);
        bcinefavo.setImage(ImageFactory.createImage(EncodedImage.getEncodedImageResource("utili.png")));
        bcinefavo.setChangeListener(new FieldChangeListener()
        {
            public void fieldChanged(Field field, int context) 
            {
            	String favfile = info.readTextFile("file:///SDCard/hellofavo" + info.ID + ".data");
         			if (favfile == null || favfile.equals(""))
        			{
        				Dialog.alert("Vous n'avez aucun film en favorie !");        				
        			}
         			else
         			{    	
         				UiApplication.getUiApplication().pushScreen(new pcinefavo(0));
         			}
            }});
        if (info.connect)
        {        	
             add(bcinefavo);
        }    
          
    	
    }
    
    public MyScreen()
    {   
        Titre = EncodedImage.getEncodedImageResource("logoHellocine.png");
        monTitre = new BitmapField(new Bitmap (0,0), FIELD_HCENTER);
        monTitre.setImage(Titre);  
        add(monTitre);     
               
      	menuscreen();
      	

   
     }
    
    public boolean onClose()
    {
        Dialog.alert("Au revoir et à bientot !");
        System.exit(0);
        return true;
    }
   
}