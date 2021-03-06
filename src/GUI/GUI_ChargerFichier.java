package GUI;

import javax.swing.*;

import Fichier_circuit.ChargerFichier;

import java.awt.*;
import java.awt.event.*;

public class GUI_ChargerFichier extends GUI
{	
	private JTextField champ_saisie_url;
	
	public GUI_ChargerFichier()
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		double h = initialiser_hauteur(toolkit);
		double l = initialiser_largeur(toolkit);
		
		this.setTitle("ElectronicCircuits - Charger un circuit depuis un fichier");		
		this.setHauteur((int)h);
		this.setLargeur((int)l);
		this.setSize(this.getLargeur(), this.getHauteur());
		this.initialiserLocation(toolkit);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.initialiser_charger_circuit();
		this.setVisible(true);
	}
	
	public void initialiser_charger_circuit()
	{
		//creation du panneau principal
		JPanel panneau = new JPanel();
		panneau.setBackground(Color.BLACK);
				
		//creation du grid layout du sous panneau
		GridLayout conteneur_boutons = new GridLayout(5, 1); 
		conteneur_boutons.setVgap(30);
		conteneur_boutons.setHgap(10);
		
		//affectation du grid layout au sous panneau
		JPanel sous_panneau_1 = new JPanel(conteneur_boutons);
		sous_panneau_1.setBackground(Color.BLACK);
		
		//creation du sous-titre
		JLabel sous_titre = new JLabel("CHARGER UN CIRCUIT DEPUIS UN FICHIER");
		sous_titre.setFont(new Font(Font.DIALOG, Font.BOLD, 30));	
		sous_titre.setForeground(Color.GREEN);
		sous_titre.setHorizontalAlignment(SwingConstants.CENTER);
		
		//creation du label d'indication
		JLabel label_champ_saisie = new JLabel("Entrez le chemin du fichier circuit (.txt) que vous souhaitez charger");
		label_champ_saisie.setForeground(Color.GREEN);
		label_champ_saisie.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		
		//creation du champ de saisie pour indiquer le chemin du fichier circuit � charger
		champ_saisie_url = new JTextField();
		champ_saisie_url.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		champ_saisie_url.setForeground(Color.WHITE);
		champ_saisie_url.setBackground(Color.WHITE);
		champ_saisie_url.setOpaque(false);
		champ_saisie_url.setHorizontalAlignment(SwingConstants.CENTER);
				
		//creation des boutons
		JButton bouton_1 = new JButton("CHARGER LE FICHIER CIRCUIT");
		bouton_1.setBackground(Color.GREEN);
		bouton_1.setBorderPainted(false);
		bouton_1.setFocusPainted(false);
		JButton bouton_2 = new JButton("REVENIR AU MENU PRINCIPAL");
		bouton_2.setBackground(Color.ORANGE);
		bouton_2.setBorderPainted(false);
		bouton_2.setFocusPainted(false);
		
		//boutons invisibles
		JButton bouton_i0 = new JButton("");
		bouton_i0.setVisible(false);
		JButton bouton_i1 = new JButton("");
		bouton_i1.setVisible(false);
		JButton bouton_i2 = new JButton("");
		bouton_i2.setVisible(false);	
		JButton bouton_i3 = new JButton("");
		bouton_i3.setVisible(false);
				
		//creation paneau du label + champ de saisie
		JPanel ps_items = new JPanel();
		ps_items.setBackground(Color.BLACK);
		ps_items.setLayout(new GridLayout(3, 1));
		ps_items.add(bouton_i0);
		ps_items.add(label_champ_saisie);
		ps_items.add(champ_saisie_url);	
		
		//creation panneau actions
		JPanel pa = new JPanel(new GridLayout(4, 1));
		pa.setBackground(Color.BLACK);
		pa.add(bouton_i2);
		pa.add(bouton_1);
		pa.add(bouton_i3);
		pa.add(bouton_2);
		
		//ajout des boutons au panneau
		sous_panneau_1.add(bouton_i1);
		sous_panneau_1.add(sous_titre);	
		sous_panneau_1.add(ps_items);
		sous_panneau_1.add(pa);
		
		//ajout des �v�nements aux boutons
		bouton_1.addActionListener(new ChargerFichierCircuitListener());
		bouton_2.addActionListener(new RevenirMenuPrincipalListener());
		
		//ajout du sous panneau au panneau principal
		panneau.add(sous_panneau_1);
		
		//ajout du panneau principal � la fenetre
		this.setContentPane(panneau);	
	}
	
	class ChargerFichierCircuitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			String url = champ_saisie_url.getText();
			String nom = "";
			String contenu = "";
			if(!url.equals("") && url != null)
			{	
				if(verifier_espaces(url))
				{
					if(url.contains("\\"))
					{
						url = champ_saisie_url.getText();
						url = supprimer_espaces(url);
						if(url.contains("\\"))
						{
							nom = url.substring(url.lastIndexOf("\\"));
							nom = nom.substring(1, nom.length() - 4);
						}
						ChargerFichier c = new ChargerFichier(url);
						contenu = c.getFichier_circuit().getContenu();
						//on vire les "null" apparents dans le fichier si encod� en UTF-8
						contenu = contenu.replaceAll("null", "");
						contenu = contenu.substring(0, contenu.length() - 2);
						if(contenu.contains("ERREUR"))
						{
							JOptionPane erreur = new JOptionPane();
							erreur.showMessageDialog(null, "Le fichier n'a pas �t� trouv� !", "Erreur lors du chargement de fichier", JOptionPane.ERROR_MESSAGE);
						}
						else
						{		
							JOptionPane succes = new JOptionPane();
							succes.showMessageDialog(null, "Votre fichier circuit a bien �t� charg� !", "Succ�s de chargement", JOptionPane.INFORMATION_MESSAGE);
							dispose();
							new GUI_AfficherFichierCharge(url, nom, contenu);
						}
					}
					else
					{
						JOptionPane erreur = new JOptionPane();
						erreur.showMessageDialog(null, "Le format de l'URL de fichier saisie est incorrect !", "Erreur lors de la saisie de chemin", JOptionPane.ERROR_MESSAGE);
					}
				}
				else
				{
					JOptionPane erreur = new JOptionPane();
					erreur.showMessageDialog(null, "Vous n'avez saisi aucune URL de fichier !", "Erreur lors du chargement de fichier", JOptionPane.ERROR_MESSAGE);
				}				
			}
			else
			{
				JOptionPane erreur = new JOptionPane();
				erreur.showMessageDialog(null, "Vous n'avez saisi aucune URL de fichier !", "Erreur lors du chargement de fichier", JOptionPane.ERROR_MESSAGE);
			}			
		}
		
		public boolean verifier_espaces(String url)
		{
			String u = new String(url);
			for(int i = 0; i < u.length(); i++)
				if(!(u.charAt(i) + "").equals(" "))
					return true;
			return false;
		}
		
		public String supprimer_espaces(String url)
		{
			String str = new String();
			for(int i = 0; i < url.length(); i++)
				if(!((url.charAt(i) + "").equals(" ")))
					str += url.charAt(i);
			return str;
		}
	}
}