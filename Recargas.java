import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Recargas {

	public static void recargaMedia(Festa festa) {
		double media;
		
		ResultSet result = Database.runQuery("SELECT AVG(VALOR)" + 
				" FROM RECARGA R JOIN PARTICIPANTE P ON (R.PARTICIPANTE = P.COD_CARTAO)" + 
				" WHERE (FESTA = UPPER('" + festa.getNome() + "')) AND (EDICAO = " + festa.getEdicao() + ")" +				
				" GROUP BY FESTA;");
		try {
			if (!result.isBeforeFirst() ) {
			    System.out.println("Não houve recargas nessa festa.");
			    return;
			}
			result.next();
			media = result.getDouble(1);
	        System.out.println("Recarga média: R$ " + String.format("%.2f", media));
		} catch (SQLException e) {
			System.out.println("Não houve recargas nessa festa.");
		}
	}
	
	public static void recargaMediaTipo(Short tipo) {
		double media;
		
		ResultSet result = Database.runQuery("SELECT AVG(VALOR)" + 
				" FROM RECARGA R JOIN PARTICIPANTE P ON (R.PARTICIPANTE = P.COD_CARTAO)" + 
				" JOIN FESTA F ON (P.FESTA = F.NOME AND P.EDICAO = F.EDICAO)" + 
				" WHERE (TIPO = " + tipo + ")" +			
				" GROUP BY TIPO;");
		try {
			if (!result.isBeforeFirst() ) {
				System.out.println("Não houve recargas para festas " + 
						(tipo == 0 ? " de halloween." : "juninas"));
			    return;
			}
			result.next();
			media = result.getDouble(1);
	        System.out.println("Recarga média para festas " + (tipo == 0 ? " de halloween" : "juninas") + ": R$ " + String.format("%.2f", media));
		} catch (SQLException e) {
			System.out.println("Não houve recargas para festas " + 
					(tipo == 0 ? " de halloween." : "juninas."));
		}
	}
	
	public static void recargaPorTempo(Festa festa) {
		System.out.println("Recarga média por hora:");
		System.out.println("HORA\tMÉDIA");
		ResultSet result = Database.runQuery("SELECT DATE_PART('HOUR', R.HORA), AVG(VALOR)" + 
				" FROM RECARGA R JOIN PARTICIPANTE P ON (R.PARTICIPANTE = P.COD_CARTAO)" + 
				" WHERE (FESTA = UPPER('" + festa.getNome() + "')) AND (EDICAO = " + festa.getEdicao() + ")" + 
				" GROUP BY DATE_PART('HOUR', R.HORA);");
		try {
			if (!result.isBeforeFirst() ) {
			    System.out.println("Não houve recargas nessa festa.");
			    return;
			}
	        ResultSetMetaData rsmd = result.getMetaData();
	        int columnsNumber = rsmd.getColumnCount();
	        while (result.next()) {
	            for (int i = 1; i <= columnsNumber; i++) {
	                String columnValue = result.getString(i);
	                if (i == 1) System.out.print(columnValue);
	                else if (i == 2) System.out.print("\t" + String.format("%.2f", Double.parseDouble(columnValue)));
	                else System.out.print("\t" + columnValue);
	            }
	            System.out.println("");
	        }
			
		} catch (SQLException e) {
			System.out.println("Não houve recargas nessa festa.");
		}
	}
	
	public static void inserirRecarga(Festa festa) {
		int participante, barraca;
		float valor, saldo;
		
		System.out.println("Código do cartão do participante: ");
		System.out.print("> ");
		participante = Keyboard.readInt();
		saldo = verifyParticipante(festa, participante);
		
		if (saldo == -1) return;
		
		System.out.println("ID da barraca de recarga: ");
		System.out.print("> ");
		barraca = Keyboard.readInt();
		
		if (!verifyBarraca(festa, barraca)) return;
		
		System.out.println("Valor: ");
		System.out.print("> ");
		valor = Keyboard.readFloat();
		
		//insere recarga
    	Database.runUpdate("INSERT INTO RECARGA" + 
				" VALUES ("+participante+","+barraca+",CURRENT_TIMESTAMP(0),"+valor+");");
    	
    	//atualiza saldo do participante
    	Database.runUpdate("UPDATE PARTICIPANTE" + 
				" SET SALDO = "+(saldo+valor)+
    			" WHERE (COD_CARTAO = "+participante+");");
	}
	
	private static float verifyParticipante(Festa festa, int participante) {
		float saldo = -1;
		ResultSet result = Database.runQuery("SELECT SALDO" + 
				" FROM PARTICIPANTE" + 
				" WHERE (COD_CARTAO = "+participante+") AND (FESTA = UPPER('" + festa.getNome() + "')) AND (EDICAO = " + festa.getEdicao() + ");");
		
		try {
			if (!result.isBeforeFirst() ) {
				System.out.println("Participante com código "+participante+" não encontrado.");
			    return saldo;
			}
			result.next();
			saldo = result.getFloat(1);
		} catch (SQLException e) {
			System.out.println("Participante com código "+participante+" não encontrado.");
		}
		
		return saldo;
	}
	
	private static boolean verifyBarraca(Festa festa, int barraca) {
		int tipo;
		ResultSet result = Database.runQuery("SELECT TIPO" + 
				" FROM BARRACA" + 
				" WHERE (ID_BARRACA = "+barraca+") AND (FESTA = UPPER('" + festa.getNome() + "')) AND (EDICAO = " + festa.getEdicao() + ");");
		
		try {
			if (!result.isBeforeFirst() ) {
				System.out.println("Barraca com código "+barraca+" não encontrado.");
			    return false;
			}
			result.next();
			tipo = result.getInt(1);
			if (tipo == 2) return true;
			else System.out.println("Barraca com código "+barraca+" não é do tipo recarga.");
		} catch (SQLException e) {
			System.out.println("Barraca com código "+barraca+" não encontrado.");
		}
		
		return false;
	}
}
