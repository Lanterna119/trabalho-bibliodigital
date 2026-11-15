import java.time.Year;

public abstract class Livro {

    protected String titulo;
    protected String autor;
    protected int anoPublicacao;

    public Livro(String titulo, String autor, int anoPublicacao) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    @Override
    public String toString() {
        return "Título: %s - Autor: %s - Ano de Publicação: %d"
                .formatted(titulo, autor, anoPublicacao);
    }

    public abstract String getTipo();

    public final int calcularIdade() {
        return Year.now().getValue() - this.anoPublicacao;
    }
}
