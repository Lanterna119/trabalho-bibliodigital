import java.util.ArrayList;
import java.util.List;

public class LivroService {

    private LivroRepository repository;

    public LivroService(LivroRepository repository) {
        this.repository = repository;
    }

    public List<Livro> listarLivros() {
        return repository.listarTodos();
    }

    public void cadastrarLivro(Livro livro) {
        validarLivro(livro, null);
        livro.setTitulo(livro.getTitulo().trim());
        livro.setAutor(livro.getAutor().trim());

        if (livro instanceof LivroFisico) {
            LivroFisico fisico = (LivroFisico) livro;
            fisico.setLocalizacao(fisico.getLocalizacao().trim());
        } else if (livro instanceof LivroDigital) {
            LivroDigital digital = (LivroDigital) livro;
            digital.setFormato(digital.getFormato().trim());
        } else if (livro instanceof Audiolivro) {
            Audiolivro audio = (Audiolivro) livro;
            audio.setNarrador(audio.getNarrador().trim());
        }

        repository.adicionar(livro);
    }

    public List<Livro> pesquisarPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe um título para pesquisar.");
        }
        return repository.buscarPorTitulo(titulo.trim());
    }

    public List<Livro> pesquisarPorAutor(String autor) {
        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe um autor para pesquisar.");
        }
        return repository.buscarPorAutor(autor.trim());
    }

    public void alterarLivro(int indice, String titulo, String autor, int ano,
                             String dadoTexto, double dadoNumero) {
        List<Livro> livros = repository.listarTodos();

        if (indice < 0 || indice >= livros.size()) {
            throw new IllegalArgumentException("Índice de livro inválido.");
        }

        Livro livro = livros.get(indice);
        validarDados(titulo, autor, ano, livro);

        livro.setTitulo(titulo.trim());
        livro.setAutor(autor.trim());
        livro.setAnoPublicacao(ano);

        if (livro instanceof LivroFisico) {
            LivroFisico fisico = (LivroFisico) livro;
            fisico.setLocalizacao(dadoTexto.trim());
            fisico.setQuantidadeExemplares((int) dadoNumero);
        } else if (livro instanceof LivroDigital) {
            LivroDigital digital = (LivroDigital) livro;
            digital.setFormato(dadoTexto.trim());
            digital.setTamanhoArquivo(dadoNumero);
        } else if (livro instanceof Audiolivro) {
            Audiolivro audio = (Audiolivro) livro;
            audio.setNarrador(dadoTexto.trim());
            audio.setDuracao((int) dadoNumero);
        }
    }

    public void excluirLivro(int indice) {
        List<Livro> livros = repository.listarTodos();

        if (indice < 0 || indice >= livros.size()) {
            throw new IllegalArgumentException("Índice de livro inválido.");
        }

        repository.excluir(livros.get(indice));
    }

    private void validarLivro(Livro livro, Livro livroAtual) {
        if (livro == null) {
            throw new IllegalArgumentException("Os dados do livro são inválidos.");
        }
        validarDados(livro.getTitulo(), livro.getAutor(),
                livro.getAnoPublicacao(), livroAtual);
    }

    private void validarDados(String titulo, String autor, int ano, Livro livroAtual) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O título não pode ficar vazio.");
        }

        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalArgumentException("O autor não pode ficar vazio.");
        }
        
        String tituloLimpo = titulo.trim();
        Livro encontrado = repository.buscarPorTituloExato(tituloLimpo);

        if (encontrado != null && encontrado != livroAtual) {
            throw new IllegalArgumentException("Já existe um livro com esse título.");
        }
    }
}
