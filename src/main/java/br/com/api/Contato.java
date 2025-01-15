package br.com.api;

public class Contato {

    public static int contador;
    private int id;
    public String cpf;
    public String nome;
    public String idade;

    public Contato() {}

    public Contato(String cpf, String nome, String idade) {
        contador++;
        this.id = contador;
        this.cpf = cpf;
        this.nome = nome;
        this.idade = idade;
    }

    public int getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    @Override
    public String toString() {
        return "id: " + id +
             ", cpf: " + cpf +
             ", nome: " + nome +
             ", idade: " + idade ;
    }

    

    
    

    

}
