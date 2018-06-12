import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

public class Emprestimos {
    Festa festa;
    public Emprestimos(Festa festa){this.festa = festa;}

    public void VisualizaEmprestimos(){
        try {
            ResultSet result = Database.runQuery("SELECT EQUIPAMENTO, DT_EMPRESTIMO, DT_DEVOLUCAO, QTD FROM EMPRESTIMO WHERE FESTA = UPPER('" + festa.getNome() + "') AND EDICAO = " + String.valueOf(festa.getEdicao())+ ";");
            if (!result.isBeforeFirst() ) {
                System.out.println("Essa festa nao realizou emprestimos.");
                return;
            }
            ResultSetMetaData rsmd = result.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (result.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = result.getString(i);
                    System.out.println(rsmd.getColumnName(i) + ": " + columnValue);
                }
                System.out.println("");
            }
        } catch (Exception e){
            System.out.println("Essa festa nao realizou emprestimos.");
        }
    }
}
