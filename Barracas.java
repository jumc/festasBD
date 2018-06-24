import java.sql.ResultSet;
import java.sql.SQLException;

public class Barracas {
	
    public static void cadastrarBarraca(Festa festa) {
    	String nome, descricao;
    	int id, num = -1, tipo = -1;
    	
    	System.out.println("Digite o ID da barraca: ");
    	System.out.print("> ");
    	id = Keyboard.readInt();
    	if (!verifyValidID(id)) {
    		System.out.println("ID ja existente");
    		return;
    	}
    	
    	while (num < 0) {
	    	System.out.println("Digite o número da barraca: ");
	    	System.out.print("> ");
	    	num = Keyboard.readInt();
	    	if (num < 0) {
	    		System.out.println("O número da barraca nao pode ser negativo!");
	    	} else {
	    		if (!verifyValidNum(festa, num)) {
	    			System.out.println("Número da barraca ja existente na festa atual");
	    			return;
	    		}
	    	}
    	}
    	
    	System.out.println("Digite o nome da barraca: ");
    	System.out.print("> ");
    	nome = Keyboard.readLine();
    	if (nome.length() > 32) nome = nome.substring(0, 32);
    	
    	System.out.println("Digite a descricao da barraca: ");
    	System.out.print("> ");
    	descricao = Keyboard.readLine();
    	if (descricao.length() > 256) descricao = descricao.substring(0, 256);
    	
    	while (tipo != 0 && tipo != 1 && tipo != 2) {
	    	System.out.println("Escolha o tipo da barraca: ");
	    	System.out.println("1) Comida");
	    	System.out.println("2) Lazer");
	    	System.out.println("3) Recarga");
	    	System.out.print("> ");
	    	tipo = Keyboard.readInt();
	    	tipo--;
    	}
    	
    	insertBarraca(festa, id, num, nome, descricao, tipo);
    }
    
    private static int insertBarraca(Festa festa, int id, int num, String nome, String descricao, int tipo) {
    	float preco = -1;
    	int r = 0;
    	
    	r += Database.runUpdate("INSERT INTO BARRACA" + 
				" VALUES ("+id+","+num+",UPPER('"+festa.getNome()+"'),"+festa.getEdicao()+",'"+nome+"','"+descricao+"',"+tipo+");");
    	
    	if (tipo == 1) {
	    	while (preco < 0) {
		    	System.out.println("Digite o preço do lazer: ");
		    	System.out.print("> ");
		    	preco = Keyboard.readFloat();
		    	if (preco < 0) {
		    		System.out.println("O preço nao pode ser negativo!");
		    	}
	    	}
	    	r += Database.runUpdate("INSERT INTO LAZER" + 
					" VALUES ("+preco+","+id+");");
	    	
    	}

    	return r;
    }

    private static boolean verifyValidID(int id) {
    	ResultSet result = Database.runQuery("SELECT ID_BARRACA FROM BARRACA WHERE ID_BARRACA = "+id+";");
    	try {
			if (!result.isBeforeFirst() ) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return false;
    }

    private static boolean verifyValidNum(Festa festa, int num) {
    	ResultSet result = Database.runQuery("SELECT ID_BARRACA FROM BARRACA WHERE NUMERO = "+num+" AND (FESTA = UPPER('" + festa.getNome() + "')) AND (EDICAO = " + festa.getEdicao() + ");");
    	try {
			if (!result.isBeforeFirst() ) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return false;
    }
}
