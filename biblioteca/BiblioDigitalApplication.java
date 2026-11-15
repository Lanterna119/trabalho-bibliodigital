import java.util.List;
import java.util.Scanner;

public class BiblioDigitalApplication {

    private static Scanner scanner = new Scanner(System.in);
    private static LivroService service =
            new LivroService(new LivroRepository());

    public static void main(String[] args) {
        int opcao;

        System.out.println("===== Bem vindo ao BiblioDigital =====");

        do {
            mostrarMenu();
            opcao = lerInteiro("Escolha uma das opções acima: ");

            try {
                switch (opcao) {
                    case 1:
                        cadastrarLivro();
                        break;
                    case 2:
                        pesquisarPorTitulo();
                        break;
                    case 3:
                        pesquisarPorAutor();
                        break;
                    case 4:
                        alterarLivro();
                        break;
                    case 5:
                        excluirLivro();
                        break;
                    case 0:
                        System.out.println("Encerrando o BiblioDigital...");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } while (opcao != 0);

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\nMenu:");
        System.out.println("1 - Cadastrar novo Livro");
        System.out.println("2 - Pesquisar Livro por título");
        System.out.println("3 - Pesquisar Livro por autor");
        System.out.println("4 - Alterar dados do Livro");
        System.out.println("5 - Excluir Livro");
        System.out.println("0 - Sair");
    }

    private static void cadastrarLivro() {
        System.out.println("\n===== CADASTRAR LIVRO =====");
        System.out.println("1 - Livro físico");
        System.out.println("2 - Livro digital");
        System.out.println("3 - Audiolivro");

        int tipo = lerInteiro("Escolha o tipo de livro: ");
        if (tipo < 1 || tipo > 3) {
            System.out.println("Tipo de livro inválido.");
            return;
        }

        String titulo = lerTexto("Digite o título do livro: ");
        String autor = lerTexto("Digite o autor do livro: ");
        int ano = lerInteiro("Digite o ano de publicação: ");

        Livro novoLivro;

        if (tipo == 1) {
            int quantidade = lerInteiro("Quantidade de exemplares: ");
            String localizacao = lerTexto("Localização do livro: ");
            novoLivro = new LivroFisico(titulo, autor, ano, quantidade, localizacao);
        } else if (tipo == 2) {
            String formato = lerTexto("Formato do arquivo: ");
            double tamanho = lerDecimal("Tamanho do arquivo em MB: ");
            novoLivro = new LivroDigital(titulo, autor, ano, formato, tamanho);
        } else {
            int duracao = lerInteiro("Duração em minutos: ");
            String narrador = lerTexto("Nome do narrador: ");
            novoLivro = new Audiolivro(titulo, autor, ano, duracao, narrador);
        }

        service.cadastrarLivro(novoLivro);
        System.out.println("Livro cadastrado com sucesso!");
    }

    private static void pesquisarPorTitulo() {
        String titulo = lerTexto("\nDigite o título ou parte dele: ");
        mostrarResultados(service.pesquisarPorTitulo(titulo));
    }

    private static void pesquisarPorAutor() {
        String autor = lerTexto("\nDigite o nome ou parte do autor: ");
        mostrarResultados(service.pesquisarPorAutor(autor));
    }

    private static void mostrarResultados(List<Livro> livros) {
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
            return;
        }

        for (Livro livro : livros) {
            System.out.println(livro);
        }
    }

    private static void alterarLivro() {
        List<Livro> livros = service.listarLivros();

        if (livros.isEmpty()) {
            System.out.println("Não existem livros cadastrados.");
            return;
        }

        System.out.println("\n===== LIVROS CADASTRADOS =====");
        for (int i = 0; i < livros.size(); i++) {
            System.out.println((i + 1) + " - " + livros.get(i).getTitulo());
        }

        int indice = lerInteiro("Informe o índice do livro que deseja alterar: ") - 1;
        if (indice < 0 || indice >= livros.size()) {
            System.out.println("Índice inválido.");
            return;
        }

        Livro livro = livros.get(indice);
        System.out.println("\nDados atuais:");
        System.out.println(livro);

        String titulo = lerTexto("Novo título: ");
        String autor = lerTexto("Novo autor: ");
        int ano = lerInteiro("Novo ano de publicação: ");

        String dadoTexto;
        double dadoNumero;

        if (livro instanceof LivroFisico) {
            LivroFisico fisico = (LivroFisico) livro;
            dadoNumero = lerInteiro("Nova quantidade de exemplares: ");
            dadoTexto = lerTexto("Nova localização: ");
        } else if (livro instanceof LivroDigital) {
            LivroDigital digital = (LivroDigital) livro;
            dadoTexto = lerTexto("Novo formato: ");
            dadoNumero = lerDecimal("Novo tamanho em MB: ");
        } else {
            Audiolivro audio = (Audiolivro) livro;
            dadoNumero = lerInteiro("Nova duração em minutos: ");
            dadoTexto = lerTexto("Novo narrador: ");
        }

        service.alterarLivro(indice, titulo, autor, ano, dadoTexto, dadoNumero);
        System.out.println("Livro alterado com sucesso!");
    }

    private static void excluirLivro() {
        List<Livro> livros = service.listarLivros();

        if (livros.isEmpty()) {
            System.out.println("Não existem livros cadastrados.");
            return;
        }

        System.out.println("\n===== LIVROS CADASTRADOS =====");
        for (int i = 0; i < livros.size(); i++) {
            System.out.println((i + 1) + " - " + livros.get(i).getTitulo());
        }

        int indice = lerInteiro("Informe o índice do livro que deseja excluir: ") - 1;
        if (indice < 0 || indice >= livros.size()) {
            System.out.println("Índice inválido.");
            return;
        }

        System.out.println("Livro selecionado: " + livros.get(indice).getTitulo());
        String confirmacao = lerTexto("Tem certeza que deseja excluir? (s/n): ");

        if (confirmacao.equalsIgnoreCase("s")) {
            service.excluirLivro(indice);
            System.out.println("Livro excluído com sucesso!");
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String valor = scanner.nextLine();

            try {
                return Integer.parseInt(valor.trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    private static double lerDecimal(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String valor = scanner.nextLine().trim().replace(',', '.');

            try {
                return Double.parseDouble(valor);
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }
}
