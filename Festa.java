public class Festa {
    private String nome = "COVEN DAS BRUXAS";
    private short edicao = 2;
    private float consumacao = 20.0f;
    private String playlist = "HTTPS://OPEN.SPOTIFY.COM/ALBUM/47TDLYZHFCIJ7LGIUB5AMR";
    private short tipo = 0;

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
