import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JPanel;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


/**
 * Die Klasse „Radiowecker“ modelliert einen Radiowecker mit verschiedenen Grundfunktionen eines typischen Radioweckers
 * 
 * @author Julian Regnet 
 * @version 12.04.2023
 */

class Radiowecker
{
    //----------------------------------------------------------
    // Klassen-Felder: keine vorhanden
    //----------------------------------------------------------
    //----------------------------------------------------------
    // Objekt-Felder
    //----------------------------------------------------------
    
    /** Gibt die Lautstärke des Radios an (Die Lautstaerke soll nur im Bereich von 0 bis 100 dB (Ganzzahl) liegen.) */
    byte lautstaerke;
    
    /** Diese Variable speichert das gewaehlte Frequenzband „FM“ (Frequenzmodulation) oder „AM“ (Amplitudenmodulation) ab. */
    String frequenz;
    
    /** Speichert die eingestellte Senderfrequenz,wie beispielsweise 101,1 ab. */
    double sender;
    
    /** Speichert die aktuelle Uhrzeit als Ganzzahl ab (z.B. 1730 für 17:30) */
    SimpleDateFormat format = new SimpleDateFormat("HHmm");
    int uhrzeit;
    
    /** Speichert die gewuenschte Weckzeit als Ganzzahl ab (z.B. 630 für 6:30) */
    int weckzeit;
    
    /** Beinhaltet true, wenn sich der Radiowecker im Alarmmodus befindet bzw. false, wenn der Alarm deaktiviert ist. */
    boolean alarm;
    
    //----------------------------------------------------------
    // Konstruktoren
    //----------------------------------------------------------
    
    /** Konstruktor 1: Elternmodus
     * @param newSender = neu einzustellender Sender
     * @param newFrequenzband = Wahl des Frequenzbandes (AM oder FM)
     * @param newUhrzeit = neu einzustellende Uhrzeit */
    Radiowecker(String newFrequenz, double newSender, int newWeckzeit)
    {
        lautstaerke = 100;
        frequenz = newFrequenz;
        sender = newSender;
        weckzeit = newWeckzeit;
        alarm = true;
    }

    /** Konstruktor 2: Werkseinstellung */
    Radiowecker()
    {
        lautstaerke = 0;
        frequenz = "FM";
        sender = 0.0;
        uhrzeit = 0;
        weckzeit = 0;
        alarm = false;
    }
    //----------------------------------------------------------
    // Klassen-Methoden: keine vorhanden
    //----------------------------------------------------------
    //----------------------------------------------------------
    // Objekt-Methoden
    //----------------------------------------------------------
    /** M1 Setter/Mutator: Die Weckuhrzeit manuell aendern
     * @param newWeckzeit = neue Weckuhrzeit des Radioweckers */
    void weckzeitEinstellen(int newWeckzeit)
    {
        weckzeit = newWeckzeit;
    }

    /** M2 Getter/Observer: Aktuelle Weckuhrzeit anzeigen lassen
     * @return weckzeit = Weckuhrzeit des Radioweckers */
    int weckzeitZeigen()
    {
        return weckzeit;
    }

    /** M3 Setter/Mutator: Die beiden Parameter Radiofrequenz
     * (FM/AM) Radiosender (z.B. 101,1) erfassen und einstellen
     * @param newSender = neu einzustellender Sender
     * @param newFrequenzband = Wahl des Frequenzbandes (AM oder FM) */
    void frequenzUndSenderFestlegen(String newFrequenz, double newSender)
    {
        frequenz = newFrequenz;
        sender = newSender;
    }

    /** M4 Setter/Mutator: Wenn der Alarmmodus aktiviert ist, soll
     * er automatisch deaktiviert werden und umgekehrt */
    void alarmmodusWechseln()
    {
        if(alarm == true)
        {
            alarm = false;
        }
        else
        {
            alarm = true;
        }
    }

    /** M5 Setter/Mutator: Reduziert die Lautstaerke jeweils um 1 dB
     * bis zur minimalen Lautstaerke von 0 dB */
    void lautstaerkeReduzieren()
    {
        if(lautstaerke == 0)
        {
            System.out.println("Lautstaerkeminimum erreicht!");
        }
        else
        {
            lautstaerke--;
        }
    }

    /** M6 Setter/Mutator: Erhoeht die Lautstaerke jeweils um 1 dB
     * bis zur maximalen Lautstaerke von 100 dB */
    void lautstaerkeErhoehen()
    {
        if(lautstaerke == 100)
        {
            System.out.println("Lautstaerkemaximum erreicht!");
        }
        else
        {
            lautstaerke++;
        }
    }

    /** M7 Setter/Mutator: Informationen der aktuellen Einstellungen des
     * Radioweckers wie in der folgenden Abbildung ausgeben. */
    void displayAnzeigen()
    {
        System.out.println("----------------------- ");
        System.out.println("Uhrzeit: " + uhrzeit);
        System.out.println("Weckzeit: " + weckzeit);
        System.out.println("Alarm an?: " + alarm);
        System.out.println("----------------------- ");
        System.out.println("Sender: " + sender);
        System.out.println("Frequenz: " + frequenz);
        System.out.println("Lautstaerke: " + lautstaerke);
        System.out.println("----------------------- ");
    }

    /** M8 Setter/Mutator: Aktualisiert die Uhrzeit und gibt den Alarm 10 Mal aus, wenn die Uhrzeit, die Weckzeit
    übereinstimmen und der Alarmmodus aktiviert ist.*/
    void wecken() throws javax.sound.sampled.UnsupportedAudioFileException,java.io.IOException,javax.sound.sampled.LineUnavailableException,InterruptedException
    {
        byte i = 1;

        while(uhrzeit != weckzeit){
            String uhrzeitString = format.format(new Date());
            uhrzeit = Integer.parseInt(uhrzeitString);
            Thread.sleep(1000);
        }
        if (uhrzeit == weckzeit && alarm == true)
        {
            while(i <= 10)
            {
                System.out.println(i + ") aufstehen");
                // Erstelle eine neue Clip-Instanz
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(new File("radiowecker.wav")));
                
                // Starte den Clip und warte, bis er fertig abgespielt wurde
                clip.start();
                Thread.sleep(1000);

                // Stoppe und schließe den Clip
                clip.stop();
                clip.close();
                i++;
            }
        }
    }
}
