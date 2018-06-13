import java.sql.ResultSet;
import java.util.ArrayList;

public class Concurso {
    private Festa festa;
    private String tipo;
    int id;
    private ArrayList<String> coordenadores;
    boolean existe;

    public Concurso(Festa festa, String tipo){
        this.festa = festa;
        this.tipo = tipo;

        if(festa.getTipo() == 0 && tipo != "FANTASIA" || festa.getTipo() == 1 && tipo == "FANTASIA"){
            existe = false;
            System.out.println("Essa festa não pode possuir esse tipo de concurso");
            return;
        }
        try {
            ResultSet result = Database.runQuery("SELECT ID_CONCURSO FROM CONC_" + tipo + " WHERE FESTA = UPPER('" + festa.getNome() + "') AND EDICAO = " + String.valueOf(festa.getEdicao())+ ";");
            if (result.isBeforeFirst() ) {
                existe = true;
                result.next();
                id = result.getInt(1);
            }
            else existe = false;
        } catch (Exception e){
            existe = false;
        }

        if(existe){
            coordenadores = new ArrayList<>();
            try {
                ResultSet result = Database.runQuery("SELECT COORDENADOR FROM COORD_" + tipo + " WHERE CONCURSO = " + id + ";");
                if(!result.isBeforeFirst()){
                    System.out.println("Esse concurso ainda nao tem coordenadores");
                    return;
                }
                System.out.println("Ja sao coordenadores:");
                while (result.next()) {
                    System.out.println(result.getString(1) + " ");
                    coordenadores.add(result.getString(1));
                }
            } catch (Exception e){
                System.out.println("Esse concurso ainda nao tem coordenadores");
            }
        }
    }

    public void inserirCoordenador(){
        if(existe) {
            Organizacao org = new Organizacao(festa);
            System.out.println("Digite o cpf do coordenador que deseja inserir: ");
            System.out.print("> ");
            String cpf = Keyboard.readLine();
            if (org.membros.contains(cpf)) {
                if(coordenadores.contains(cpf)){
                    System.out.println("Essa pessoa ja e coordenadora desse concurso.");
                    return;
                }
                int n = Database.runUpdate("INSERT INTO COORD_" + tipo + "(COORDENADOR, CONCURSO) VALUES ('" + cpf + "', " + id + ");");
                if (n == 1) {
                    System.out.println("Pessoa inserida com sucesso como coordenadora do concurso.");
                } else {
                    System.out.println("Ocorreu um erro ao inserir o coordenador.");
                }
            } else {
                System.out.println("Essa pessoa nao organiza essa festa.");
            }
        }
    }
}
