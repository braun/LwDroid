package lw.droid.http;
/**
 * callback rozhrani metody Usync ComEngine
 * @author Standa
 *
 */
public interface SyncProcessor {

  /**
   * Metoda zajišťující update textu stavu protokolu AFL-Prot
   * @param state stav komunikacniho cyklu
   */
    void UpdateSyncProgress(ProgressState state);
  
    /**
     * Metoda zajišťující zobrazení textu obecné zprávy
     * @param message texdt k zobrazeni
     */
    void Message(String message);

	void setAlertConnectionLost(boolean lost);

	void clearMessage();
}