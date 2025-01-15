package br.com.api;

import java.util.ArrayList;
import java.util.List;
import spark.Spark;
import spark.Request;
import spark.Response;
import spark.Route;

public class Main {

    private static List<Contato> listaContatos = new ArrayList<>();
    public static void main(String[] args) {
        Spark.port(8080);

        Spark.get("/contato/listar", listarContatos());
        Spark.get("/contato/listar/cpf/:cpf", listarPorCpf());
        Spark.get("/contato/listar/idade/:idade", listarPorIdade());
        Spark.get("/contato/listar/nome/:nome", listarPorNome());

        Spark.post("/contato/cadastrar", incluirContato());
        Spark.put("/contato/atualizar/:codigo", atualizarContato());
        Spark.delete("/contato/deletar/:codigo/:nome", excluirContato());

    }

    private static Route atualizarContato() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                int id = Integer.parseInt(request.params(":codigo"));

                if (listaContatos.isEmpty()) {
                    response.status(404); 
                    return "Não existem contatos na base.";

                } else {
                    for(Contato pessoa : listaContatos){
                        if (pessoa.getId()  == id) {
                            String cpf = request.queryParams("cpf");
                            String nome = request.queryParams("nome");
                            String idade = request.queryParams("idade");

                            pessoa.setCpf(cpf);
                            pessoa.setNome(nome);
                            pessoa.setIdade(idade);

                            response.status(200);

                            return "Usuário com ID " + id + " foi atualizado com sucesso!";

                        }
                       
                    }

                    response.status(404);
                    return "Contato com o ID "+ id +" especificado não encontrado.";
                }

            }
        };
    }

    private static Route excluirContato() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                int id = Integer.parseInt(request.params(":codigo"));
                String nome = request.params(":nome").trim();
             
                if (listaContatos.isEmpty()) {
                    response.status(404); // 404 Not Found
                    return "Não existem contatos na base.";
                } 

                boolean contatoRemovido = listaContatos.removeIf(pessoa ->
                 pessoa.getId() == id && pessoa.getNome().equalsIgnoreCase(nome)
                );

                if (contatoRemovido) {
                    response.status(200);
                    return "Usuário com ID " + id + " e nome \"" + nome + "\" foi excluído com sucesso!";
                } else {
                    response.status(404);
                    return "Contato com ID " + id + " e nome \"" + nome + "\" não encontrado.";
                }
            }

        };

    }

    private static Route incluirContato() {
        return new Route() {
            @Override

            public Object handle(Request request, Response response) throws Exception {

                String cpf = request.queryParams("cpf");
                String nome = request.queryParams("nome");
                String idade = request.queryParams("idade");

                Contato novoContato = new Contato(cpf, nome, idade);

                listaContatos.add(novoContato);

                response.status(201);

                return "Contato incluído com sucesso!";

            }

        };

    }

    private static Route listarContatos() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {

                String resposta = "";

                // metodo isEmpty verifica se a lista esta vazia

                if (listaContatos.isEmpty()) {

                    resposta += "Nenhum contato cadastrado.";

                } else {

                    resposta += "\n--- Lista de Contatos ---";

                    for (Contato pessoa : listaContatos) {
                        resposta += "\n" + pessoa.toString();
                    }
                }

                return resposta;

            }

        };
    }

    private static Route listarPorIdade() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response)  {
                try {

                    String idade = request.params(":idade");

                    List<Contato> contatosFiltrados = listaContatos.stream()
                    .filter(pessoa -> pessoa.getIdade().equals(idade))
                    .toList();

                    if (contatosFiltrados.isEmpty()) {
                        response.status(404); // 404 Not Found
                        return "Nenhum contato encontrado com a idade: " + idade;
                    }
        
                    StringBuilder resposta = new StringBuilder("\n--- Contatos com Idade: " + idade + " ---\n");
                    for (Contato pessoa : contatosFiltrados) {
                        resposta.append(pessoa.toString()).append("\n");
                    }
        
                    response.status(200); // 200 OK
                    return resposta.toString();


                    
                } catch (NumberFormatException e) {
                    response.status(400); // 400 Bad Request
                    return "Idade fornecida é inválida. Use um número inteiro.";
                }

            }

        };
    }

    private static Route listarPorCpf() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {

               String cpf = request.params(":cpf");

               List<Contato> contatosFiltrados = listaContatos.stream()
                    .filter(pessoa -> pessoa.getCpf().equals(cpf))
                    .toList();

                if (contatosFiltrados.isEmpty()) {
                    response.status(404); // 404 Not Found
                    return "Nenhum contato encontrado com o CPF: " + cpf;
                }

                StringBuilder resposta = new StringBuilder("\n--- Contatos com CPF: " + cpf + " ---\n");
                for (Contato pessoa : contatosFiltrados) {
                    resposta.append(pessoa.toString()).append("\n");
                }
        
                response.status(200); // 200 OK
                return resposta.toString();
            }

        };
    }

    private static Route listarPorNome() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {

               String nome = request.params(":nome").trim();

               List<Contato> contatosFiltrados = listaContatos.stream()
                    .filter(pessoa -> pessoa.getNome().equalsIgnoreCase(nome))
                    .toList();

                if (contatosFiltrados.isEmpty()) {
                    response.status(404); // 404 Not Found
                    return "Nenhum contato encontrado com o nome: " + nome;
                }

                StringBuilder resposta = new StringBuilder("\n--- Contatos com Nome: " + nome + " ---\n");
                for (Contato pessoa : contatosFiltrados) {
                    resposta.append(pessoa.toString()).append("\n");
                }
        
                response.status(200); // 200 OK
                return resposta.toString();
            }

        };
    }

}