import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Aluguel {
    private Festa festa;
    private String endereco, inicio, fim;
    private float valor;

    public Aluguel(Festa festa){
        this.festa = festa;
    }

    private void getInfo(){
        do{
            System.out.println("Digite o ENDERECO do local: ");
            System.out.print("> ");
            endereco = Keyboard.readLine();
        } while(verifyEndereco() == false);
        System.out.println("Digite a hora e data de INICIO no formato hh:mm-dd/MM/AAAA (ex: 19:30-23/02/2015): ");
        System.out.print("> ");
        inicio = Keyboard.readTimestamp();
        System.out.println("Digite a hora e data de FIM no formato hh:mm-dd/MM/AAAA (ex: 19:30-23/02/2015): ");
        System.out.print("> ");
        fim = Keyboard.readTimestamp();
        System.out.println("Digite o valor do aluguel:");
        System.out.print("> ");
        valor = Keyboard.readFloat();
    }

    private boolean verify(ResultSet result){
        try {
            if(result.next()){
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean verifyOverlap(){
        // Se nao encontra overlap retorna true
        ResultSet result = Database.runQuery("SELECT * FROM ALUGUEL WHERE (INICIO, FIM) OVERLAPS (TO_TIMESTAMP('" +  inicio+ "','HH24:MI-DD/MM/YYYY'), TO_TIMESTAMP('" + fim + "','HH24:MI-DD/MM/YYYY')) AND ENDERECO = UPPER('" + endereco + "');");
        return verify(result);
    }

    private boolean verifyEmpty(){
        // Se nao encontra nenhum aluguel para a festa retorna true
        ResultSet result = Database.runQuery("SELECT * FROM ALUGUEL WHERE FESTA = UPPER('" + festa.getNome() + "') AND EDICAO = " + festa.getEdicao()+ ";");
        return verify(result);
    }

    private boolean verifyEndereco(){
        // Se o endereco existe retorna true
        ResultSet result = Database.runQuery("SELECT * FROM LUGAR WHERE ENDERECO = UPPER('" + endereco + "');");
        if(!verify(result) == false){
            System.out.println("Local nao cadastrado.");
            return false;
        }
        return true;
    }

    public void inserirAluguel(){
        if (verifyEmpty()){
            getInfo();
            if(verifyOverlap()) {
                int n = Database.runUpdate("INSERT INTO ALUGUEL(ENDERECO, INICIO, FIM, FESTA, EDICAO, VALOR) VALUES (UPPER('" + endereco + "'), TO_TIMESTAMP('" + inicio.toString() + "','HH24:MI-DD/MM/YYYY'), TO_TIMESTAMP('" + fim + "','HH24:MI-DD/MM/YYYY'), UPPER('" + festa.getNome() + "'), " + festa.getEdicao() + ", " + valor + ");");
                if (n == 1) {
                    System.out.println("Aluguel inserido com sucesso.");
                }
            } else {
                System.out.println("Este local já está alugado neste período.");
            }
        } else {
            System.out.println("Essa festa já possui um aluguel");
        }
    }
}
