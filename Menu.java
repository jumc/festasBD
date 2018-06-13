public class Menu {

    public static void MenuPrincipal(){
        System.out.print("\n" +
                "    _                         _           _       ___       _           \n" +
                "   /_\\  __ ___ ______ ___ _ _(_)__ _   __| |___  | __|__ __| |_ __ _ ___\n" +
                "  / _ \\/ _/ -_|_-<_-</ _ \\ '_| / _` | / _` / -_) | _/ -_|_-<  _/ _` (_-<\n" +
                " /_/ \\_\\__\\___/__/__/\\___/_| |_\\__,_| \\__,_\\___| |_|\\___/__/\\__\\__,_/__/\n" +
                "                                                                        \n");
        System.out.println("SELECIONE O QUE DESEJA FAZER APERTANDO O NUMERO CORRESPONDENTE \n");
        System.out.println("1) Gerenciar festas");
        System.out.println("2) Gerenciar pessoas");
        System.out.print("> ");

        int i = Keyboard.readInt();
        switch (i){
            case 1:
                MenuFesta();
                break;
            case 2:
                MenuPessoas();
                break;
        }
    }

    public static void MenuFesta(){
        System.out.println("\n>> Gerenciar festas ");
        System.out.println("1) Selecionar festa");
        System.out.println("2) Criar festa");
        System.out.println("3) Voltar");
        System.out.print("> ");

        int i = Keyboard.readInt();
        switch (i){
            case 1:
                MenuFestaSelecionada();
                break;
            case 2:
                //funcao criar
                break;
            case 3:
                MenuPrincipal();
                break;
        }
    }

    public static void MenuFestaSelecionada(){
        System.out.println("\n>> Gerenciar festas >> Selecionar festa");
        System.out.println("1) Alterar festa");
        System.out.println("2) Remover festa");
        System.out.println("3) Inserir membro da organizacao");
        System.out.println("4) Inserir aluguel");
        System.out.println("5) Equipamentos emprestados");
        System.out.println("6) Produtos comprados por empresa");
        System.out.println("7) Balanço financeiro");
        System.out.println("8) Recargas");
        System.out.println("9) Gerenciar concursos");
        System.out.println("10) Voltar");
        System.out.print("> ");

        Festa festa = new Festa();

        int i = Keyboard.readInt();
        switch (i){
            case 1:
                // funcao alterar
                break;
            case 2:
                //funcao remover
                break;
            case 3:
                //funcao organizacao
                break;
            case 4:
                Aluguel aluguel = new Aluguel(festa);
                aluguel.inserirAluguel();
                break;
            case 5:
                MenuEmprestimos(festa);
                MenuFestaSelecionada();
                break;
            case 6:
                //funcao produtos
                break;
            case 7:
                MenuBalanco();
                break;
            case 8:
                MenuRecarga();
                break;
            case 9:
                MenuConcursos(festa);
                MenuFestaSelecionada();
            case 10:
                MenuFesta();
                break;
        }
}

    public static void MenuEmprestimos(Festa festa){
        System.out.println("\n>> Gerenciar festas >> Selecionar festa >> Equipamentos emprestados");
        System.out.println("1) Visualizar emprestimos");
        System.out.println("2) Cadastrar emprestimos");
        System.out.println("3) Voltar");
        System.out.print("> ");

        Emprestimos empr = new Emprestimos(festa);

        int i = Keyboard.readInt();
        switch (i){
            case 1:
                empr.visualizaEmprestimos();
                MenuFestaSelecionada();
                break;
            case 2:
                empr.inserirEmprestimo();
                MenuFestaSelecionada();
                break;
            case 3:
                MenuFestaSelecionada();
                break;
        }
    }

    public static void MenuBalanco(){
        System.out.println("\n>> Gerenciar festas >> Selecionar festa >> Balanco financeiro");
        System.out.println("1) Saldo final");
        System.out.println("2) Despesas por tipo de produto/servico");
        System.out.println("3) Arrecadação");
        System.out.println("4) Voltar");
        System.out.print("> ");

        int i = Keyboard.readInt();
        switch (i){
            case 1:
                // funcao saldo
                break;
            case 2:
                //funcao despesas
                break;
            case 3:
                //funcao arrecadacao
                break;
            case 4:
                MenuFestaSelecionada();
                break;
        }
    }

    public static void MenuRecarga(){
        System.out.println("\n>> Gerenciar festas >> Selecionar festa >> Recargas");
        System.out.println("1) Recarga média");
        System.out.println("2) Recarga média para o seu tipo de festa");
        System.out.println("3) Volume de recargas da sua festa, por faixa de tempo");
        System.out.println("4) Voltar");
        System.out.print("> ");

        int i = Keyboard.readInt();
        switch (i){
            case 1:
                // funcao media
                break;
            case 2:
                //funcao media tipo
                break;
            case 3:
                //funcao recarga por tempo
                break;
            case 4:
                MenuFestaSelecionada();
                break;
        }
    }

    public static void MenuConcursos(Festa festa){
        System.out.println("\n>> Gerenciar festas >> Selecionar festa >> Gerenciar concursos");
        System.out.println("1) Cadastrar coordenador de concurso de adivinhacao");
        System.out.println("2) Cadastrar coordenador de concurso de quem come mais");
        System.out.println("3) Cadastrar coordenador de concurso de fantasia");
        System.out.println("4) Voltar");
        System.out.print("> ");

        int i = Keyboard.readInt();
        switch (i){
            case 1:
                Concurso conc1 = new Concurso(festa, "ADV");
                conc1.inserirCoordenador();
                MenuConcursos(festa);
                break;
            case 2:
                Concurso conc2 = new Concurso(festa, "COMIDA");
                conc2.inserirCoordenador();
                MenuConcursos(festa);
                break;
            case 3:
                Concurso conc3 = new Concurso(festa, "FANTASIA");
                conc3.inserirCoordenador();
                MenuConcursos(festa);
                break;
            case 4:
                MenuFestaSelecionada();
        }
    }

    public static void MenuPessoas(){
        System.out.println("\n>> Gerenciar pessoas");
        System.out.println("1) Cadastrar pessoa");
        System.out.println("2) Selecionar pessoa");
        System.out.println("3) Voltar");
        System.out.print("> ");

        int i = Keyboard.readInt();
        switch (i){
            case 1:
                // funcao cadastrar
                break;
            case 2:
                // funcao selecionar
                break;
            case 3:
                MenuPrincipal();
                break;
        }
    }

    public static void MenuPessoaSelecionada(){
        System.out.println("\n>> Gerenciar pessoas >> Selecionar pessoa");
        System.out.println("1) Alterar pessoa");
        System.out.println("2) Remover pessoa");
        System.out.println("3) Voltar");
        System.out.print("> ");

        int i = Keyboard.readInt();
        switch (i){
            case 1:
                // funcao alterar
                break;
            case 2:
                // funcao remover
                break;
            case 3:
                MenuPessoas();
                break;
        }
    }
}