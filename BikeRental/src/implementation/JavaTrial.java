package implementation;

import javax.swing.SwingUtilities;

public class JavaTrial {


	    	 public static void main(String[] args) {
	    	        SwingUtilities.invokeLater(() -> {
	    	            new CombinedFrames();
	    	        });
	    	    }
	}
	

// vreau sa fac on interfata in care sa adaug login si register -> la login sa extrag date din tabela utilizator si la register sa adaug date in tabela utilizator
// o alta interfata in care sa pot sa imi aleg bicicleta si programarea si adaug in tabela progrmari