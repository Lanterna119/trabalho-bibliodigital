import java.util.ArrayList;
import java.util.List;

public class LivroRepository {

    private List<Livro> livros = new ArrayList<>();

    public List<Livro> listarTodos() {
        return new ArrayList<>(livros);
    }

    public void adicionar(Livro livro) {
        livros.add(livro);
    }

    public Livro buscarPorTituloExato(String titulo) {
        for (Livro livro : livros) {
            if (livro.getTitulo().equalsIgnoreCase(titulo)) {
                return livro;
            }
        }
        return null;
    }

    public List<Livro> buscarPorTitulo(String titulo) {
        List<Livro> encontrados = new ArrayList<>();

        for (Livro livro : livros) {
            if (livro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                encontrados.add(livro);
            }
        }

        return encontrados;
    }

    public List<Livro> buscarPorAutor(String autor) {
        List<Livro> encontrados = new ArrayList<>();

        for (Livro livro : livros) {
            if (livro.getAutor().toLowerCase().contains(autor.toLowerCase())) {
                encontrados.add(livro);
            }
        }

        return encontrados;
    }

    public void excluir(Livro livro) {
        livros.remove(livro);
    }
}
