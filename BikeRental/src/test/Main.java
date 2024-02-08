
package test;

import javax.swing.SwingUtilities;

import implementation.*;

public class Main {

	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CombinedFrames();
        });
    }	
}
