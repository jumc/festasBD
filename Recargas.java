import java.sql.ResultSet;
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
}
