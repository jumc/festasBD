import java.io.Console;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Organizacao {

    private Festa festa;
    private ArrayList<String> membros;

    public Organizacao(Festa festa){
        this.festa = festa;
        membros = new ArrayList<>();
        try {
            ResultSet result = Database.runQuery("SELECT * FROM ORGANIZACAO WHERE FESTA = UPPER('" + festa.getNome() + "') AND EDICAO = " + String.valueOf(festa.getEdicao())+ ";");
            System.out.println("Ja sao organizadores:");
            while (result.next()) {
                System.out.println(result.getString(1) + " ");
                membros.add(result.getString(1));
            }
        } catch (Exception e){
            if(membros.isEmpty())
                System.out.println("Essa festa ainda nao possui membros na organizacao.");
        }
    }

    private String getMembro(){
        System.out.println("Digite o cpf do organizador que deseja inserir: ");
        System.out.print("> ");
        String cpf = Keyboard.readLine();
        ResultSet result = Database.runQuery("SELECT CPF FROM MEMBRO_COMISSAO WHERE CPF = UPPER('" + cpf.trim() + "');");
        try {
            if (result.isBeforeFirst() ) {
                return cpf;
            }
            else {
                System.out.println("Essa pessoa não esta cadastrada no sistema.");
                System.out.println("1) Cadastra-la");
                System.out.println("2) Tentar com outra pessoa");
                System.out.print("> ");
                int i = Keyboard.readInt();
                switch (i){
                    case 1:
                        return "";
                    case 2:
                        return getMembro();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private boolean verifyComOrg(String cpf){
        if(membros.contains(cpf)){
            return false;
        }
        return true;
    }

    public void inserirMembroOrganizacao(){
        String cpf = getMembro();
        if(cpf == "")
            Menu.MenuPessoas();
        if(verifyComOrg(cpf)) {
            int n = Database.runUpdate("INSERT INTO ORGANIZACAO(MEMBRO, FESTA, EDICAO) VALUES ('" + cpf + "', UPPER('" + festa.getNome() + "'), " + festa.getEdicao() + ");");
            if (n == 1) {
                System.out.println("Pessoa inserida com sucesso na comissao organizadora da festa.");
            } else {
                System.out.println("Ocorreu um erro ao inserir o organizador.");
            }
        } else {
            System.out.println("Esta pessoa ja organiza essa festa.");
        }
    }
}
