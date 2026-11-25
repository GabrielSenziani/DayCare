/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package daycare;
import daycare.view.TelaPrincipal;
import java.sql.SQLException;

/**
 *
 * @author senzi
 */
public class Daycare {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            daycare.dao.Conexao.criarTabelas();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Daycare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return;
        }
        java.awt.EventQueue.invokeLater(() -> {
         new TelaPrincipal().setVisible(true);
    });
    }
    
}
