import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class main {
    private static final String prenotazioni = "resources/prenotazioni.csv";
    private static final String viaggi = "resources/viaggi.csv";
    private static final String utenti = "resources/utenti.csv";
    private static final String DELIMITER = ";";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command;
        while (true) {
            System.out.println("1: visualizzare i viaggi");
            System.out.println("2: prenotare un viaggio");
            System.out.println("3: disdire prenotazione");
            System.out.println("4: aggiungere nuovo utente");
            System.out.println("5: esportare file");
            System.out.println("0: uscire");
            command = scanner.nextLine();
            switch (command) {
                case "1":
                    visualizzaViaggi();
                    break;
                case "2":
                    prenotaViaggio();
                    break;
                case "3":
                    disdirePrenotazione();
                    break;
                case "4":
                    aggiungiNuovoUtente();
                    break;
                case "5":
                    esportaFile();
                    break;
                case "0":
                    System.out.println("Uscita dall'applicazione");
                    return;
                default:
                    System.out.println("Comando non valido");
            }
        }
    }
    private static void visualizzaViaggi() {
        try (BufferedReader reader = new BufferedReader(new FileReader(viaggi))) {
        String line;
        while ((line = reader.readLine()) != null) {
            StringTokenizer tokenizer = new StringTokenizer(line, DELIMITER);
            String idViaggio = tokenizer.nextToken();
            String dataViaggio = tokenizer.nextToken();
            String durataViaggio = tokenizer.nextToken();
            String destinazioneViaggioA = tokenizer.nextToken();
            String destinazioneViaggioB = tokenizer.nextToken();
            String disponibileViaggio = tokenizer.nextToken();
            System.out.println("ID Viaggio: " + idViaggio);
            System.out.println("Data Viaggio: " + dataViaggio);
            System.out.println("Durata Viaggio in ore: " + durataViaggio + ("h"));
            System.out.println("Destinazione A-R: " + destinazioneViaggioA + destinazioneViaggioB);
            System.out.println("Disponibile: " + disponibileViaggio);
            System.out.println();
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void prenotaViaggio() {
        try (BufferedReader reader = new BufferedReader(new FileReader(viaggi))) {
            List<String> availableViaggi = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(DELIMITER);
                if (parts.length >= 6 && "SI".equals(parts[5])) {
                    availableViaggi.add(line);
                }
            }
            if (availableViaggi.isEmpty()) {
                System.out.println("Nessun viaggio disponibile per la prenotazione.");
                return;
            }
            System.out.println("Viaggi disponibili:");
            for (String viaggio : availableViaggi) {
                System.out.println(viaggio);
            }
            Scanner scanner = new Scanner(System.in);
            System.out.println("Seleziona il viaggio inserendo l'ID numerico:");
            String selectedViaggioId = scanner.nextLine();
            System.out.println("Inserisci il tuo numero ID:");
            String userId = scanner.nextLine();
            System.out.println("Il viaggio è stato prenotato con successo.");
        } catch (IOException e) {
            e.printStackTrace();
        }}
    private static void disdirePrenotazione() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Prenotazioni disponibili:");
            List<String[]> prenotazioniData = caricaDati(prenotazioni);
            for (String[] prenotazione : prenotazioniData) {
                System.out.println("ID Prenotazione: " + prenotazione[0] + ", Data Prenotazione: " + prenotazione[1]);
            }
            System.out.println("Inserisci l'ID della prenotazione da disdire:");
            String prenotazioneId = scanner.nextLine();
            boolean found = false;
            for (String[] prenotazione : prenotazioniData) {
                if (prenotazione[0].equals(prenotazioneId)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                List<String[]> newData = new ArrayList<>();
                for (String[] prenotazione : prenotazioniData) {
                    if (!prenotazione[0].equals(prenotazioneId)) {
                        newData.add(prenotazione);
                    }
                }
                salvaDati(prenotazioni, newData);
                System.out.println("Prenotazione disdetta con successo.");
            } else {
                System.out.println("Prenotazione non trovata.");
            }
        }
        private static void salvaDati(String filePath, List<String[]> newData) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String[] data : newData) {
                    writer.write(String.join(DELIMITER, data) + System.lineSeparator());
                }
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    private static void aggiungiNuovoUtente() {
        Scanner input = new Scanner(System.in);
        String filePath = utenti;
        String tempFile = "temp.csv";
        String line;
        String[] parts;
        String id;
        String nomeUtente;
        String cognomeUtente;
        String dataUtente;
        String indirizzo;
        String idPersonale;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            while ((line = reader.readLine()) != null) {
                writer.write(line + System.lineSeparator());
            }
            System.out.println("Inserisci i dati per la nuova categoria:");
            System.out.print("ID: ");
            id = input.nextLine();
            System.out.print("Nome: ");
            nomeUtente = input.nextLine();
            System.out.print("Cognome: ");
            cognomeUtente = input.nextLine();
            System.out.print("Data di nascita: ");
            dataUtente = input.nextLine();
            System.out.print("Indirizzo: ");
            indirizzo = input.nextLine();
            System.out.print("Numero ID personale: ");
            idPersonale = input.nextLine();
            writer.write(id + DELIMITER + nomeUtente + DELIMITER + cognomeUtente + DELIMITER + dataUtente + DELIMITER + indirizzo + DELIMITER + idPersonale + DELIMITER + System.lineSeparator());
            writer.flush();
            System.out.println("Dati salvati con successo.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            File file = new File(filePath);
            File temp = new File(tempFile);
            temp.renameTo(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void esportaFile() {
        String filePath = viaggi;
        String exportFilePath = "dati_viaggi.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
            BufferedWriter writer = new BufferedWriter(new FileWriter(exportFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(DELIMITER);
                writer.write(line + System.lineSeparator());
            }
            System.out.println("Esportazione del file completata con successo!");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    private static void collegaDati() {
        List<String[]> prenotazioniData = caricaDati(prenotazioni);
        List<String[]> viaggiData = caricaDati(viaggi);
        List<String[]> utentiData = caricaDati(utenti);
    
        String exportFilePath = "dati_connessi.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(exportFilePath))) {
            writer.write("Prenotazione ID, Nome Viaggio, Cognome Utente, Data Prenotazione" + System.lineSeparator());
            for (String[] prenotazione : prenotazioniData) {
                String idPrenotazione = prenotazione[0];
                String nomeViaggio = "";
                String cognomeUtente = "";
                for (String[] viaggio : viaggiData) {
                    if (viaggio[0].equals(idPrenotazione)) {
                        nomeViaggio = viaggio[1];
                        break;
                    }
                }
                for (String[] utente : utentiData) {
                    if (utente[0].equals(idPrenotazione)) {
                        cognomeUtente = utente[2];
                        break;
                    }
                }
                writer.write(idPrenotazione + "," + nomeViaggio + "," + cognomeUtente + "," + prenotazione[1] + System.lineSeparator());
            }
            writer.flush();
            System.out.println("Dati collegati salvati con successo in " + exportFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String[]> caricaDati(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(DELIMITER);
                data.add(parts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }



}
