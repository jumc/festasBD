import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Emprestimos {
    Festa festa;
    public Emprestimos(Festa festa){this.festa = festa;}

    public void visualizaEmprestimos(){
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

    private boolean verifyItem(String nome){
        ResultSet result = Database.runQuery("SELECT NOME FROM EQUIPAMENTO WHERE NOME = UPPER('" + nome + "');");
        try {
            if (!result.isBeforeFirst() ) {
                System.out.println("Equipamento inexistente.");
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void inserirEmprestimo(){
        String item, dt_emprestimo, dt_devolucao;
        int qtd;

        System.out.println("Digite o nome do equipamento: ");
        System.out.print("> ");
        item = Keyboard.readLine();
        if(!verifyItem(item)) return;
        System.out.println("Digite a data de emprestimo: ");
        System.out.print("> ");
        dt_emprestimo= Keyboard.readDate();
        System.out.println("Digite a data de devolucao: ");
        System.out.print("> ");
        dt_devolucao = Keyboard.readDate();
        System.out.println("Digite a quantidade: ");
        System.out.print("> ");
        qtd = Keyboard.readInt();

        int n = Database.runUpdate("INSERT INTO EMPRESTIMO(EQUIPAMENTO, FESTA, EDICAO, DT_EMPRESTIMO, DT_DEVOLUCAO, QTD) VALUES (UPPER('" + item + "'), UPPER('" + festa.getNome() + "'), " + festa.getEdicao() + ", TO_DATE('" + dt_emprestimo + "','DD/MM/YYYY'), TO_DATE('" + dt_devolucao + "','DD/MM/YYYY'), " + qtd + ");");
        if (n == 1) {
            System.out.println("Emprestimo inserido com sucesso.");
        } else {
            System.out.println("Ocorreu um erro ao inserir o emprestimo.");
        }
    }
}
