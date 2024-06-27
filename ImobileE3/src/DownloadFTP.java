import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
public class DownloadFTP {

	 private static JTextField serverField;
	    private static JTextField userField;
	    private static JPasswordField passField;
	    private static JTextArea messageArea;

	    public static void main(String[] args) {
	        // Crearea ferestrei principale
	        JFrame frame = new JFrame("FTP Download");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(400, 400);

	        // Crearea panoului principal
	        JPanel panel = new JPanel();
	        panel.setLayout(new GridLayout(6, 2));

	        // Adresa IP server
	        panel.add(new JLabel("Adresa IP server:"));
	        serverField = new JTextField("10.100.155.62", 20);
	        panel.add(serverField);

	        // Nume utilizator
	        panel.add(new JLabel("Utilizator:"));
	        userField = new JTextField("", 20);
	        panel.add(userField);

	        // Parola
	        panel.add(new JLabel("Parola:"));
	        passField = new JPasswordField("", 20);
	        panel.add(passField);

	        // Butonul de descărcare
	        JButton downloadButton = new JButton("Descarcă");
	        downloadButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                downloadFiles();
	            }
	        });
	        panel.add(downloadButton);

	        // Butonul de închidere
	        JButton closeButton = new JButton("Close");
	        closeButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                System.exit(0);
	            }
	        });
	        panel.add(closeButton);

	        // Text area pentru mesaje
	        messageArea = new JTextArea();
	        messageArea.setEditable(false);
	        JScrollPane scrollPane = new JScrollPane(messageArea);

	        // Adăugarea panoului și a text area în fereastră
	        frame.getContentPane().add(panel, BorderLayout.NORTH);
	        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
	        frame.setVisible(true);
	    }

	    private static void downloadFiles() {
	        String server = serverField.getText();
	        String user = userField.getText();
	        String pass = new String(passField.getPassword());

	        // Fișierele de descărcat
	        String[] fileNames = {
	            "cadgen3constructii.zip",
	            "cadgen3imobile.zip",
	            "ConstructiiE3.zip",
	            "ImobileE3.zip"
	        };

	        FTPClient ftpClient = new FTPClient();

	        try {
	            // Conectare la server
	            ftpClient.connect(server);
	            boolean login = ftpClient.login(user, pass);

	            if (login) {
	                messageArea.append("Conectat la server.\n");

	                // Setarea tipului de fișiere pentru descărcare
	                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

	                for (String fileName : fileNames) {
	                    if (!fileName.isEmpty()) {
	                        // Pregătirea fluxului de ieșire pentru fișierul descărcat
	                        File downloadFile = new File(fileName);
	                        FileOutputStream fos = new FileOutputStream(downloadFile);

	                        // Descărcarea fișierului
	                        boolean success = ftpClient.retrieveFile(fileName, fos);
	                        fos.close();

	                        if (success) {
	                            messageArea.append("Fișierul " + fileName + " a fost descărcat cu succes.\n");
	                        } else {
	                            messageArea.append("Eroare la descărcarea fișierului " + fileName + ".\n");
	                        }
	                    }
	                }

	                // Deconectarea de la server
	                ftpClient.logout();
	                messageArea.append("Deconectat de la server.\n");
	            } else {
	                messageArea.append("Conectarea la server a eșuat.\n");
	            }
	        } catch (IOException ex) {
	            messageArea.append("Eroare: " + ex.getMessage() + "\n");
	            ex.printStackTrace();
	        } finally {
	            try {
	                if (ftpClient.isConnected()) {
	                    ftpClient.disconnect();
	                }
	            } catch (IOException ex) {
	                messageArea.append("Eroare la deconectare: " + ex.getMessage() + "\n");
	                ex.printStackTrace();
	            }
	        }
	    }
}








