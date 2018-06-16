public class Festa {
    private String nome = "FESTA JUNINA DA CATEDRAL";
    private short edicao = 1;
    private float consumacao = 0.0f;
    private String playlist = "HTTPS://OPEN.SPOTIFY.COM/USER/MREEBEE3/PLAYLIST/5XHDAAV4UXWGBVOIXLOCUZ";
    private short tipo = 1;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public short getEdicao() {
        return edicao;
    }

    public void setEdicao(short edicao) {
        this.edicao = edicao;
    }

    public float getConsumacao() {
        return consumacao;
    }

    public void setConsumacao(float consumacao) {
        this.consumacao = consumacao;
    }

    public String getPlaylist() {
        return playlist;
    }

    public void setPlaylist(String playlist) {
        this.playlist = playlist;
    }

    public short getTipo() {
        return tipo;
    }

    public void setTipo(short tipo) {
        this.tipo = tipo;
    }
}
