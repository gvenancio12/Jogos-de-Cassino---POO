package Perfil;

import Blackjack.Recursos.*;

public abstract class Pessoa {
    private String nome;
    protected Mao mao;

    public Pessoa(String nome) {
        this.nome = nome;
        this.mao = new Mao();
    }

    public String getNome() {
        return nome;
    }
    
    public Mao getMao() {
        return mao;
    }

    public void adicionarCarta(Carta carta) {
        this.mao.addCarta(carta);
    }

    public void limparMao() {
        this.mao.resetMao();
    }

    public int valorMao(){
        return this.mao.calculaValor();
    }

    public abstract void ImprimirMao();
}
