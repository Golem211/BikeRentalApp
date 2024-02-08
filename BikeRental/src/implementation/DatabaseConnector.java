package implementation;

//import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class DatabaseConnector {

	private String url = "jdbc:sqlserver://DESKTOP-0O01498\\SQLEXPRESS;encrypt=false;databaseName=Inchiriere_Biciclete";
    private String user = "anaaremere";
    private String password = "123456789012345";

        

    public void executeUpdate(String sqlInsert) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
             
            int rowsAffected = statement.executeUpdate(sqlInsert);

           
            System.out.println(rowsAffected + " row(s) affected.");

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception or log it
        }
    }
    
    /* trebuie sa fie in acelasi fisier ptc result nu se poate folosi cand conection este inchis
     */
    public String[] loginVerify(JTextField textField1, JTextField textField2) {
        // Get user input from text fields
        String email = textField1.getText();
        String parola = textField2.getText();
        
        // Create SQL select statement
        String sqlLogin = "SELECT nume, prenume FROM Utilizator WHERE email = ? AND parola = ?";
     
        
        // Initialize ResultSet to null
        String[] userInfo = null;
     

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlLogin)) {

            // Set parameters
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, parola);

            // Execute the SQL statement
            try (ResultSet result = preparedStatement.executeQuery()) {
                // Check if the user exists
                if (result.next()) {
                    // Retrieve name and surname
                    String nume = result.getString("nume");
                    String prenume = result.getString("prenume");

                    // Store the user information in an array
                    userInfo = new String[]{nume, prenume};
                    
                    // Display user information
                    System.out.println("Name: " + nume);
                    System.out.println("Surname: " + prenume);
                }
            }

            // Clear text fields after insertion
            //textField1.setText("");
            //textField2.setText("");

        } catch (SQLException e) {
            // Handle SQL exception
            e.printStackTrace();
            // Log the exception if needed
        }

        return userInfo;
    }
    
    public String[] adminVerify(JTextField textField1, JTextField textField2) {
        // Get user input from text fields
        String email = textField1.getText();
        String parola = textField2.getText();
        
        // Create SQL select statement
        String sqlAdmin = "Select DISTINCT A.id_admin, U.nume, U.prenume From Administrator A, Utilizator U "+
        					" where A.id_utilizator = U.id_utilizator and U.email = ? and U.parola = ? ";
     
        
        // Initialize ResultSet to null
        String[] adminInfo = null;
     

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlAdmin)) {

            // Set parameters
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, parola);

            // Execute the SQL statement
            try (ResultSet result = preparedStatement.executeQuery()) {
                // Check if the user exists
                if (result.next()) {
                    
                	int idAdmin = result.getInt("id_admin");
                    String nume = result.getString("nume");
                    String prenume = result.getString("prenume");

                    // Store the user information in an array
                    adminInfo = new String[]{Integer.toString(idAdmin),nume, prenume};
                    
                    // Display user information
                    System.out.println("id_admin: " + idAdmin);
                    System.out.println("Name: " + nume);
                    System.out.println("Surname: " + prenume);
                }
            }

            
        } catch (SQLException e) {
            // Handle SQL exception
            e.printStackTrace();
            // Log the exception if needed
        }

        return adminInfo;
    }

    public String[] getLocations() {
        // Create SQL select statement for locations
        String sqlLocations = "SELECT locatie FROM Centru_Inchirieri";
        String[] locations = null;

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlLocations);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            List<String> locationList = new ArrayList<>();

            // Retrieve locations
            while (resultSet.next()) {
                String location = resultSet.getString("locatie");
                locationList.add(location);
            }

            // Store locations in an array
            locations = locationList.toArray(new String[0]);

            // Display locations
            System.out.println("Locations: " + Arrays.toString(locations));

        } catch (SQLException e) {
            // Handle SQL exception
            e.printStackTrace();
            // Log the exception if needed
        }

        return locations;
    }
    
    
    //Select cu subcereri si join
    public List<String> getUserAppointments(String email, String parola ){
		

    	List<String> appointmentsList = new ArrayList<>();
    	String sqlGetAppointments = "Select Distinct CI.locatie, CB.denumire, P.interval*CB.pret_inchiriat_ora as Pret_total, P.start_programare, P.ora \n"+ 
    			"From Programari P, Categorie_Bicicleta CB , Centru_Inchirieri CI, Utilizator U \n"+
    			"where P.id_utilizator = (Select U1.id_utilizator From Utilizator U1 where U1.email = ? and U1.parola = ? ) and " +
    			"P.id_categorie = CB.id_categorie and P.id_centru = CI.id_centru" ;
    	
    	try (Connection connection = DriverManager.getConnection(url, user, password);
    		PreparedStatement preparedStatement = connection.prepareStatement(sqlGetAppointments)) {

        // Set parameters
    	
    		preparedStatement.setString(1, email);
    		preparedStatement.setString(2, parola);
            
    		try (ResultSet resultSet = preparedStatement.executeQuery()) {

                
                while (resultSet.next()) {
                  
                    String appointmentDetails = "Aveti o rezervare la centru din " + resultSet.getString("locatie") + ", o bicicleta din categoria " +
                            resultSet.getString("denumire") + ", care va va costa " +
                            resultSet.getInt("Pret_total") + ", iar rezervarea este facuta pentru data de " +
                            resultSet.getString("start_programare") + " " +
                            resultSet.getString("ora");

                    appointmentsList.add(appointmentDetails);
                }
            }      

    	} catch (SQLException e) {
        // Handle SQL exception
    		e.printStackTrace();
        // Log the exception if needed
    	}

    	
    	return appointmentsList;
    	
    	
    }
    
    public List<BikeCategory> getBikeCategoryInfo() {
        String sqlQuery = "SELECT denumire, pret_inchiriat_ora FROM Categorie_Bicicleta";
        List<BikeCategory> bikeCategories = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String category = resultSet.getString("denumire");
                double pricePerHour = resultSet.getDouble("pret_inchiriat_ora");

                BikeCategory bikeCategory = new BikeCategory(category, pricePerHour);
                bikeCategories.add(bikeCategory);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bikeCategories;
    }
    
    //update simple
    public void updatePersonalDetails(String nume,String prenume,String email,String parola, String oldEmail, String oldPassword) {
        // Get user input from text fields
        
        
        // Create SQL select statement
     
        String sqlUpdateDetails  = " UPDATE Utilizator SET nume = ?, prenume= ?, email= ?, parola=?  WHERE email = ? and parola = ?";
        
        
        // Initialize ResultSet to null
        
     

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdateDetails)) {

            // Set parameters
        	preparedStatement.setString(1, nume);
        	preparedStatement.setString(2, prenume);
        	preparedStatement.setString(3, email);
        	preparedStatement.setString(4, parola);
            preparedStatement.setString(5, oldEmail);
            preparedStatement.setString(6, oldPassword);
                
            int rowsAffected = preparedStatement.executeUpdate();

           
            System.out.println(rowsAffected + " row(s) affected.");          

        } catch (SQLException e) {
            // Handle SQL exception
            e.printStackTrace();
            // Log the exception if needed
        }

        
    }
    
    // INsert cu subcereri 
    public void makeAppointment(String centru,String categorie,String email,String parola, String data, int ora, int interval) {
        // Get user input from text fields
        // Create SQL select statement
        String sqlAppointment  = "INSERT INTO Programari(id_centru, id_categorie, id_utilizator, start_programare, ora, interval) "+
        " VALUES ((SELECT id_centru FROM Centru_Inchirieri WHERE locatie = ?),"+
        " (SELECT id_categorie FROM Categorie_Bicicleta WHERE denumire = ?),"+
        " (SELECT id_utilizator FROM Utilizator WHERE  email = ? and parola = ?), ?, ?, ? )";
        
        
        // Initialize ResultSet to null

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlAppointment)) {

            // Set parameters
        	preparedStatement.setString(1, centru);
        	preparedStatement.setString(2, categorie);
        	preparedStatement.setString(3, email);
        	preparedStatement.setString(4, parola);
            preparedStatement.setString(5, data);
            preparedStatement.setInt(6, ora);
            preparedStatement.setInt(7, interval);
                
            int rowsAffected = preparedStatement.executeUpdate();
            //int rowsAffected = 1;
            // Optionally, you can check the number of rows affected
            System.out.println(rowsAffected + " row(s) affected.");          

        } catch (SQLException e) {
            // Handle SQL exception
            e.printStackTrace();
            // Log the exception if needed
        }
    }
    public void deleteUser(String email, String parola){
    	
    	String sqlDeleteUser = "Delete from Utilizator where email = ? and parola = ?";
    	try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement preparedStatement = connection.prepareStatement(sqlDeleteUser)) {

               // Set parameters
           	preparedStatement.setString(1, email);
           	preparedStatement.setString(2, parola);

           	int rowsAffected = preparedStatement.executeUpdate();
            //int rowsAffected = 1;
            // Optionally, you can check the number of rows affected
            System.out.println(rowsAffected + " row(s) affected.");          

        } catch (SQLException e) {
            // Handle SQL exception
            e.printStackTrace();
            // Log the exception if needed
        }

    	
    }

    public void deleteAppointment(String email, String parola){
    	
    	String sqlDeleteAppointment = "Delete From Programari where id_utilizator = "+
    	"(Select id_utilizator From Utilizator where email = ? and parola = ?)";
    	try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement preparedStatement = connection.prepareStatement(sqlDeleteAppointment)) {

               // Set parameters
           	preparedStatement.setString(1, email);
           	preparedStatement.setString(2, parola);

           	int rowsAffected = preparedStatement.executeUpdate();
            //int rowsAffected = 1;
            // Optionally, you can check the number of rows affected
            System.out.println(rowsAffected + " row(s) affected.");          

        } catch (SQLException e) {
            // Handle SQL exception
            e.printStackTrace();
            // Log the exception if needed
        }

    	
    }
    //sql complicat cu join si functii agregat
    public String getTopCostumer() {
	// TODO Auto-generated method stub
	 String sqlTopClient = "Select C.nume, C.prenume, SUM( CB.pret_inchiriat_ora* B.durata_inchirierii) as TotalSpent "+
	" From Client C, Bon B, Bicicleta Bi, Categorie_Bicicleta CB where B.id_client = C.id_client and "+ 
	"	Bi.id_categorie = CB.id_categorie and 	B.id_bicicleta = Bi.id_bicicleta Group by C.nume, C.prenume "+
	"order by TotalSpent desc ";
     String dial = null;
     String name = null;
     String prenume = null;
     int totalPrice = -1;

     try (Connection connection = DriverManager.getConnection(url, user, password);
          PreparedStatement preparedStatement = connection.prepareStatement(sqlTopClient);
          ResultSet resultSet = preparedStatement.executeQuery()) {

         

         // Retrieve locations
         while (resultSet.next()) {
              name = resultSet.getString("nume");
              prenume = resultSet.getString("prenume");
              totalPrice = resultSet.getInt("TotalSpent");
              break;
             
         }

         // Store locations in an array
         
         dial = "Top Costumer is: " +name+' '+prenume +" cu suma de: " + Integer.toString(totalPrice);
         // Display locations
         System.out.println(dial);

     } catch (SQLException e) {
         // Handle SQL exception
         e.printStackTrace();
         // Log the exception if needed
     }

     return dial;

	
    }
    // get nr of bikes where the youngest worker works
    // are subcereri
    public String getBikesAtACenter() {
    // Create SQL select statement for locations
    	String sqlBikes = "Select Count(B.id_bicicleta) as nrBiciclete From Bicicleta B, Centru_Inchirieri CI, "+
    				" Angajat A where B.id_centru = CI.id_centru and 	A.id_centru = CI.id_centru and A.id_centru = "+
    								" (Select top 1 id_centru	from Angajat	order by data_nasterii DESC) ";
    	int nrBikes = -1;
    	String dial = null;

	    try (Connection connection = DriverManager.getConnection(url, user, password);
	         PreparedStatement preparedStatement = connection.prepareStatement(sqlBikes);
	         ResultSet resultSet = preparedStatement.executeQuery()) {

	        while (resultSet.next()) {
	             nrBikes = resultSet.getInt("nrBiciclete");
	            
	        }
	        dial = "Nr biciclete ce sunt la centru unde lucreaza cel mai tanar angajat: " + Integer.toString(nrBikes);
	
	        System.out.println(dial);
	
	    } catch (SQLException e) {
	        // Handle SQL exception
	        e.printStackTrace();
	        // Log the exception if needed
	    }
	
	    return dial;
	}

	public void insertNewCenter(String centerName, String mName, String mPrenume) {
		//insert center on name
		String sqlInsertCenter = "Insert into Centru_Inchirieri(locatie,managerID) " +
" VALUES (? , (SELECT id_angajat FROM Angajat WHERE nume = ? and prenume = ?))";
		
		try (Connection connection = DriverManager.getConnection(url, user, password);
				 PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertCenter)) {

            // Set parameters
        	preparedStatement.setString(1, centerName);
        	preparedStatement.setString(2, mName);
        	preparedStatement.setString(3, mPrenume);


			int rowsAffected = preparedStatement.executeUpdate();
            //int rowsAffected = 1;
            // Optionally, you can check the number of rows affected
            System.out.println(rowsAffected + " row(s) affected.");          

        } catch (SQLException e) {
            // Handle SQL exception
            e.printStackTrace();
            // Log the exception if needed
        }


		// TODO Auto-generated method stub
		
	}

	public String[] getNonManageri() {
		String sqlNonManageri= "SELECT A.nume, A.prenume "
				+ "FROM Angajat A LEFT JOIN Centru_Inchirieri CI "
				+ "ON A.id_angajat = CI.managerID "
				+ "WHERE CI.managerID IS NULL";
        String[] nonManageri = null;

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlNonManageri);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            List<String> managerList = new ArrayList<>();

            // Retrieve locations
            while (resultSet.next()) {
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                String nman = nume+ ' ' + prenume;
                
                managerList.add(nman);
            }

            // Store locations in an array
            nonManageri = managerList.toArray(new String[0]);

            // Display locations
            System.out.println("nonManageri: " + Arrays.toString(nonManageri));

        } catch (SQLException e) {
            // Handle SQL exception
            e.printStackTrace();
            // Log the exception if needed
        }

        return nonManageri;

	}

	public void updateWorkLocation(String numeCentru, String numeManager, String prenumeManager) {
		// TODO Auto-generated method stub
		String sqlWorkLocation= "UPDATE Angajat "
				+ " SET id_centru = (Select id_centru From Centru_Inchirieri where locatie = ? ) "
				+ " WHERE nume = ? and prenume = ? ";
		try (Connection connection = DriverManager.getConnection(url, user, password);
				 PreparedStatement preparedStatement = connection.prepareStatement(sqlWorkLocation)) {

           // Set parameters
       	preparedStatement.setString(1, numeCentru);
       	preparedStatement.setString(2, numeManager);
       	preparedStatement.setString(3, prenumeManager);


			int rowsAffected = preparedStatement.executeUpdate();
           //int rowsAffected = 1;
           // Optionally, you can check the number of rows affected
           System.out.println(rowsAffected + " row(s) affected.");          

       } catch (SQLException e) {
           // Handle SQL exception
           e.printStackTrace();
           // Log the exception if needed
       }


		// TODO Auto-generated method stub
		
	}

	public double getAverageCenter(String numeCentru) {
		// TODO Auto-generated method stub
		double avg = -1.3;
		System.out.println(numeCentru);
		String sqlGetAverageSalary = "SELECT AVG(A.salariu) AS average_salariu "
				+ "FROM Angajat A JOIN Centru_Inchirieri CI "
				+ "ON A.id_centru = CI.id_centru "
				+ "WHERE CI.locatie = ? ";
		
		
		    	try (Connection connection = DriverManager.getConnection(url, user, password);
		    		    PreparedStatement preparedStatement = connection.prepareStatement(sqlGetAverageSalary)) {

		    	    preparedStatement.setString(1, numeCentru); // Set parameter before executing query
		    	    
		    	    try (ResultSet resultSet = preparedStatement.executeQuery()) {
		    	        while (resultSet.next()) {
		    	            avg = resultSet.getDouble("average_salariu");
		    	        }
		    	    }
		        } catch (SQLException e) {
		            // Handle SQL exception
		            e.printStackTrace();
		            // Log the exception if needed
		        }

		return avg;
	}
	
	public double getCashInFrom(String year1, String year2) {
		// TODO Auto-generated method stub
		double cash = -1.3;
		
		
		String sqlGetAverageSalary = "Select SUM(B.durata_inchirierii* CB.pret_inchiriat_ora) as CashIn "
				+ "From Bon B, Categorie_Bicicleta CB, Bicicleta Bi "
				+ "where B.id_bicicleta = Bi.id_bicicleta and	"
				+ "Bi.id_categorie = CB.id_categorie and  "
				+ "B.data_inchirierii >= ?  and		"
				+ "B.data_inchirierii <= ? ";
		
		
		
		    	try (Connection connection = DriverManager.getConnection(url, user, password);
		    		    PreparedStatement preparedStatement = connection.prepareStatement(sqlGetAverageSalary)) {

		    	    
		    		if(Integer.parseInt(year2)> Integer.parseInt(year1)){
		    			preparedStatement.setString(1,year1); // Set parameter before executing query
		    			preparedStatement.setString(2,year2);
		    		}
		    		else{
		    			preparedStatement.setString(1, year2);
		    			preparedStatement.setString(2, year1);
		    		}
		    	    try (ResultSet resultSet = preparedStatement.executeQuery()) {
		    	        while (resultSet.next()) {
		    	            cash = resultSet.getDouble("CashIn");
		    	        }
		    	    }
		        } catch (SQLException e) {
		            // Handle SQL exception
		            e.printStackTrace();
		            // Log the exception if needed
		        }

		return cash;
	}

		
		
		
	

    
    
}
