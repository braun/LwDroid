package lw.droid.http;


/**
 * Enumerace stavů protokolu AFL-Prot
 * @author Standa
 *
 */
public enum ProgressState 
{
	
   
        /**
         *  Klid komunikace
         */
        DONE,
      
        /**
         * Oslovování serveru
         */
        ASKING,
        
        /**
         * Zprac. odpovědi
         */
        PROCESSING,
       
        /**
         *  Vypršel časový limit pro odpověď
         */
        TIMEOUT,
      
        /**
         * Příjem dat
         */
        RECEIVING,
       
        /**
         * Přihlášení bylo úspěšné
         */
        LOGINOK,
       
        /**
         * Přihlášení bylo neúspěšné
         */
        LOGINERR,
       
        /**
         *  Odesílání dat
         */
        SENDDATA,
        /**
         *  Odesílání dat
         */
        SENDSTATUS,
        
        /**
         *  Chyba při odesílání dat
         */
        ERRORINSEND,
        
        /**
         * Update bodů zájmu v navig. sw.
         */
        UPDATEPOI,
        
        /**
         * Update obrázků v navig. sw
         */
        UPDATEBMP
    
}
